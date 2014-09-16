package com.kymjs.mobile.ui.fragment;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.ViewInject;
import org.kymjs.aframe.ui.fragment.BaseFragment;
import org.kymjs.aframe.utils.StringUtils;
import org.kymjs.aframe.utils.SystemTool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kymjs.mobile.AppContext;
import com.kymjs.mobile.R;
import com.kymjs.mobile.ui.activity.CarMode;
import com.kymjs.mobile.ui.activity.GamePadMode;
import com.kymjs.mobile.ui.activity.GamePadModeTraditional;
import com.kymjs.mobile.ui.activity.Main;
import com.kymjs.mobile.ui.activity.TouchPadMode;
import com.kymjs.mobile.ui.widget.ScrollLayout;
import com.kymjs.mobile.ui.widget.ScrollLayout.OnViewChangeListener;

public class MainFragment extends BaseFragment {

    @BindView(id = R.id.main_tv_title)
    private TextView mTvTitle;
    @BindView(id = R.id.main_et_pcip)
    private EditText mEtPcIp;
    @BindView(id = R.id.main_et_pcport)
    private EditText mEtPcPort;
    @BindView(id = R.id.main_btn_submit, click = true)
    private Button mBtnSubmit;
    @BindView(id = R.id.main_pager)
    private ScrollLayout mContentPager;

    private Main aty;

    @Override
    protected View inflaterView(LayoutInflater arg0, ViewGroup arg1,
            Bundle arg2) {
        aty = (Main) getActivity();
        return arg0.inflate(R.layout.frag_main, null);
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        mEtPcIp.setText("192.168.1.100");
        mEtPcPort.setText("8899");
        aty.resideMenu.addIgnoredView(mContentPager);
        mContentPager
                .setOnViewChangeListener(new OnViewChangeListener() {
                    @Override
                    public void OnViewChange(int view) {
                        switch (view) {
                        case 0:
                            mTvTitle.setText("← "
                                    + getString(R.string.touch_pad_mode)
                                    + " →");
                            break;
                        case 1:
                            mTvTitle.setText("← "
                                    + getString(R.string.game_pad_mode)
                                    + " →");
                            break;
                        case 2:
                            mTvTitle.setText("← "
                                    + getString(R.string.control_pad_mode)
                                    + " →");
                            break;
                        case 3:
                            mTvTitle.setText("← "
                                    + getString(R.string.car_mode)
                                    + " →");
                            break;
                        }
                    }
                });
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
        case R.id.main_btn_submit:
            beginConnection();
            break;
        }
    }

    /**
     * 判断输入是否为IP地址
     */
    private boolean checkedInput() {
        return true;
    }

    /**
     * 检测网络，记录IP
     */
    private void beginConnection() {
        if (!checkedInput()) {
            ViewInject.toast("输入地址不正确");
        } else {
            if (SystemTool.checkNet(aty) && SystemTool.isWiFi(aty)) {
                ((AppContext) aty.getApplication()).ip = mEtPcIp
                        .getText().toString();
                ((AppContext) aty.getApplication()).port = StringUtils
                        .toInt(mEtPcPort.getText());
                recordTo();
            } else {
                ViewInject
                        .toast(getString(R.string.connection_error));
            }
        }
    }

    /**
     * 跳转逻辑
     */
    private void recordTo() {
        switch (mContentPager.getCurScreen()) {
        case 0:
            aty.showActivity(aty, TouchPadMode.class);
            break;
        case 1:
            aty.showActivity(aty, GamePadModeTraditional.class);
            break;
        case 2:
            aty.showActivity(aty, GamePadMode.class);
            break;
        case 3:
            aty.showActivity(aty, CarMode.class);
            break;
        }
    }
}
