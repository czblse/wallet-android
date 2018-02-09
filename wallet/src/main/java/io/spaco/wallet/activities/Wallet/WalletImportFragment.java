package io.spaco.wallet.activities.Wallet;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.spaco.wallet.R;
import io.spaco.wallet.api.Const;
import io.spaco.wallet.base.BaseFragment;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.utils.SpacoWalletUtils;

/**
 * 导入钱包界面视图碎片
 * Created by kimi on 2018/1/24.
 */

public class WalletImportFragment extends BaseFragment {

    WalletCreateListener walletListener;
    EditText mEdtWalletName,mEdtseed;
    public static WalletImportFragment newInstance(Bundle args){
        WalletImportFragment instance = new WalletImportFragment();
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
        return R.layout.fragment_import_wallet;
    }

    @Override
    protected void initViews(View rootView) {
        View tvNext = rootView.findViewById(R.id.next);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String walletNameString = mEdtWalletName.getText().toString();
                String importSeedString = mEdtseed.getText().toString();
                if (checkWalletName(Constant.COIN_TYPE_SKY, walletNameString, importSeedString)) {
                    if (walletListener != null) {
                        walletListener.importWallet(walletNameString, importSeedString,"");
                    }
                }
            }
        });
        mEdtseed = rootView.findViewById(R.id.seed);
        mEdtWalletName = rootView.findViewById(R.id.wallet_name);
    }

    private boolean checkWalletName(String coinType, String walletName, String seed){
        if (TextUtils.isEmpty(walletName)){
            mEdtWalletName.requestFocus();
            Toast.makeText(this.getActivity(), getResources().getString(R.string.pls_input_wallet_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(seed)){
            mEdtWalletName.requestFocus();
            Toast.makeText(this.getActivity(), getResources().getString(R.string.pls_input_wallet_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (SpacoWalletUtils.isWalletExist(coinType, walletName)){
            mEdtWalletName.requestFocus();
            Toast.makeText(this.getActivity(), getResources().getString(R.string.wallet_is_existed), Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }
    @Override
    protected void initData() {

    }
}
