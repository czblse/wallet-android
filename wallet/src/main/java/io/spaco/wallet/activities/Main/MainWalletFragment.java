package io.spaco.wallet.activities.Main;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
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
import io.spaco.wallet.utils.ToastUtils;

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
    SwipeRefreshLayout refresh;
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
        refresh = rootView.findViewById(R.id.refresh);
        toolbar.inflateMenu(R.menu.wallet_fragment);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.refresh:
                        refresh.post(new Runnable() {
                            @Override
                            public void run() {
                                refresh.setRefreshing(true);
                                refreshDatas();
                            }
                        });
                        break;
                    case R.id.send:
                        startSendCost();
                        break;
                    case R.id.version:
                        chcekUp();
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
        refresh.setColorSchemeColors(Color.parseColor("#0075FF"), Color.parseColor("#00B9FF"));

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDatas();
            }
        });
    }

    @Override
    protected void initData() {
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

    /**
     * 刷新数据
     */
    private void refreshDatas() {
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
                        refresh.setRefreshing(false);
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

    @Override
    public void onResume() {
        super.onResume();
        refresh.setRefreshing(true);
        refreshDatas();
    }

    /**
     * 检测更新
     */
    private void chcekUp() {
        Beta.checkUpgrade();
        //loadUpgradeInfo();
    }

    /**
     * 获取升级信息
     */
    private void loadUpgradeInfo() {

        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();

        if (upgradeInfo == null) {
            return;
        }
        StringBuilder info = new StringBuilder();
        info.append("id: ").append(upgradeInfo.id).append("\n");
        info.append("标题: ").append(upgradeInfo.title).append("\n");
        info.append("升级说明: ").append(upgradeInfo.newFeature).append("\n");
        info.append("versionCode: ").append(upgradeInfo.versionCode).append("\n");
        info.append("versionName: ").append(upgradeInfo.versionName).append("\n");
        info.append("发布时间: ").append(upgradeInfo.publishTime).append("\n");
        info.append("安装包Md5: ").append(upgradeInfo.apkMd5).append("\n");
        info.append("安装包下载地址: ").append(upgradeInfo.apkUrl).append("\n");
        info.append("安装包大小: ").append(upgradeInfo.fileSize).append("\n");
        info.append("弹窗间隔（ms）: ").append(upgradeInfo.popInterval).append("\n");
        info.append("弹窗次数: ").append(upgradeInfo.popTimes).append("\n");
        info.append("发布类型（0:测试 1:正式）: ").append(upgradeInfo.publishType).append("\n");
        info.append("弹窗类型（1:建议 2:强制 3:手工）: ").append(upgradeInfo.upgradeType);
        ToastUtils.show(info.toString());
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
