package io.spaco.wallet.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import io.spaco.wallet.datas.WalletManager;
import io.spaco.wallet.push.WalletPush;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wallet  = (Wallet) getIntent().getSerializableExtra(Constant.KEY_WALLET);
        //设置钱包余额
        ((TextView)findViewById(R.id.tv_balance)).setText(wallet.getBalance());
        ((TextView)findViewById(R.id.tv_sky_hours)).setText(wallet.getHours()+" SKY Hours");

        walletDetailsAdapter = new WalletDetailsAdapter(walletDetailsBeans);
        walletDetailsAdapter.setWalletDetailsListener(this);
        recyclerView.setAdapter(walletDetailsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wallet_details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:
                initData();
                break;
            case R.id.createAddress:
                try {
                    Mobile.newAddress(wallet.getWalletID(), 1, SpacoWalletUtils.getPin16());
                    initData();
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.show(getString(R.string.str_create_address_failed));
                }
                break;
            case R.id.transaction:
                Intent intent = new Intent(this, TransactionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.KEY_WALLET,wallet);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.backups:
                Intent intent2 = new Intent(this, BackupsWalletActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable(Constant.KEY_WALLET,wallet);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                break;
            case R.id.delete:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.delete_wallet)
                        .setMessage(R.string.delete_wallet_tips)
                        .setPositiveButton(R.string.delete_confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteWallet();
                            }
                        })
                        .setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).setCancelable(false).show();
                break;
        }
        return true;
    }

    /**
     * 删除钱包
     */
    public void deleteWallet(){
        showDialog(1);
        walletViewModel.deleteWallet(wallet)
                .compose(this.<Boolean>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        //通知首页钱包进行更新
                        WalletPush.getInstance().walletUpdate();
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(getString(R.string.delete_wallet_error_msg));
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog(1);
                    }
                });
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
            ToastUtils.show(getString(R.string.str_create_address_failed));
        }
    }
}
