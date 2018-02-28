package io.spaco.wallet.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.BackupsWallet.BackupsWalletFragment;
import io.spaco.wallet.activities.PIN.PinInputFragment;
import io.spaco.wallet.activities.PIN.PinSetListener;
import io.spaco.wallet.base.BaseActivity;
import io.spaco.wallet.utils.SpacoWalletUtils;
import io.spaco.wallet.utils.ToastUtils;

/**
 * 备份钱包
 */
public class BackupsWalletActivity extends BaseActivity implements PinSetListener {


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_backups_wallet;
    }

    @Override
    protected void initViews() {
        Toolbar toolbar = findViewById(R.id.id_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(savedInstanceState == null){
            PinInputFragment pinInputFragment = PinInputFragment.newInstance(null);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container,pinInputFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onPinSetSuccess(String pin) {
        if (TextUtils.equals(pin, SpacoWalletUtils.getPin())) {
            getSupportActionBar().show();
            BackupsWalletFragment backupsWalletFragment = BackupsWalletFragment.newInstance(getIntent().getExtras());
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container,backupsWalletFragment);
            fragmentTransaction.commit();
        }else{
            ToastUtils.show(getString(R.string.pin_error));
        }
    }

    @Override
    public void onPinSetVerifySuccess(String verifyPin) {

    }

    @Override
    public void onPinReset() {

    }
}
