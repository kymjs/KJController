package com.kymjs.mobile.ui.fragment;

import org.kymjs.aframe.ui.fragment.BaseFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kymjs.mobile.R;

public class SetKey extends BaseFragment {
    
    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup arg1,
            Bundle arg2) {
        return inflater.inflate(R.layout.frag_setkey, null);
    }
}
