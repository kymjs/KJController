package com.kymjs.mobile.ui.activity;

import org.kymjs.aframe.utils.PreferenceHelper;

import android.widget.ImageView;

import com.kymjs.mobile.APPConfig;
import com.kymjs.mobile.AppContext;
import com.kymjs.mobile.R;

/**
 * Splash
 */
public class Splash extends BaseSplash {

    private AppContext application;
    private String[] menus;

    @Override
    protected void setRootBackground(ImageView bg) {
        bg.setImageResource(R.drawable.pic_bg_startpage);
    }

    @Override
    protected void initData() {
        super.initData();
        application = (AppContext) getApplication();
        menus = getResources().getStringArray(R.array.keyboard);
        application.keyVolumeUp = PreferenceHelper.readString(aty,
                APPConfig.KEYBOARD_KEY, APPConfig.VOLUME_UP_MSG_KEY,
                menus[3]);
        application.keyVolumeDown = PreferenceHelper.readString(aty,
                APPConfig.KEYBOARD_KEY,
                APPConfig.VOLUME_DOWN_MSG_KEY, menus[4]);
        application.keyBack = PreferenceHelper.readString(aty,
                APPConfig.KEYBOARD_KEY, APPConfig.BACK_MSG_KEY,
                menus[4]);
        application.keyMenu = PreferenceHelper.readString(aty,
                APPConfig.KEYBOARD_KEY, APPConfig.MENU_MSG_KEY,
                menus[7]);
        application.keyGamePadA = PreferenceHelper.readString(aty,
                APPConfig.KEYBOARD_KEY, APPConfig.GAMEPAD_A_MSG_KEY,
                menus[10]);
        application.keyGamePadW = PreferenceHelper.readString(aty,
                APPConfig.KEYBOARD_KEY, APPConfig.GAMEPAD_W_MSG_KEY,
                menus[8]);
        application.keyGamePadS = PreferenceHelper.readString(aty,
                APPConfig.KEYBOARD_KEY, APPConfig.GAMEPAD_S_MSG_KEY,
                menus[9]);
        application.keyGamePadD = PreferenceHelper.readString(aty,
                APPConfig.KEYBOARD_KEY, APPConfig.GAMEPAD_D_MSG_KEY,
                menus[11]);
        application.keyGamePadBack = PreferenceHelper.readString(aty,
                APPConfig.KEYBOARD_KEY,
                APPConfig.GAMEPAD_BACK_MSG_KEY, menus[4]);
        application.keyGamePadMenu = PreferenceHelper.readString(aty,
                APPConfig.KEYBOARD_KEY,
                APPConfig.GAMEPAD_MENU_MSG_KEY, menus[7]);
        application.keyGamePadUp = PreferenceHelper.readString(aty,
                APPConfig.KEYBOARD_KEY, APPConfig.GAMEPAD_UP_MSG_KEY,
                menus[0]);
        application.keyGamePadDown = PreferenceHelper.readString(aty,
                APPConfig.KEYBOARD_KEY,
                APPConfig.GAMEPAD_DOWN_MSG_KEY, menus[1]);
        application.keyGamePadLeft = PreferenceHelper.readString(aty,
                APPConfig.KEYBOARD_KEY,
                APPConfig.GAMEPAD_LEFT_MSG_KEY, menus[2]);
        application.keyGamePadRight = PreferenceHelper.readString(
                aty, APPConfig.KEYBOARD_KEY,
                APPConfig.GAMEPAD_RIGHT_MSG_KEY, menus[3]);
    }

    @Override
    protected void redirectTo() {
        super.redirectTo();
        skipActivity(aty, Main.class);
    }
}
