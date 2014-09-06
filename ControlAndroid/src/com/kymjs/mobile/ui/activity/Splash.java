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
                menus[0]);
        application.keyMenu = PreferenceHelper.readString(aty,
                APPConfig.KEYBOARD_KEY, APPConfig.MENU_MSG_KEY,
                menus[0]);
    }

    @Override
    protected void redirectTo() {
        super.redirectTo();
        skipActivity(aty, Main.class);
    }
}
