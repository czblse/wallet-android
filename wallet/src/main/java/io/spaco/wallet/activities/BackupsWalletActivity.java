package io.spaco.wallet.activities;

import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.BackupsWallet.BackupsWalletFragment;
import io.spaco.wallet.activities.PIN.PinInputFragment;
import io.spaco.wallet.activities.PIN.PinSetListener;
import io.spaco.wallet.activities.PIN.VerifyPinSetFragment;
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

    private PinInputFragment inputFragment;

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
        if (savedInstanceState == null) {
            inputFragment = PinInputFragment.newInstance(null);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, inputFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void initData() {

    }

    //pin输入次数
    private int pinNum;

    @Override
    public void onPinSetSuccess(String pin) {
        if (SpacoWalletUtils.returnPinHint()) {
            if (TextUtils.equals(pin, SpacoWalletUtils.getPin())) {
                getSupportActionBar().show();
                BackupsWalletFragment backupsWalletFragment = BackupsWalletFragment.newInstance(getIntent().getExtras());
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, backupsWalletFragment);
                fragmentTransaction.commit();
            } else {
                pinNum++;
                if (pinNum == 3) {
                    ToastUtils.show(getResources().getString(R.string.input_pinnum_error));
                    SpacoWalletUtils.setPinTime();
                } else {
                    if ((3 - pinNum) == 2) {
                        ToastUtils.show(getResources().getString(R.string.input_pinnum_two));
                    } else if ((3 - pinNum) == 1) {
                        ToastUtils.show(getResources().getString(R.string.input_pinnum_one));
                    }
                }
                clearInput();
            }

        } else {
            int time = SpacoWalletUtils.getOutPinTime();
            ToastUtils.show(getResources().getString(R.string.input_pintime) + time + getResources().getString(R.string.input_pinminut));
            clearInput();
        }
    }

    private void clearInput() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                inputFragment.clearInput();
            }
        }, 300);
    }

    @Override
    public void onPinSetVerifySuccess(String verifyPin) {

    }

    @Override
    public void onPinReset() {

    }
}
