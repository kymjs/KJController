package com.kymjs.mobile.ui.fragment;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.ViewInject;
import org.kymjs.aframe.ui.fragment.BaseFragment;
import org.kymjs.aframe.utils.PreferenceHelper;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kymjs.mobile.APPConfig;
import com.kymjs.mobile.AppContext;
import com.kymjs.mobile.R;
import com.kymjs.mobile.ui.activity.Main;

public class SetKey extends BaseFragment {

    @BindView(id = R.id.setting_touch_volume_up, click = true)
    private RelativeLayout mLayoutTouchUp;
    @BindView(id = R.id.setting_touch_volume_down, click = true)
    private RelativeLayout mLayoutTouchDown;
    @BindView(id = R.id.setting_touch_menu_key, click = true)
    private RelativeLayout mLayoutTouchMenu;
    @BindView(id = R.id.setting_touch_back_key, click = true)
    private RelativeLayout mLayoutTouchBack;
    @BindView(id = R.id.setkey_touch_tv_volume_up)
    private TextView mTvTouchUp;
    @BindView(id = R.id.setkey_touch_tv_volume_down)
    private TextView mTvTouchDown;
    @BindView(id = R.id.setkey_touch_tv_menu)
    private TextView mTvTouchMenu;
    @BindView(id = R.id.setkey_touch_tv_back)
    private TextView mTvTouchBack;

    @BindView(id = R.id.setting_gamepad_up, click = true)
    private RelativeLayout mLayoutGamePadUp;
    @BindView(id = R.id.setting_gamepad_down, click = true)
    private RelativeLayout mLayoutGamePadDown;
    @BindView(id = R.id.setting_gamepad_left, click = true)
    private RelativeLayout mLayoutGamePadLeft;
    @BindView(id = R.id.setting_gamepad_right, click = true)
    private RelativeLayout mLayoutGamePadRight;
    @BindView(id = R.id.setting_gamepad_w, click = true)
    private RelativeLayout mLayoutGamePadW;
    @BindView(id = R.id.setting_gamepad_s, click = true)
    private RelativeLayout mLayoutGamePadS;
    @BindView(id = R.id.setting_gamepad_a, click = true)
    private RelativeLayout mLayoutGamePadA;
    @BindView(id = R.id.setting_gamepad_d, click = true)
    private RelativeLayout mLayoutGamePadD;
    @BindView(id = R.id.setting_gamepad_menu_key, click = true)
    private RelativeLayout mLayoutGamePadMenu;
    @BindView(id = R.id.setting_gamepad_back_key, click = true)
    private RelativeLayout mLayoutGamePadBack;
    @BindView(id = R.id.setkey_gamepad_tv_up)
    private TextView mTvGamePadUp;
    @BindView(id = R.id.setkey_gamepad_tv_down)
    private TextView mTvGamePadDown;
    @BindView(id = R.id.setkey_gamepad_tv_left)
    private TextView mTvGamePadLeft;
    @BindView(id = R.id.setkey_gamepad_tv_right)
    private TextView mTvGamePadRight;
    @BindView(id = R.id.setkey_gamepad_tv_w)
    private TextView mTvGamePadW;
    @BindView(id = R.id.setkey_gamepad_tv_s)
    private TextView mTvGamePadS;
    @BindView(id = R.id.setkey_gamepad_tv_a)
    private TextView mTvGamePadA;
    @BindView(id = R.id.setkey_gamepad_tv_d)
    private TextView mTvGamePadD;
    @BindView(id = R.id.setkey_gamepad_tv_menu)
    private TextView mTvGamePadMenu;
    @BindView(id = R.id.setkey_gamepad_tv_back)
    private TextView mTvGamePadBack;

    private String[] menus;

    private Main aty;
    private AppContext application;

    @Override
    protected View inflaterView(LayoutInflater inflater,
            ViewGroup arg1, Bundle arg2) {
        aty = (Main) getActivity();
        application = (AppContext) getActivity().getApplication();
        return inflater.inflate(R.layout.frag_setkey, null);
    }

    @Override
    protected void initData() {
        super.initData();
        menus = getResources().getStringArray(R.array.keyboard);
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        mTvTouchUp.setText(application.keyVolumeUp);
        mTvTouchDown.setText(application.keyVolumeDown);
        mTvTouchMenu.setText(application.keyMenu);
        mTvTouchBack.setText(application.keyBack);
        mTvGamePadBack.setText(application.keyGamePadBack);
        mTvGamePadMenu.setText(application.keyGamePadMenu);
        mTvGamePadUp.setText(application.keyGamePadUp);
        mTvGamePadDown.setText(application.keyGamePadDown);
        mTvGamePadLeft.setText(application.keyGamePadLeft);
        mTvGamePadRight.setText(application.keyGamePadRight);
        mTvGamePadW.setText(application.keyGamePadW);
        mTvGamePadS.setText(application.keyGamePadS);
        mTvGamePadA.setText(application.keyGamePadA);
        mTvGamePadD.setText(application.keyGamePadD);
    }

    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
        case R.id.setting_touch_volume_up:
            getDialogListView(new ListListener() {
                @Override
                public void onItemClickListener(String hotkey) {
                    application.keyVolumeUp = hotkey;
                    mTvTouchUp.setText(hotkey);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.VOLUME_UP_MSG_KEY, hotkey);
                }
            });
            break;
        case R.id.setting_touch_volume_down:
            getDialogListView(new ListListener() {
                @Override
                public void onItemClickListener(String hotkey) {
                    application.keyVolumeDown = hotkey;
                    mTvTouchDown.setText(hotkey);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.VOLUME_DOWN_MSG_KEY, hotkey);
                }
            });
            break;
        case R.id.setting_touch_menu_key:
            getDialogListView(new ListListener() {
                @Override
                public void onItemClickListener(String hotkey) {
                    application.keyMenu = hotkey;
                    mTvTouchMenu.setText(hotkey);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.MENU_MSG_KEY, hotkey);
                }
            });
            break;
        case R.id.setting_touch_back_key:
            getDialogListView(new ListListener() {
                @Override
                public void onItemClickListener(String hotkey) {
                    application.keyBack = hotkey;
                    mTvTouchBack.setText(hotkey);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.BACK_MSG_KEY, hotkey);
                }
            });
            break;
        case R.id.setting_gamepad_w:
            getDialogListView(new ListListener() {
                @Override
                public void onItemClickListener(String hotkey) {
                    application.keyGamePadW = hotkey;
                    mTvGamePadW.setText(hotkey);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.GAMEPAD_W_MSG_KEY, hotkey);
                }
            });
            break;
        case R.id.setting_gamepad_s:
            getDialogListView(new ListListener() {
                @Override
                public void onItemClickListener(String hotkey) {
                    application.keyGamePadS = hotkey;
                    mTvGamePadS.setText(hotkey);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.GAMEPAD_S_MSG_KEY, hotkey);
                }
            });
            break;
        case R.id.setting_gamepad_a:
            getDialogListView(new ListListener() {
                @Override
                public void onItemClickListener(String hotkey) {
                    application.keyGamePadA = hotkey;
                    mTvGamePadA.setText(hotkey);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.GAMEPAD_A_MSG_KEY, hotkey);
                }
            });
            break;
        case R.id.setting_gamepad_d:
            getDialogListView(new ListListener() {
                @Override
                public void onItemClickListener(String hotkey) {
                    application.keyGamePadD = hotkey;
                    mTvGamePadD.setText(hotkey);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.GAMEPAD_D_MSG_KEY, hotkey);
                }
            });
            break;
        case R.id.setting_gamepad_up:
            getDialogListView(new ListListener() {
                @Override
                public void onItemClickListener(String hotkey) {
                    application.keyGamePadUp = hotkey;
                    mTvGamePadUp.setText(hotkey);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.GAMEPAD_UP_MSG_KEY, hotkey);
                }
            });
            break;
        case R.id.setting_gamepad_down:
            getDialogListView(new ListListener() {
                @Override
                public void onItemClickListener(String hotkey) {
                    application.keyGamePadDown = hotkey;
                    mTvGamePadDown.setText(hotkey);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.GAMEPAD_DOWN_MSG_KEY, hotkey);
                }
            });
            break;
        case R.id.setting_gamepad_left:
            getDialogListView(new ListListener() {
                @Override
                public void onItemClickListener(String hotkey) {
                    application.keyGamePadLeft = hotkey;
                    mTvGamePadLeft.setText(hotkey);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.GAMEPAD_LEFT_MSG_KEY, hotkey);
                }
            });
            break;
        case R.id.setting_gamepad_right:
            getDialogListView(new ListListener() {
                @Override
                public void onItemClickListener(String hotkey) {
                    application.keyGamePadRight = hotkey;
                    mTvGamePadRight.setText(hotkey);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.GAMEPAD_RIGHT_MSG_KEY, hotkey);
                }
            });
            break;
        case R.id.setting_gamepad_menu_key:
            getDialogListView(new ListListener() {
                @Override
                public void onItemClickListener(String hotkey) {
                    application.keyGamePadMenu = hotkey;
                    mTvGamePadMenu.setText(hotkey);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.GAMEPAD_MENU_MSG_KEY, hotkey);
                }
            });
            break;
        case R.id.setting_gamepad_back_key:
            getDialogListView(new ListListener() {
                @Override
                public void onItemClickListener(String hotkey) {
                    application.keyGamePadBack = hotkey;
                    mTvGamePadBack.setText(hotkey);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.GAMEPAD_BACK_MSG_KEY, hotkey);
                }
            });
            break;
        }
    }

    private void getDialogListView(final ListListener l) {
        final ListView listView = (ListView) View.inflate(aty,
                R.layout.listview, null);
        listView.setAdapter(new ArrayAdapter<String>(aty,
                android.R.layout.simple_list_item_1, menus));
        final AlertDialog dialog = ViewInject.create().getDialogView(
                aty, null, listView);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                l.onItemClickListener(menus[position]);
                dialog.dismiss();
            }
        });
    }

    private interface ListListener {
        void onItemClickListener(String hotkey);
    }
}
