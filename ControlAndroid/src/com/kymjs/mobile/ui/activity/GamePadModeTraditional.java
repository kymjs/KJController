package com.kymjs.mobile.ui.activity;

import org.kymjs.aframe.ui.BindView;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.kymjs.mobile.R;

public class GamePadModeTraditional extends ControlActivity {

    @BindView(id = R.id.gamepad_left_up, click = true)
    private ImageView mImgW;
    @BindView(id = R.id.gamepad_left_down, click = true)
    private ImageView mImgS;
    @BindView(id = R.id.gamepad_left_left, click = true)
    private ImageView mImgA;
    @BindView(id = R.id.gamepad_left_right, click = true)
    private ImageView mImgD;
    @BindView(id = R.id.gamepad_right_up, click = true)
    private ImageView mImgUp;
    @BindView(id = R.id.gamepad_right_down, click = true)
    private ImageView mImgDown;
    @BindView(id = R.id.gamepad_right_left, click = true)
    private ImageView mImgLeft;
    @BindView(id = R.id.gamepad_right_right, click = true)
    private ImageView mImgRight;
    @BindView(id = R.id.gamepad_space, click = true)
    private TextView mSpace;
    @BindView(id = R.id.gamepad_back, click = true)
    private ImageView mBack;

    public GamePadModeTraditional() {
        setAllowFullScreen(true);
        setHiddenActionBar(true);
        setScreenOrientation(ScreenOrientation.HORIZONTAL);
    }

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_gamepad_traditional);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mImgW.setOnTouchListener(getTouchListener(application.keyGamePadW));
        mImgS.setOnTouchListener(getTouchListener(application.keyGamePadS));
        mImgA.setOnTouchListener(getTouchListener(application.keyGamePadA));
        mImgD.setOnTouchListener(getTouchListener(application.keyGamePadD));
        mImgUp.setOnTouchListener(getTouchListener(application.keyGamePadUp));
        mImgDown.setOnTouchListener(getTouchListener(application.keyGamePadDown));
        mImgLeft.setOnTouchListener(getTouchListener(application.keyGamePadLeft));
        mImgRight
                .setOnTouchListener(getTouchListener(application.keyGamePadRight));
    }

    private OnTouchListener getTouchListener(final String keyEvent) {
        return new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendMessage(KeyBoard + keyEvent + "Press");
                } else {
                    sendMessage(KeyBoard + keyEvent + "Release");
                }
                return true;
            }
        };
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
        case R.id.gamepad_left_up:
            sendMessage(KeyBoard + application.keyGamePadW);
            break;
        case R.id.gamepad_left_down:
            sendMessage(KeyBoard + application.keyGamePadS);
            break;
        case R.id.gamepad_left_left:
            sendMessage(KeyBoard + application.keyGamePadA);
            break;
        case R.id.gamepad_left_right:
            sendMessage(KeyBoard + application.keyGamePadD);
            break;
        case R.id.gamepad_right_up:
            sendMessage(KeyBoard + application.keyGamePadUp);
            break;
        case R.id.gamepad_right_down:
            sendMessage(KeyBoard + application.keyGamePadDown);
            break;
        case R.id.gamepad_right_left:
            sendMessage(KeyBoard + application.keyGamePadLeft);
            break;
        case R.id.gamepad_right_right:
            sendMessage(KeyBoard + application.keyGamePadRight);
            break;
        case R.id.gamepad_space:
            sendMessage(KeyBoard + "Space");
            break;
        case R.id.gamepad_back:
            finish();
            break;
        }
    }

    @Override
    protected void handleKeyBoardEvent(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            sendMessage(KeyBoard + application.keyGamePadBack);
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            sendMessage(KeyBoard + application.keyGamePadMenu);
        }
    }
}
