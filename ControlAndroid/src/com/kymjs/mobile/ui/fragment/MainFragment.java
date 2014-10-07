package com.kymjs.mobile.ui.fragment;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.ViewInject;
import org.kymjs.aframe.ui.fragment.BaseFragment;
import org.kymjs.aframe.ui.widget.KJViewPager;
import org.kymjs.aframe.ui.widget.KJViewPager.OnViewChangeListener;
import org.kymjs.aframe.utils.PreferenceHelper;
import org.kymjs.aframe.utils.StringUtils;
import org.kymjs.aframe.utils.SystemTool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kymjs.mobile.APPConfig;
import com.kymjs.mobile.AppContext;
import com.kymjs.mobile.R;
import com.kymjs.mobile.ui.activity.CarMode;
import com.kymjs.mobile.ui.activity.GamePadMode;
import com.kymjs.mobile.ui.activity.GamePadModeTraditional;
import com.kymjs.mobile.ui.activity.Main;
import com.kymjs.mobile.ui.activity.TouchPadMode;

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
    private KJViewPager mContentPager;

    private Main aty;
    private String mPcIp, mPcProt, mCarIp, MCarProt;

    @Override
    protected View inflaterView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
        aty = (Main) getActivity();
        return arg0.inflate(R.layout.frag_main, null);
    }

    @Override
    protected void initData() {
        super.initData();
        mPcIp = PreferenceHelper.readString(aty, APPConfig.PREFERENCE_FILE,
                APPConfig.IP_KEY_PC, "192.168.1.100");
        mPcProt = PreferenceHelper.readString(aty, APPConfig.PREFERENCE_FILE,
                APPConfig.PROT_KEY_PC, "8899");
        mCarIp = PreferenceHelper.readString(aty, APPConfig.PREFERENCE_FILE,
                APPConfig.IP_KEY_CAR, "192.168.8.1");
        MCarProt = PreferenceHelper.readString(aty, APPConfig.PREFERENCE_FILE,
                APPConfig.PROT_KEY_CAR, "2001");
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        aty.resideMenu.addIgnoredView(mContentPager);
        mEtPcIp.setText(mPcIp);
        mEtPcPort.setText(mPcProt);
        mContentPager.setOnViewChangeListener(new OnViewChangeListener() {
            @Override
            public void OnViewChange(int view) {
                switch (view) {
                case 0:
                    mTvTitle.setText("← " + getString(R.string.touch_pad_mode)
                            + " →");
                    mEtPcIp.setText(mPcIp);
                    mEtPcPort.setText(mPcProt);
                    break;
                case 1:
                    mTvTitle.setText("← " + getString(R.string.game_pad_mode)
                            + " →");
                    mEtPcIp.setText(mPcIp);
                    mEtPcPort.setText(mPcProt);
                    break;
                case 2:
                    mTvTitle.setText("← "
                            + getString(R.string.control_pad_mode) + " →");
                    mEtPcIp.setText(mPcIp);
                    mEtPcPort.setText(mPcProt);
                    break;
                case 3:
                    mTvTitle.setText("← " + getString(R.string.car_mode) + " →");
                    mEtPcIp.setText(mCarIp);
                    mEtPcPort.setText(MCarProt);
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
                ((AppContext) aty.getApplication()).ip = mEtPcIp.getText()
                        .toString();
                ((AppContext) aty.getApplication()).port = StringUtils
                        .toInt(mEtPcPort.getText());
                recordTo();
            } else {
                ViewInject.toast(getString(R.string.connection_error));
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
        PreferenceHelper.write(aty, APPConfig.PREFERENCE_FILE,
                APPConfig.IP_KEY_PC, mEtPcIp.getText().toString());
        PreferenceHelper.write(aty, APPConfig.PREFERENCE_FILE,
                APPConfig.PROT_KEY_PC, mEtPcPort.getText().toString());
        PreferenceHelper.write(aty, APPConfig.PREFERENCE_FILE,
                APPConfig.IP_KEY_CAR, mEtPcIp.getText().toString());
        PreferenceHelper.write(aty, APPConfig.PREFERENCE_FILE,
                APPConfig.PROT_KEY_CAR, mEtPcPort.getText().toString());
    }
}
