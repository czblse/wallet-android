package io.spaco.wallet.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.WalletDetails.WalletDetailsAdapter;
import io.spaco.wallet.activities.WalletDetails.WalletDetailsListener;
import io.spaco.wallet.base.BaseActivity;
import io.spaco.wallet.beans.WalletDetailsBean;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.datas.Address;
import io.spaco.wallet.datas.WalletManager;
import io.spaco.wallet.utils.StatusBarUtils;
import io.spaco.wallet.utils.ToastUtils;
import io.spaco.wallet.widget.ShowQrDialog;
import mobile.Mobile;

public class WalletDetailsActivity extends BaseActivity implements WalletDetailsListener {

    RecyclerView recyclerView;
    WalletDetailsAdapter walletDetailsAdapter;
    String walletId;
    ArrayList<Address> walletDetailsBeans = new ArrayList<>();

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

        walletDetailsAdapter = new WalletDetailsAdapter(walletDetailsBeans);
        walletDetailsAdapter.setWalletDetailsListener(this);
        recyclerView.setAdapter(walletDetailsAdapter);
    }

    @Override
    protected void initData() {
        walletId  = getIntent().getStringExtra(Constant.KEY_WALLET_ID);
        loadAddressMsg(walletId);
    }

    @Override
    public void onItemClick(int position, Address bean) {
        ShowQrDialog showQrDialog = new ShowQrDialog(this);
        showQrDialog.setKey(bean.getAddress());
        showQrDialog.show();
    }

    private void loadAddressMsg(String walletId){
        if (!TextUtils.isEmpty(walletId)) {
            walletDetailsAdapter.setWalletDetails(WalletManager.getInstance().getAddressesByWalletId(Constant.COIN_TYPE_SKY, walletId));
            walletDetailsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateAddress() {
        try {
            Mobile.newAddress(walletId, 1);
            loadAddressMsg(walletId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
