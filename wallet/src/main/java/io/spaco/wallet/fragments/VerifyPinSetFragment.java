package io.spaco.wallet.fragments;

import android.os.Bundle;
import android.view.View;

import io.spaco.wallet.R;
import io.spaco.wallet.base.BaseFragment;

/**
 * Pin确认界面碎片
 * Created by kimi on 2018/1/23.
 */

public class VerifyPinSetFragment extends BaseFragment {

    public static VerifyPinSetFragment newInstance(Bundle args){
        VerifyPinSetFragment instance = new VerifyPinSetFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_pin_set;
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected void initData() {

    }
}
