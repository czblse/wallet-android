package io.spaco.wallet.activities.Wallet;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.spaco.wallet.R;
import io.spaco.wallet.base.BaseFragment;
import io.spaco.wallet.utils.SpacoWalletUtils;
import mobile.Mobile;

/**
 * 创建钱包界面视图碎片
 * Created by kimi on 2018/1/24.
 */

public class WalletCreateFragment extends BaseFragment {
    EditText editTextMobileName,editTextSeedShow,editTextTextSeedInput;
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

        final View generateSeed = rootView.findViewById(R.id.generate_seed);
        editTextMobileName = rootView.findViewById(R.id.mobile_name);
        editTextSeedShow = rootView.findViewById(R.id.ed_seed);
        generateSeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seed = Mobile.newSeed();
                editTextSeedShow.setText(seed);
                generateSeed.setVisibility(View.GONE);
            }
        });
        editTextTextSeedInput = rootView.findViewById(R.id.confirm_seed);
        View tvNext = rootView.findViewById(R.id.next);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String walletName = editTextMobileName.getText().toString();
                if(checkWalletName("skycoin", walletName) && walletListener != null && checkInputSeed()){
                    walletListener.createWallet("skycoin", walletName, editTextSeedShow.getText().toString());
                }
            }
        });
    }
    private boolean checkInputSeed(){
        if (editTextSeedShow != null && editTextTextSeedInput != null){
            String input = editTextSeedShow.getText().toString();
            String output = editTextTextSeedInput.getText().toString();
            if (TextUtils.equals(input.trim(), output.trim())){
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean checkWalletName(String coinType, String walletName){
        if (TextUtils.isEmpty(walletName)){
            editTextMobileName.requestFocus();
            Toast.makeText(this.getActivity(), getResources().getString(R.string.pls_input_wallet_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (SpacoWalletUtils.isWalletExist(coinType, walletName)){
            editTextMobileName.requestFocus();
            Toast.makeText(this.getActivity(), getResources().getString(R.string.wallet_is_existed), Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }
    @Override
    protected void initData() {

    }
}
