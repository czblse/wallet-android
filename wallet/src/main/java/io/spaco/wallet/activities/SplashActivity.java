package io.spaco.wallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import io.spaco.wallet.R;
import io.spaco.wallet.base.BaseActivity;

/**
 * Created by zjy on 2018/1/20.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, PinSetActivity.class));
            }
        },3000);
    }

    /**
     * 如果pin没有设定的话，跳到pin设置页面
     * 如果没有创建钱包，则跳转到pin输入页面
     */
    private void launchToNextAcitivity(){

    }
}
