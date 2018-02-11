package io.spaco.wallet.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.PIN.PinInputFragment;
import io.spaco.wallet.activities.PIN.PinSetFragment;
import io.spaco.wallet.activities.PIN.PinSetListener;
import io.spaco.wallet.activities.PIN.VerifyPinSetFragment;
import io.spaco.wallet.base.BaseActivity;
import io.spaco.wallet.beans.WalletDetailsBean;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.datas.Wallet;
import io.spaco.wallet.utils.JsonUtils;
import io.spaco.wallet.utils.LogUtils;
import io.spaco.wallet.utils.SpacoWalletUtils;
import io.spaco.wallet.utils.ToastUtils;
import io.spaco.wallet.widget.DisclaimerDialog;

/**
 * Pin设置界面，用于为钱包设置Pin保护密码
 * Created by zjy on 2018/1/20.
 */

public class PinSetActivity extends BaseActivity implements PinSetListener {

    private String pinCode;
    private DisclaimerDialog mDialog;
    Handler handler = new Handler();


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_pin_set;
    }

    @Override
    protected void initViews() {
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            if (SpacoWalletUtils.isPinSet()){
                fragmentTransaction.add(R.id.container, PinInputFragment.newInstance(null),
                        PinInputFragment.class.getSimpleName());
            }else {
                fragmentTransaction.add(R.id.container, PinSetFragment.newInstance(null),
                        PinSetFragment.class.getSimpleName());
            }
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

    }

    @Override
    protected void initData() {
        isShowDialog();
    }

    /**
     * 判断是否已同意免责声明
     */
    private void isShowDialog() {
        if (!SpacoWalletUtils.getGgreeState()) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showDialog();
                }
            }, 10);
        }
    }

    private void showDialog() {
        mDialog = new DisclaimerDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.img_exit:
                        mDialog.setTitle(getResources().getString(R.string.error_agreement), 1);
                        break;
                    case R.id.tx_continue:
                        if (mDialog.isCheck()) {
                            mDialog.dismiss();
                            SpacoWalletUtils.setGgreeState(true);
                        } else {
                            mDialog.setTitle(getResources().getString(R.string.error_agreement), 1);
                        }
                        break;
                    default:
                }
            }
        });
        mDialog.show();
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPinSetSuccess(String pin) {
        pinCode = pin;
        if (SpacoWalletUtils.isPinSet()){
            launchToWalletActivity();
        }else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container, VerifyPinSetFragment.newInstance(null),
                    VerifyPinSetFragment.class.getSimpleName());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onPinSetVerifySuccess(String verifyPin) {
        if (pinCode.equals(verifyPin)) {
            SpacoWalletUtils.setPin(verifyPin);
            launchToWalletActivity();
        } else {
            ToastUtils.show(getString(R.string.toast_pin_error));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 如果钱包已经创建直接进入钱包详情页面
     * 否则去创建钱包
     */
    private void launchToWalletActivity() {
        if (SpacoWalletUtils.isWalletExist(Wallet.buildTestData().getWalletType(),Wallet.buildTestData().getWalletName())){
            Intent intent = new Intent(this, WalletDetailsActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, WalletCreatActivity.class);
            intent.putExtra(Constant.KEY_PIN, pinCode);
            startActivity(intent);

        }
        finish();

    }

    /**
     * 捕捉到了Fragment回退事件
     */
    @Override
    public void onBackPressed() {
        finish();
    }
}
