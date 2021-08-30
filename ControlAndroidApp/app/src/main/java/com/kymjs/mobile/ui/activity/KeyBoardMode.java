package com.kymjs.mobile.ui.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.kymjs.mobile.R;

import org.kymjs.aframe.ui.BindView;

public class KeyBoardMode extends ControlActivity {

    @BindView(id = R.id.start, click = true)
    Button button;

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_keyboard);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.start:
                sendPcMessage(KeyBoard + application.keyGameHotKey1);
                break;
        }
    }

    @Override
    protected void handleKeyBoardEvent(int keyCode, KeyEvent event) {

    }
}
