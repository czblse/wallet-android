package io.spaco.wallet.activities.Main;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trello.rxlifecycle2.android.FragmentEvent;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.spaco.wallet.R;
import io.spaco.wallet.activities.SendCostActivity;
import io.spaco.wallet.activities.WalletCreatActivity;
import io.spaco.wallet.activities.WalletDetailsActivity;
import io.spaco.wallet.base.BaseFragment;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.datas.Wallet;
import io.spaco.wallet.push.WalletListener;
import io.spaco.wallet.push.WalletPush;
import io.spaco.wallet.utils.LogUtils;
import io.spaco.wallet.utils.NetUtil;
import io.spaco.wallet.utils.StatusBarUtils;

/**
 * 钱包主页视图碎片
 * Created by kimi on 2018/1/25.
 */

public class MainWalletFragment extends BaseFragment implements MainWalletListener {

    RecyclerView recyclerView;
    MainWalletAdapter mainWalletAdapter;
    TextView tvBalance, tvHours, tvExchangeCoin;
    List<Wallet> mainWalletBeans = new ArrayList<>();
    boolean exchangeCoinFirst = true;//标记
    boolean exchangeCoin = true;//标记，true表示cny，false表示usd

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
        Toolbar toolbar = rootView.findViewById(R.id.id_toolbar);
        toolbar.inflateMenu(R.menu.wallet_fragment);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.refresh:
                        initData();
                        break;
                    case R.id.send:
                        startSendCost();
                        break;
                }
                return true;
            }
        });
        rootView.findViewById(R.id.coin_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换对应的钱包汇率
                if (exchangeCoin)
                    initCNYexchangCoin();
                else
                    initUSDexchangCoin();
                exchangeCoin = !exchangeCoin;
            }
        });
        tvExchangeCoin = rootView.findViewById(R.id.exchange_coin);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        tvBalance = rootView.findViewById(R.id.tv_balance);
        tvHours = rootView.findViewById(R.id.tv_hours);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        mainWalletAdapter = new MainWalletAdapter(mainWalletBeans);
        mainWalletAdapter.setMainWalletListener(this);
        recyclerView.setAdapter(mainWalletAdapter);
        //添加观察者
        WalletPush.getInstance().addObserver(listener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //移除观察者
        WalletPush.getInstance().deleteObserver(listener);
    }

    private WalletListener listener = new WalletListener() {
        @Override
        protected void walletUpdate() {
            initData();
        }

        @Override
        protected void transactionUpdate() {

        }

        @Override
        protected void balanceUpdate() {

        }
    };

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
                        tvHours.setText(String.valueOf(WalletViewModel.totalHours + " SKY Hours"));
                        mainWalletAdapter.setWallets(wallets);
                        mainWalletAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        mActivity.dismissDialog(1);
                        //初始化相应的钱包汇率
                        String language = Locale.getDefault().getLanguage();
                        if (exchangeCoinFirst) {
                            exchangeCoinFirst = false;
                            if ("zh".equals(language)) {
                                exchangeCoin = true;
                                initCNYexchangCoin();
                            } else {
                                initUSDexchangCoin();
                                exchangeCoin = false;
                            }
                        }
                    }
                });
    }

    /**
     * 初始化钱包汇率CNY
     */
    private void initCNYexchangCoin() {
        walletViewModel.getCNYcoinExchange()
                .compose(this.<String>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        try {
                            double price_cny = new JSONArray(s).getJSONObject(0).optDouble("price_cny");
                            double value = WalletViewModel.totalBalance * price_cny;
                            DecimalFormat decimalFormat = new DecimalFormat(".00");
                            tvExchangeCoin.setText("￥" + decimalFormat.format(value));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 初始化钱包汇率USD
     */
    private void initUSDexchangCoin() {
        walletViewModel.getUSDcoinExchange()
                .compose(this.<String>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        try {
                            double price_usd = new JSONArray(s).getJSONObject(0).optDouble("price_usd");
                            double value = WalletViewModel.totalBalance * price_usd;
                            DecimalFormat decimalFormat = new DecimalFormat(".00");
                            tvExchangeCoin.setText("$" + decimalFormat.format(value));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

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
        startActivityForResult(intent, 1);

    }

    @Override
    public void onImportWallet() {
        Intent intent = new Intent(getActivity(), WalletCreatActivity.class);
        intent.putExtra(Constant.KEY_PAGE, 1);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        initData();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 开始发送交易
     */
    private void startSendCost() {
        try {
            Intent intent = new Intent(getActivity(), SendCostActivity.class);
            intent.putExtra(Constant.KEY_WALLET, walletViewModel.wallets.get(0));
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.send_cost_in, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
