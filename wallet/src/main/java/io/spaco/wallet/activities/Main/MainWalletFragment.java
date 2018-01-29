package io.spaco.wallet.activities.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.WalletCreatActivity;
import io.spaco.wallet.activities.WalletDetailsActivity;
import io.spaco.wallet.base.BaseFragment;
import io.spaco.wallet.beans.MainWalletBean;
import io.spaco.wallet.utils.StatusBarUtils;

/**
 * 钱包主页视图碎片
 * Created by kimi on 2018/1/25.
 */

public class MainWalletFragment extends BaseFragment implements MainWalletListener{

    RecyclerView recyclerView;
    MainWalletAdapter mainWalletAdapter;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        ArrayList<MainWalletBean> mainWalletBeans = new ArrayList<>();
        mainWalletBeans.add(new MainWalletBean());
        mainWalletBeans.add(new MainWalletBean());
        mainWalletBeans.add(new MainWalletBean());
        mainWalletAdapter = new MainWalletAdapter(mainWalletBeans);
        mainWalletAdapter.setMainWalletListener(this);
        recyclerView.setAdapter(mainWalletAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onItemClick(int position, MainWalletBean bean) {
        Intent intent = new Intent(getActivity(), WalletDetailsActivity.class);
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
}
