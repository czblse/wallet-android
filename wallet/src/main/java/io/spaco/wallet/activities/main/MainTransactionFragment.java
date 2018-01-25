package io.spaco.wallet.activities.main;

import android.os.Bundle;
import android.view.View;

import io.spaco.wallet.R;
import io.spaco.wallet.base.BaseFragment;

/**
 * 交易记录视图碎片
 * Created by kimi on 2018/1/25.
 */

public class MainTransactionFragment extends BaseFragment {

    public static MainTransactionFragment newInstance(Bundle args){
        MainTransactionFragment instance = new MainTransactionFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_main_transaction;
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected void initData() {

    }
}
