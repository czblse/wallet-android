package io.spaco.wallet.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.spaco.wallet.R;
import io.spaco.wallet.activities.Main.WalletViewModel;
import io.spaco.wallet.activities.WalletDetails.WalletDetailsAdapter;
import io.spaco.wallet.activities.WalletDetails.WalletDetailsListener;
import io.spaco.wallet.base.BaseActivity;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.datas.Address;
import io.spaco.wallet.datas.Wallet;
import io.spaco.wallet.utils.SpacoWalletUtils;
import io.spaco.wallet.utils.StatusBarUtils;
import io.spaco.wallet.utils.ToastUtils;
import io.spaco.wallet.widget.ShowQrDialog;
import mobile.Mobile;

public class WalletDetailsActivity extends BaseActivity implements WalletDetailsListener {

    RecyclerView recyclerView;
    WalletDetailsAdapter walletDetailsAdapter;
    ArrayList<Address> walletDetailsBeans = new ArrayList<>();

    Wallet wallet;
    /**
     * 钱包控制层
     */
    WalletViewModel walletViewModel = new WalletViewModel();

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_wallet_details;
    }

    @Override
    protected void initViews() {
        StatusBarUtils.statusBarCompat(this);
        recyclerView = findViewById(R.id.recyclerview);
        Toolbar toolbar = findViewById(R.id.id_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.img_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wallet  = (Wallet) getIntent().getSerializableExtra(Constant.KEY_WALLET);

        walletDetailsAdapter = new WalletDetailsAdapter(walletDetailsBeans);
        walletDetailsAdapter.setWalletDetailsListener(this);
        recyclerView.setAdapter(walletDetailsAdapter);
    }

    @Override
    protected void initData() {
        showDialog(1);
        walletViewModel.getAllAddressByWalletId(wallet.getWalletType(),wallet.getWalletID())
                .compose(this.<List<Address>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<List<Address>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Address> addresses) {
                        walletDetailsAdapter.setWalletDetails(addresses);
                        walletDetailsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onComplete();
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog(1);
                    }
                });
    }

    @Override
    public void onItemClick(int position, Address bean) {
        ShowQrDialog showQrDialog = new ShowQrDialog(this);
        showQrDialog.setKey(bean.getAddress());
        showQrDialog.show();
    }

    @Override
    public void onCreateAddress() {
        try {
            Mobile.newAddress(wallet.getWalletID(), 1, SpacoWalletUtils.getPin16());
            initData();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.show("地址创建失败");
        }
    }
}
