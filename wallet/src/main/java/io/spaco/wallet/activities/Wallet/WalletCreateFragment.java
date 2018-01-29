package io.spaco.wallet.activities.Wallet;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import io.spaco.wallet.R;
import io.spaco.wallet.base.BaseFragment;

/**
 * 创建钱包界面视图碎片
 * Created by kimi on 2018/1/24.
 */

public class WalletCreateFragment extends BaseFragment {

    WalletCreateListener walletListener;

    public static WalletCreateFragment newInstance(Bundle args){
        WalletCreateFragment instance = new WalletCreateFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof WalletCreateListener){
            walletListener = (WalletCreateListener) context;
        }
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_create_wallet;
    }

    @Override
    protected void initViews(View rootView) {
        View tvNext = rootView.findViewById(R.id.next);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(walletListener != null){
                    walletListener.createWallet("","");
                }
            }
        });
    }

    @Override
    protected void initData() {

    }
}
