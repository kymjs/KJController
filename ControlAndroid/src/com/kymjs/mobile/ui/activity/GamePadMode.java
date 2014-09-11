package com.kymjs.mobile.ui.activity;

import org.kymjs.aframe.ui.BindView;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.kymjs.mobile.R;

public class GamePadMode extends TouchControlActivity {
    @BindView(id = R.id.gamepad_mouse)
    private View mMouseTouch;
    @BindView(id = R.id.gamepad_back, click = true)
    private ImageView mImgBack;
    @BindView(id = R.id.gamepad_keyboard_up, click = true)
    private ImageView mKeyBoardUp;
    @BindView(id = R.id.gamepad_keyboard_down, click = true)
    private ImageView mKeyBoardDown;
    @BindView(id = R.id.gamepad_keyboard_left, click = true)
    private ImageView mKeyBoardLeft;
    @BindView(id = R.id.gamepad_keyboard_right, click = true)
    private ImageView mKeyBoardRight;

    public GamePadMode() {
        setAllowFullScreen(true);
        setHiddenActionBar(true);
        setScreenOrientation(ScreenOrientation.HORIZONTAL);
    }

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_gamepad);
    }

    @Override
    protected View setTouchView() {
        return mMouseTouch;
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
        case R.id.gamepad_back:
            finish();
            break;
        case R.id.gamepad_keyboard_up:
            sendMessage(KeyBoard + application.keyGamePadUp);
            break;
        case R.id.gamepad_keyboard_down:
            sendMessage(KeyBoard + application.keyGamePadDown);
            break;
        case R.id.gamepad_keyboard_left:
            sendMessage(KeyBoard + application.keyGamePadLeft);
            break;
        case R.id.gamepad_keyboard_right:
            sendMessage(KeyBoard + application.keyGamePadRight);
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
