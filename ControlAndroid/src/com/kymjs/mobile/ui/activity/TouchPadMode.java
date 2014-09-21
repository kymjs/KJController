package com.kymjs.mobile.ui.activity;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.utils.DensityUtils;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.kymjs.mobile.R;

public class TouchPadMode extends TouchControlActivity implements
        OnLongClickListener {
    
    private static float sMoveY = 0;// 发送的鼠标移动的差值
    private static float sPrevY;
    
    @BindView(id = R.id.control_btn_mouse_leftkey, click = true)
    private Button mMouseLeftKey;
    @BindView(id = R.id.control_btn_mouse_rightkey, click = true)
    private Button mMouseRightKey;
    @BindView(id = R.id.control_wheel)
    private View mMouseWheel;
    @BindView(id = R.id.control_mouse)
    private View mMouseTouch;
    @BindView(id = R.id.control_back, click = true)
    private ImageView mImgBack;
    
    private boolean flag_press = false;
    
    @Override
    public void setRootView() {
        setContentView(R.layout.aty_control);
    }
    
    @Override
    protected View setTouchView() {
        return mMouseTouch;
    }
    
    @Override
    protected void initWidget() {
        super.initWidget();
        screenAdapter();
        mMouseLeftKey.setOnLongClickListener(this);
        mMouseRightKey.setOnLongClickListener(this);
        mMouseLeftKey.setBackgroundResource(R.drawable.selector_mouse_left);
        mMouseRightKey.setBackgroundResource(R.drawable.selector_mouse_right);
        mMouseWheel.setBackgroundResource(R.drawable.ic_mouse_wheel);
        /**
         * 滚轮事件
         */
        mMouseWheel.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sPrevY = event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.performClick();
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    float currentY = event.getY();
                    sMoveY = currentY - sPrevY;
                    sPrevY = currentY;
                    handleWheelEvent();
                }
                return true;
            }
        });
    }
    
    /**
     * 屏幕适配器
     */
    private void screenAdapter() {
        int w;
        LinearLayout.LayoutParams paramsL = (LayoutParams) mMouseLeftKey
                .getLayoutParams();
        w = DensityUtils.getScreenW(aty) / 5;
        paramsL.width = w * 2;
        mMouseLeftKey.setLayoutParams(paramsL);
        LinearLayout.LayoutParams paramsW = (LayoutParams) mMouseWheel
                .getLayoutParams();
        paramsW.width = w;
        mMouseWheel.setLayoutParams(paramsW);
        LinearLayout.LayoutParams paramsR = (LayoutParams) mMouseRightKey
                .getLayoutParams();
        paramsR.width = w * 2;
        mMouseRightKey.setLayoutParams(paramsR);
    }
    
    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
        case R.id.control_btn_mouse_leftkey:
            sendPcMessage(LeftKeyDown);
            sendPcMessage(LeftKeyUp);
            break;
        case R.id.control_btn_mouse_rightkey:
            sendPcMessage(RightKeyDown);
            sendPcMessage(RightKeyUp);
            break;
        case R.id.control_back:
            finish();
            break;
        }
    }
    
    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
        case R.id.control_btn_mouse_leftkey:
            if (!flag_press) {
                sendPcMessage(LeftKeyDown);
                mMouseLeftKey
                        .setBackgroundResource(R.drawable.ic_mouse_left_sel);
                flag_press = true;
            }
            break;
        case R.id.control_btn_mouse_rightkey:
            if (!flag_press) {
                sendPcMessage(RightKeyDown);
                mMouseRightKey
                        .setBackgroundResource(R.drawable.ic_mouse_right_sel);
                flag_press = true;
            }
            break;
        }
        return true;
    }
    
    /**
     * 处理滚轮事件
     */
    private void handleWheelEvent() {
        if (sMoveY > 3 || sMoveY < -3) { // 减少发送次数 滑轮移动慢点
            sendPcMessage(MouseWheel + sMoveY);
        }
    }
    
    @Override
    protected void handleKeyBoardEvent(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            sendPcMessage(KeyBoard + application.keyBack);
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            sendPcMessage(KeyBoard + application.keyMenu);
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            sendPcMessage(KeyBoard + application.keyVolumeUp);
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            sendPcMessage(KeyBoard + application.keyVolumeDown);
        }
    }
    
    @Override
    protected void sendPcMessage(String datas) {
        if (LeftKeyUp.equals(datas) && flag_press) {
            flag_press = false;
            mMouseLeftKey.setBackgroundResource(R.drawable.selector_mouse_left);
            mMouseRightKey
                    .setBackgroundResource(R.drawable.selector_mouse_right);
        }
        if (RightKeyUp.equals(datas) && flag_press) {
            flag_press = false;
            mMouseLeftKey.setBackgroundResource(R.drawable.selector_mouse_left);
            mMouseRightKey
                    .setBackgroundResource(R.drawable.selector_mouse_right);
        }
        super.sendPcMessage(datas);
    }
    
}
