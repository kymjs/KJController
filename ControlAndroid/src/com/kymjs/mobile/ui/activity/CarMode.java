package com.kymjs.mobile.ui.activity;

import org.kymjs.aframe.ui.BindView;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.kymjs.mobile.R;

public class CarMode extends ControlActivity {

    @BindView(id = R.id.up, click = true)
    private ImageView keyboardUp;
    @BindView(id = R.id.down, click = true)
    private ImageView keyboardDown;
    @BindView(id = R.id.left, click = true)
    private ImageView keyboardLeft;
    @BindView(id = R.id.right, click = true)
    private ImageView keyboardRight;
    @BindView(id = R.id.more)
    private ImageView more;
    @BindView(id = R.id.spaker)
    private ImageView speaker;
    @BindView(id = R.id.light, click = true)
    private CheckBox light;
    @BindView(id = R.id.gamepad_back, click = true)
    private ImageView mImgBack;

    public CarMode() {
        setAllowFullScreen(true);
        setHiddenActionBar(true);
        setScreenOrientation(ScreenOrientation.HORIZONTAL);
    }

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_car);
    }

    @Override
    protected void handleKeyBoardEvent(int keyCode, KeyEvent event) {}

    @Override
    protected void initWidget() {
        super.initWidget();
        keyBoardTouchListener listener = new keyBoardTouchListener();
        keyboardUp.setOnTouchListener(listener);
        keyboardDown.setOnTouchListener(listener);
        keyboardLeft.setOnTouchListener(listener);
        keyboardRight.setOnTouchListener(listener);
        more.setOnTouchListener(listener);
        speaker.setOnTouchListener(listener);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
        case R.id.gamepad_back:
            finish();
            break;
        case R.id.light:
            sendPcMessage(light.isChecked() ? Car_LightOn
                    : Car_LightOff);
            break;
        }
    }

    private class keyBoardTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {// 按下
                switch (v.getId()) {
                case R.id.up:
                    sendCarMessage(Car_Up);
                    break;
                case R.id.down:
                    sendCarMessage(Car_Down);
                    break;
                case R.id.left:
                    sendCarMessage(Car_Left);
                    break;
                case R.id.right:
                    sendCarMessage(Car_Right);
                    break;
                case R.id.spaker:
                    sendCarMessage(Car_Spaker);
                    break;
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {// 弹起
                sendCarMessage(Car_Release);
                v.performClick();
            }
            return false;
        }
    }
}
