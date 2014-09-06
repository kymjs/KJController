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
    private static final int VOLUME_UP = 0x1;
    private static final int VOLUME_DOWN = 0x2;
    private static final int BACK = 0x3;
    private static final int MENU = 0x4;

    @BindView(id = R.id.setting_touch_volume_up, click = true)
    private RelativeLayout mLayoutTouchUp;
    @BindView(id = R.id.setting_touch_volume_down, click = true)
    private RelativeLayout mLayoutTouchDown;
    @BindView(id = R.id.setting_touch_menu_key, click = true)
    private RelativeLayout mLayoutTouchMenu;
    @BindView(id = R.id.setting_touch_back_key, click = true)
    private RelativeLayout mLayoutTouchBack;
    @BindView(id = R.id.setkey_tv_volume_up)
    private TextView mTvTouchUp;
    @BindView(id = R.id.setkey_tv_volume_down)
    private TextView mTvTouchDown;
    @BindView(id = R.id.setkey_tv_menu)
    private TextView mTvTouchMenu;
    @BindView(id = R.id.setkey_tv_back)
    private TextView mTvTouchBack;

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
    }

    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
        case R.id.setting_touch_volume_up:
            getDialogListView(VOLUME_UP);
            break;
        case R.id.setting_touch_volume_down:
            getDialogListView(VOLUME_DOWN);
            break;
        case R.id.setting_touch_menu_key:
            getDialogListView(MENU);
            break;
        case R.id.setting_touch_back_key:
            getDialogListView(BACK);
            break;
        }
    }

    private void getDialogListView(final int key) {
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
                switch (key) {
                case VOLUME_UP:
                    application.keyVolumeUp = menus[position];
                    mTvTouchUp.setText(menus[position]);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.VOLUME_UP_MSG_KEY,
                            menus[position]);
                    break;
                case VOLUME_DOWN:
                    application.keyVolumeDown = menus[position];
                    mTvTouchDown.setText(menus[position]);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.VOLUME_DOWN_MSG_KEY,
                            menus[position]);
                    break;
                case BACK:
                    application.keyBack = menus[position];
                    mTvTouchBack.setText(menus[position]);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.BACK_MSG_KEY, menus[position]);
                    break;
                case MENU:
                    application.keyMenu = menus[position];
                    mTvTouchMenu.setText(menus[position]);
                    PreferenceHelper.write(aty,
                            APPConfig.KEYBOARD_KEY,
                            APPConfig.MENU_MSG_KEY, menus[position]);
                    break;
                }
                dialog.dismiss();
            }
        });
    }
}
