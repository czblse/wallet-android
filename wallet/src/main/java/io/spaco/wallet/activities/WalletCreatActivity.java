package io.spaco.wallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.Wallet.WalletCreateFragment;
import io.spaco.wallet.activities.Wallet.WalletImportFragment;
import io.spaco.wallet.activities.Wallet.WalletCreateListener;
import io.spaco.wallet.base.BaseActivity;
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
        //默认切换为创建钱包
        switchFragment(walletCreateFragment);
    }

    @Override
    protected void initData() {
        //自动生成助记词
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            Mobile.newWallet(walletType, walletName, seed);
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
        ToastUtils.show("开始导入钱包");
        try {
            Mobile.newWallet(walletType, walletName, seed);
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
