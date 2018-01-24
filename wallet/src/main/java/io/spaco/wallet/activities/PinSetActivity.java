package io.spaco.wallet.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import io.spaco.wallet.R;
import io.spaco.wallet.activities.pin.PinSetListener;
import io.spaco.wallet.base.BaseActivity;
import io.spaco.wallet.activities.pin.PinSetFragment;
import io.spaco.wallet.activities.pin.VerifyPinSetFragment;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.utils.ToastUtils;

/**
 * Pin设置界面，用于为钱包设置Pin保护密码
 * Created by zjy on 2018/1/20.
 */

public class PinSetActivity extends BaseActivity implements PinSetListener {

    private String pinCode;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_pin_set;
    }

    @Override
    protected void initViews() {
        if(savedInstanceState == null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container, PinSetFragment.newInstance(null),PinSetFragment.class.getSimpleName());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPinSetSuccess(String pin) {
        pinCode = pin;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, VerifyPinSetFragment.newInstance(null),VerifyPinSetFragment.class.getSimpleName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onPinSetVerifySuccess(String verifyPin) {
        if(pinCode.equals(verifyPin)){
            Intent intent = new Intent(this, WalletCreatActivity.class);
            intent.putExtra(Constant.KEY_PIN,pinCode);
            startActivity(intent);
            finish();
        }else{
            ToastUtils.show(getString(R.string.toast_pin_error));
        }
    }

}
