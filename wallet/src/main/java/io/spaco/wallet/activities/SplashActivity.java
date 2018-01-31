package io.spaco.wallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.ArrayList;

import io.spaco.wallet.R;
import io.spaco.wallet.base.BaseActivity;
import io.spaco.wallet.datas.bip39.MnemonicGenerator;
import io.spaco.wallet.datas.bip39.Words;
import io.spaco.wallet.datas.bip39.wordlists.English;
import io.spaco.wallet.utils.AppUtils;
import io.spaco.wallet.utils.SpacoWalletUtils;
import io.spaco.wallet.utils.StatusBarUtils;

/**
 * Created by zjy on 2018/1/20.
 */

public class SplashActivity extends BaseActivity {

    Handler handler = new Handler();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {
        TextView mVersionName = findViewById(R.id.version_name);
        mVersionName.setText(AppUtils.getVersionName(getApplicationContext()));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                launchToNextAcitivity();
                finish();
            }
        }, 1500);
    }

    @Override
    protected void initData() {
    }


    /**
     * 如果pin没有设定的话，跳到pin设置页面
     * 如果没有创建钱包，则跳转到pin输入页面
     */
    private void launchToNextAcitivity() {
        if (SpacoWalletUtils.isPinSet()) {
            launchToPinInputActvity();
        } else {
            launchToPinSetActivity();
        }
    }

    private void launchToPinSetActivity() {
        Intent intent = new Intent(this, WalletDetailsActivity.class);
        startActivity(intent);
    }

    private void launchToPinInputActvity() {
        Intent intent = new Intent(this, PinSetActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }
}
