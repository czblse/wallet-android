package io.spaco.wallet.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.Main.MainTransactionFragment;
import io.spaco.wallet.activities.Main.MainWalletFragment;
import io.spaco.wallet.base.BaseActivity;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.widget.SendKeyDialog;

/**
 * 主界面，一级界面
 */
public class MainActivity extends BaseActivity {

    /**
     * 底部导航选中位置
     */
    int bottomSelectedIndex = 0;

    MainWalletFragment mainWalletFragment;
    MainTransactionFragment mainTransactionFragment;
    private View createWallet, importWallet;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

        mainWalletFragment = MainWalletFragment.newInstance(null);
        mainTransactionFragment = MainTransactionFragment.newInstance(null);
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

    /**
     * 开始发送交易
     */
    private void startSendCost() {
        Intent intent = new Intent(this, SendCostActivity.class);
        startActivity(intent);
    }

    /**
     * 碎片视图切换回掉
     * @param index
     */
    public void onBottomContainerSelected(int index){
        if(index == 0){
            switchFragment(mainWalletFragment);
        }else if(index == 1){
            startSendCost();
        }else if(index == 2){
            switchFragment(mainTransactionFragment);
        }
    }

    @Override
    protected void initData() {

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
