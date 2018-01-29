package io.spaco.wallet.activities.Wallet;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.security.SecureRandom;
import java.util.ArrayList;

import io.spaco.wallet.R;
import io.spaco.wallet.base.BaseFragment;
import io.spaco.wallet.datas.bip39.MnemonicGenerator;
import io.spaco.wallet.datas.bip39.Words;
import io.spaco.wallet.datas.bip39.wordlists.English;
import io.spaco.wallet.utils.StringUtils;
import mobile.Mobile;

/**
 * 创建钱包界面视图碎片
 * Created by kimi on 2018/1/24.
 */

public class WalletCreateFragment extends BaseFragment {
    EditText editTextSeedShow,editTextTextSeedInput;
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
        editTextSeedShow = rootView.findViewById(R.id.ed_seed);
        generateSeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] entropy = new byte[Words.TWELVE.byteLength()];

                new SecureRandom().nextBytes(entropy);
                ArrayList<String> result = new MnemonicGenerator(English.INSTANCE)
                        .createMnemonicList(entropy);
                String string = StringUtils.converListToString(result);
                editTextSeedShow.setText(Mobile.newSeed());
                generateSeed.setVisibility(View.GONE);
            }
        });
        editTextTextSeedInput = rootView.findViewById(R.id.confirm_seed);
        View tvNext = rootView.findViewById(R.id.next);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(walletListener != null && checkInputSeed()){
                    walletListener.createWallet("","");
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

    @Override
    protected void initData() {

    }
}
