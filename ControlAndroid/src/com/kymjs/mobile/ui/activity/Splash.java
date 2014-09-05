package com.kymjs.mobile.ui.activity;

import android.widget.ImageView;

import com.kymjs.mobile.R;

/**
 * Splash
 */
public class Splash extends BaseSplash {

    @Override
    protected void setRootBackground(ImageView bg) {
        bg.setImageResource(R.drawable.pic_bg_startpage);
    }

    @Override
    protected void redirectTo() {
        super.redirectTo();
        skipActivity(aty, Main.class);
    }
}
