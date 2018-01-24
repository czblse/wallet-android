package io.spaco.wallet.activities.wallet;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import io.spaco.wallet.R;
import io.spaco.wallet.base.BaseFragment;

/**
 * 导入钱包界面视图碎片
 * Created by kimi on 2018/1/24.
 */

public class WalletImportFragment extends BaseFragment {

    WalletListener walletListener;

    public static WalletImportFragment newInstance(Bundle args){
        WalletImportFragment instance = new WalletImportFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof WalletListener){
            walletListener = (WalletListener) context;
        }
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_import_wallet;
    }

    @Override
    protected void initViews(View rootView) {
        View tvNext = rootView.findViewById(R.id.next);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(walletListener != null){
                    walletListener.importWallet("","");
                }
            }
        });
    }

    @Override
    protected void initData() {

    }
}
