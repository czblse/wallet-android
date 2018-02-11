package io.spaco.wallet.activities.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.WalletCreatActivity;
import io.spaco.wallet.activities.WalletDetailsActivity;
import io.spaco.wallet.base.BaseFragment;
import io.spaco.wallet.beans.MainWalletBean;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.datas.Wallet;
import io.spaco.wallet.datas.WalletManager;
import io.spaco.wallet.utils.StatusBarUtils;
import mobile.Mobile;

/**
 * 钱包主页视图碎片
 * Created by kimi on 2018/1/25.
 */

public class MainWalletFragment extends BaseFragment implements MainWalletListener{

    RecyclerView recyclerView;
    MainWalletAdapter mainWalletAdapter;
    List<Wallet> mainWalletBeans = new ArrayList<>();
    private float accountBalance = 0;//账户余额
    List<Wallet> wallets;
    TextView tvBalance;
    public static MainWalletFragment newInstance(Bundle args){
        MainWalletFragment instance = new MainWalletFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_main_wallet;
    }

    @Override
    protected void initViews(View rootView) {
        StatusBarUtils.statusBarCompat(this);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        tvBalance = rootView.findViewById(R.id.tv_balance);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        mainWalletAdapter = new MainWalletAdapter(mainWalletBeans);
        mainWalletAdapter.setMainWalletListener(this);
        recyclerView.setAdapter(mainWalletAdapter);
    }

    @Override
    protected void initData() {
        mainWalletBeans = restoreWalletFromLocal();
        if (mainWalletBeans == null || mainWalletBeans.size() == 0){
            Wallet wallet = Wallet.buildTestData();
            wallets = new ArrayList<>();
            wallets.add(wallet);
        }
        float walletBalance;
        for (Wallet wallet : mainWalletBeans) {
            try {
                walletBalance = Wallet.getBalanceFromRawData(Mobile.getWalletBalance(Constant.COIN_TYPE_SKY, wallet.getWalletID()));
                accountBalance += walletBalance;
                wallet.setBalance(String.valueOf(walletBalance));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        tvBalance.setText( accountBalance+"");
        mainWalletAdapter.setWallets(mainWalletBeans);
        mainWalletAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position, Wallet bean) {
        Intent intent = new Intent(getActivity(), WalletDetailsActivity.class);
        intent.putExtra(Constant.KEY_WALLET_ID, bean.getWalletID());
        startActivity(intent);
    }

    @Override
    public void onCreateWallet() {
        Intent intent = new Intent(getActivity(), WalletCreatActivity.class);
        startActivity(intent);
    }

    @Override
    public void onImportWallet() {
        Intent intent = new Intent(getActivity(), WalletCreatActivity.class);
        startActivity(intent);
    }
    private List<Wallet> restoreWalletFromLocal(){
        return WalletManager.getInstance().getAllWallet();
    }
}
