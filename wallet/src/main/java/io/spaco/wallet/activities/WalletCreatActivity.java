package io.spaco.wallet.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;

import java.util.List;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.wallet.WalletCreateFragment;
import io.spaco.wallet.activities.wallet.WalletImportFragment;
import io.spaco.wallet.activities.wallet.WalletListener;
import io.spaco.wallet.base.BaseActivity;
import io.spaco.wallet.base.BaseFragment;
import io.spaco.wallet.utils.StatusBarUtils;
import io.spaco.wallet.utils.ToastUtils;
import io.spaco.wallet.widget.DisclaimerDialog;
import io.spaco.wallet.widget.ShowQrDialog;

/**
 * 创建或导入钱包界面
 * Created by zjy on 2018/1/20.
 */

public class WalletCreatActivity extends BaseActivity implements WalletListener {

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
    DisclaimerDialog dialog;
    ShowQrDialog mDialog;
    @Override
    public void createWallet(String walletName, String seed) {
        ToastUtils.show("开始创建钱包");
        dialog = new DisclaimerDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.img_exit:
                        dialog.dismiss();
                        break;
                    case R.id.tx_continue:
                        if (dialog.isCheck()){
                            dialog.dismiss();
                            ToastUtils.show("已同意声明");
                        }else {
                            ToastUtils.show("未同意");
                        }
                        break;
                        default:

                }
            }
        });
        dialog.show();

    }

    @Override
    public void importWallet(String walletName, String seed) {
        ToastUtils.show("开始导入钱包");
        mDialog = new ShowQrDialog(this);
        mDialog.show();
        mDialog.setKey("12dad9sad8192e921je212jd");

    }
}
