package com.kymjs.mobile.ui.activity;

import org.kymjs.aframe.ui.BindView;

import android.view.View;

import com.kymjs.mobile.R;

public class GamePadMode extends ControlActivity {
    @BindView(id = R.id.gamepad_mouse)
    private View mMouse;

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
    public boolean onLongClick(View v) {
        return false;
    }
}
