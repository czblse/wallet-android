package io.spaco.wallet.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import java.util.List;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.PIN.PinInputFragment;
import io.spaco.wallet.activities.PIN.PinSetFragment;
import io.spaco.wallet.activities.PIN.PinSetListener;
import io.spaco.wallet.activities.PIN.VerifyPinSetFragment;
import io.spaco.wallet.base.BaseActivity;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.datas.Wallet;
import io.spaco.wallet.datas.WalletManager;
import io.spaco.wallet.utils.AppManager;
import io.spaco.wallet.utils.SpacoWalletUtils;
import io.spaco.wallet.utils.ToastUtils;
import io.spaco.wallet.widget.DisclaimerDialog;
import mobile.Mobile;

/**
 * Pin设置界面，用于为钱包设置Pin保护密码
 * Created by zjy on 2018/1/20.
 */

public class PinSetActivity extends BaseActivity implements PinSetListener {

    Handler handler = new Handler();
    private String pinCode;
    private DisclaimerDialog mDialog;
    private PinInputFragment inputFragment;
    //pin输入次数
    private int pinNum;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_pin_set;
    }

    @Override
    protected void initViews() {
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            if (SpacoWalletUtils.isPinSet()) {
                inputFragment = PinInputFragment.newInstance(null);
                fragmentTransaction.add(R.id.container, inputFragment,
                        PinInputFragment.class.getSimpleName());
            } else {
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPinSetSuccess(String pin) {
        pinCode = pin;
        if (SpacoWalletUtils.returnPinHint()) {
            if (SpacoWalletUtils.isPinSet()) {
                if (TextUtils.equals(pinCode, SpacoWalletUtils.getPin())) {
                    launchToWalletActivity();
                    pinNum = 0;
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
                FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.container, VerifyPinSetFragment.newInstance(null),
                        VerifyPinSetFragment.class.getSimpleName());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        } else {
            clearInput();
            int time = SpacoWalletUtils.getOutPinTime();
            ToastUtils.show(getResources().getString(R.string.input_pintime) + time
                    + getResources().getString(R.string.input_pinminut));
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
        if (pinCode.equals(verifyPin)) {
            SpacoWalletUtils.setPin(verifyPin);
            launchToWalletActivity();
        } else {
            ToastUtils.show(getString(R.string.toast_pin_error));
        }
    }

    @Override
    public void onPinReset() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, PinSetFragment.newInstance(null),
                PinSetFragment.class.getSimpleName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
        List<Wallet> allWallet = WalletManager.getInstance().getAllWallet();
        if (allWallet != null && allWallet.size() > 0) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            String walletDir = getApplicationContext().getFilesDir().toString() + "/spo";
            try {
                Mobile.init(walletDir, SpacoWalletUtils.getPin16());
                Mobile.registerNewCoin("spocoin", "i.spo.network:8620");
                Mobile.registerNewCoin("skycoin", "i.spo.network:6420");
            } catch (Exception e) {

            }

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
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (SpacoWalletUtils.isPinSet()) {
                AppManager.getAppManager().AppExit(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
