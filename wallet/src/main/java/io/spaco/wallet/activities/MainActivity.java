package io.spaco.wallet.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.tencent.bugly.beta.Beta;

import java.util.List;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.Transaction.TransactionFragment;
import io.spaco.wallet.activities.Main.MainWalletFragment;
import io.spaco.wallet.base.BaseActivity;
import io.spaco.wallet.common.Constant;

/**
 * 主界面，一级界面
 */
public class MainActivity extends BaseActivity {

    MainWalletFragment mainWalletFragment;
    TransactionFragment mainTransactionFragment;
    private View createWallet, importWallet;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

        mainWalletFragment = MainWalletFragment.newInstance(null);
        mainTransactionFragment = TransactionFragment.newInstance(null);
        createWallet = findViewById(R.id.new_wallet);
        importWallet = findViewById(R.id.import_wallet);
        createWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateWallet();
            }
        });

        importWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImportWallet();
            }
        });
        switchFragment(mainWalletFragment);
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
            fragmentTransaction.add(R.id.main_container,fragment,tag);
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
    protected void initData() {
        Beta.checkUpgrade();
    }
    public void onCreateWallet() {
        Intent intent = new Intent(this, WalletCreatActivity.class);
        startActivity(intent);
    }

    public void onImportWallet() {
        Intent intent = new Intent(this, WalletCreatActivity.class);
        intent.putExtra(Constant.KEY_PAGE, 1);
        startActivity(intent);
    }


}
