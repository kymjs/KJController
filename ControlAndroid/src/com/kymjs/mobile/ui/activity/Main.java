package com.kymjs.mobile.ui.activity;

import org.kymjs.aframe.ui.KJActivityManager;
import org.kymjs.aframe.ui.fragment.BaseFragment;
import org.kymjs.aframe.ui.widget.ResideMenuItem;

import android.view.KeyEvent;
import android.view.View;

import com.kymjs.mobile.R;
import com.kymjs.mobile.ui.fragment.MainFragment;
import com.kymjs.mobile.ui.fragment.SetKey;

/**
 * BeginConnection
 */
public class Main extends SlidTemplet {

    private ResideMenuItem item1;
    private ResideMenuItem item2;
    private ResideMenuItem item3;
    private ResideMenuItem item4;
    private ResideMenuItem item5;

    BaseFragment currentFragment;

    @Override
    protected int setRootViewID() {
        return R.layout.aty_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        changeFragment(new MainFragment());
        item1 = new ResideMenuItem(this, R.drawable.ic_launcher,
                "自定按键");
        item2 = new ResideMenuItem(this, R.drawable.ic_launcher,
                "模式设置");
        item3 = new ResideMenuItem(this, R.drawable.ic_launcher,
                "主题选择");
        item4 = new ResideMenuItem(this, R.drawable.ic_launcher,
                "关于应用");
        item5 = new ResideMenuItem(this, R.drawable.ic_launcher,
                "立即退出");
        // 必须调用initSlidMenu()才能初始化菜单项
        initSlidMenus(item1, item2, item3, item4, item5);
    }

    @Override
    public void onSlidMenuClick(View v) {
        if (v == item1) {
            changeFragment(new SetKey());
        } else if (v == item2) {
        } else if (v == item3) {
        } else if (v == item4) {
        } else if (v == item5) {
            KJActivityManager.create().AppExit(aty);
        }
        resideMenu.closeMenu();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (currentFragment instanceof MainFragment) {
                resideMenu.openMenu();
            } else {
                toHome();
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            resideMenu.openMenu();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 必须调用super()，否则界面触摸将被屏蔽
     */
    @Override
    public void changeFragment(BaseFragment targetFragment) {
        super.changeFragment(targetFragment);
        currentFragment = targetFragment;
        changeFragment(R.id.content, targetFragment);
    }

    public void toHome() {
        changeFragment(new MainFragment());
    }
}
