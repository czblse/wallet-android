package io.spaco.wallet.activities.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.spaco.wallet.R;
import io.spaco.wallet.activities.WalletCreatActivity;
import io.spaco.wallet.activities.WalletDetailsActivity;
import io.spaco.wallet.base.BaseFragment;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.datas.Wallet;
import io.spaco.wallet.utils.StatusBarUtils;

/**
 * 钱包主页视图碎片
 * Created by kimi on 2018/1/25.
 */

public class MainWalletFragment extends BaseFragment implements MainWalletListener {

    RecyclerView recyclerView;
    MainWalletAdapter mainWalletAdapter;
    ImageView imgRefresh;
    TextView tvBalance;
    List<Wallet> mainWalletBeans = new ArrayList<>();

    /**
     * 钱包控制层
     */
    WalletViewModel walletViewModel = new WalletViewModel();

    /**
     * 测试数据
     */
    List<Wallet> wallets;

    public static MainWalletFragment newInstance(Bundle args) {
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
        imgRefresh = rootView.findViewById(R.id.img_refresh);

        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        mainWalletAdapter = new MainWalletAdapter(mainWalletBeans);
        mainWalletAdapter.setMainWalletListener(this);
        recyclerView.setAdapter(mainWalletAdapter);
    }

    @Override
    protected void initData() {
        mActivity.showDialog(1);
        walletViewModel.getAllWallets()
                .compose(this.<List<Wallet>>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new Observer<List<Wallet>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Wallet> wallets) {
                        walletViewModel.wallets = wallets;
                        tvBalance.setText(String.valueOf(WalletViewModel.totalBalance));
                        mainWalletAdapter.setWallets(wallets);
                        mainWalletAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onComplete();
                    }

                    @Override
                    public void onComplete() {
                        mActivity.dismissDialog(1);
                    }
                });
    }

    @Override
    public void onItemClick(int position, Wallet bean) {
        Intent intent = new Intent(getActivity(), WalletDetailsActivity.class);
        intent.putExtra(Constant.KEY_WALLET, bean);
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
        intent.putExtra(Constant.KEY_PAGE, 1);
        startActivity(intent);
    }

}
