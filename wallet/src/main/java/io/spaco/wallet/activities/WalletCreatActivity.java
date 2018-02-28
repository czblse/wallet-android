package io.spaco.wallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.Wallet.WalletCreateFragment;
import io.spaco.wallet.activities.Wallet.WalletImportFragment;
import io.spaco.wallet.activities.Wallet.WalletCreateListener;
import io.spaco.wallet.base.BaseActivity;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.datas.Address;
import io.spaco.wallet.datas.Wallet;
import io.spaco.wallet.datas.WalletManager;
import io.spaco.wallet.push.WalletPush;
import io.spaco.wallet.utils.LogUtils;
import io.spaco.wallet.utils.SpacoWalletUtils;
import io.spaco.wallet.utils.StatusBarUtils;
import io.spaco.wallet.utils.ToastUtils;
import mobile.Mobile;

/**
 * 创建或导入钱包界面
 * Created by zjy on 2018/1/20.
 */

public class WalletCreatActivity extends BaseActivity implements WalletCreateListener {

    RadioGroup radioGroup;
    /**
     * 创建钱包碎片视图
     */
    WalletCreateFragment walletCreateFragment;
    /**
     * 导入钱包碎片视图
     */
    WalletImportFragment walletImportFragment;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_new_wallet;
    }

    @Override
    protected void initViews() {
        StatusBarUtils.statusBarCompat(this);
        radioGroup = findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.create_wallet){
                    switchFragment(walletCreateFragment);
                }else if(checkedId == R.id.import_wallet){
                    switchFragment(walletImportFragment);
                }
            }
        });

        walletCreateFragment = WalletCreateFragment.newInstance(null);
        walletImportFragment = WalletImportFragment.newInstance(null);

        //选中指定界面
        int page = getIntent().getIntExtra(Constant.KEY_PAGE,0);
        if(page == 0)
            ((RadioButton)radioGroup.findViewById(R.id.create_wallet)).setChecked(true);
        else
            ((RadioButton)radioGroup.findViewById(R.id.import_wallet)).setChecked(true);

    }

    @Override
    protected void initData() {

    }

    /**
     * 切换碎片视图
     * @param fragment
     */
    private void switchFragment(Fragment fragment){
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment instance = getSupportFragmentManager().findFragmentByTag(tag);
        if(instance == null){
            fragmentTransaction.add(R.id.container,fragment,tag);
        }
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if(fragments != null){
            for(Fragment item : fragments){
                if(item.equals(fragment)){
                    fragmentTransaction.show(item);
                }else{
                    fragmentTransaction.hide(item);
                }
            }
        }
        fragmentTransaction.commit();
    }

    @Override
    public boolean createWallet(String walletType, String walletName, String seed) {
        try {
            String walletId = Mobile.newWallet(walletType, walletName, seed, SpacoWalletUtils.getPin16());
            Wallet wallet = new Wallet(walletType, walletName, walletId);
            wallet.save();
            //发送通知
            WalletPush.getInstance().walletUpdate();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean importWallet(String walletType, String walletName, String seed) {
        try {
            String walletId = Mobile.newWallet(walletType, walletName, seed, SpacoWalletUtils.getPin16());
            Wallet wallet = new Wallet(walletType, walletName, walletId);
            wallet.save();
            //发送通知
            WalletPush.getInstance().walletUpdate();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
