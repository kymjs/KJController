package com.kymjs.mobile.ui.fragment;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.ViewInject;
import org.kymjs.aframe.ui.fragment.BaseFragment;
import org.kymjs.aframe.utils.SystemTool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kymjs.mobile.AppContext;
import com.kymjs.mobile.R;
import com.kymjs.mobile.ui.activity.ControlActivity;
import com.kymjs.mobile.ui.activity.Main;

public class MainFragment extends BaseFragment {
    
    @BindView(id = R.id.main_et_pcip)
    private EditText mEtPcIp;
    @BindView(id = R.id.main_btn_submit, click = true)
    private Button mBtnSubmit;
    
    private Main aty;
    
    @Override
    protected View inflaterView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
        aty = (Main) getActivity();
        return arg0.inflate(R.layout.frag_main, null);
    }
    
    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        mEtPcIp.setText("192.168.1.");
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
                aty.showActivity(aty, ControlActivity.class);
            } else {
                ViewInject.toast(getString(R.string.connection_error));
            }
        }
    }
}
