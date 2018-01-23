package io.spaco.wallet.activities;

import android.support.v4.view.ViewPager;
import android.view.View;

import io.spaco.wallet.R;
import io.spaco.wallet.adapter.PinSetAdapter;
import io.spaco.wallet.base.BaseActivity;

/**
 * Pin设置界面，用于为钱包设置Pin保护密码
 * Created by zjy on 2018/1/20.
 */

public class PinSetActivity extends BaseActivity {

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_pin_set;
    }

    @Override
    protected void initViews() {
        if(savedInstanceState == null){
            ViewPager viewPager = findViewById(R.id.viewpager);
            viewPager.setAdapter(new PinSetAdapter(getSupportFragmentManager()));
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {

    }
}
