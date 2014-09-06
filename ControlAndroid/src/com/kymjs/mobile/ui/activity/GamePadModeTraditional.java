package com.kymjs.mobile.ui.activity;

import org.kymjs.aframe.ui.activity.BaseActivity;

import com.kymjs.mobile.R;

public class GamePadModeTraditional extends BaseActivity {
    public GamePadModeTraditional() {
        setAllowFullScreen(true);
        setScreenOrientation(ScreenOrientation.HORIZONTAL);
    }

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_gamepad);
    }
}
