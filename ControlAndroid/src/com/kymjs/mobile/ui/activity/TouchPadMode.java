package com.kymjs.mobile.ui.activity;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.utils.DensityUtils;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.kymjs.mobile.R;

public class TouchPadMode extends ControlActivity {

    private static final String LeftKeyDown = "leftButton:down";
    private static final String LeftKeyUp = "leftButton:release";
    private static final String RightKeyDown = "rightButton:down";
    private static final String RightKeyUp = "rightButton:release";
    private static final String MouseMove = "mouse:";
    private static final String MouseWheel = "mousewheel:";

    private static float sMoveX = 0; // 发送的鼠标移动的差值
    private static float sMoveY = 0;
    private static float sPrevX; // 记录上次鼠标的位置
    private static float sPrevY;
    private static float sFirstX; // 手指第一次接触屏幕时的坐标
    private static float sFirstY;

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
    protected void initWidget() {
        super.initWidget();
        screenAdapter();
        mMouseLeftKey.setOnLongClickListener(this);
        mMouseRightKey.setOnLongClickListener(this);
        mMouseLeftKey
                .setBackgroundResource(R.drawable.selector_mouse_left);
        mMouseRightKey
                .setBackgroundResource(R.drawable.selector_mouse_right);
        mMouseWheel.setBackgroundResource(R.drawable.ic_mouse_wheel);
        initTouchEvent();
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
            sendMessage(LeftKeyDown);
            sendMessage(LeftKeyUp);
            break;
        case R.id.control_btn_mouse_rightkey:
            sendMessage(RightKeyDown);
            sendMessage(RightKeyUp);
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
                sendMessage(LeftKeyDown);
                mMouseLeftKey
                        .setBackgroundResource(R.drawable.ic_mouse_left_sel);
                flag_press = true;
            }
            break;
        case R.id.control_btn_mouse_rightkey:
            if (!flag_press) {
                sendMessage(RightKeyDown);
                mMouseRightKey
                        .setBackgroundResource(R.drawable.ic_mouse_right_sel);
                flag_press = true;
            }
            break;
        }
        return true;
    }

    /**
     * 初始化鼠标移动事件
     */
    private void initTouchEvent() {
        /**
         * 触摸板事件
         */
        mMouseTouch.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sPrevX = event.getX();
                    sPrevY = event.getY();
                    sFirstX = event.getX();
                    sFirstY = event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    handleMoveEvent(event);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (sFirstX == event.getX()
                            && sFirstY == event.getY()) {
                        mMouseLeftKey.performClick();
                    } else {
                        v.performClick();
                    }
                }
                return true;
            }
        });

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
     * 处理鼠标移动事件
     */
    private void handleMoveEvent(MotionEvent ev) {
        float currentX = ev.getX();
        sMoveX = currentX - sPrevX; // 当前鼠标位置 - 上次鼠标的位置
        sPrevX = currentX; // 把当前鼠标的位置付给lx 以备下次使用
        float currentY = ev.getY();
        sMoveY = currentY - sPrevY;
        sPrevY = currentY;
        if (sMoveX != 0 && sMoveY != 0) {
            StringBuilder str = new StringBuilder(MouseMove);
            str.append(sMoveX).append(",").append(sMoveY);
            sendMessage(str.toString());
        }
    }

    /**
     * 处理滚轮事件
     */
    private void handleWheelEvent() {
        if (sMoveY > 3 || sMoveY < -3) { // 减少发送次数 滑轮移动慢点
            sendMessage(MouseWheel + sMoveY);
        }
    }

    @Override
    protected void sendMessage(String datas) {
        if (LeftKeyUp.equals(datas) && flag_press) {
            flag_press = false;
            mMouseLeftKey
                    .setBackgroundResource(R.drawable.selector_mouse_left);
            mMouseRightKey
                    .setBackgroundResource(R.drawable.selector_mouse_right);
        }
        if (RightKeyUp.equals(datas) && flag_press) {
            flag_press = false;
            mMouseLeftKey
                    .setBackgroundResource(R.drawable.selector_mouse_left);
            mMouseRightKey
                    .setBackgroundResource(R.drawable.selector_mouse_right);
        }
        super.sendMessage(datas);
    }
}
