package io.spaco.wallet.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.TextView;

import java.util.List;

import io.spaco.wallet.R;
import io.spaco.wallet.base.BaseActivity;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.utils.AppUtils;
import io.spaco.wallet.utils.SpacoWalletUtils;
import mobile.Mobile;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by zjy on 2018/1/20.
 */

public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    Handler handler = new Handler();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {
        TextView mVersionName = findViewById(R.id.version_name);
        mVersionName.setText(AppUtils.getVersionName(getApplicationContext()));
    }

    @Override
    protected void initData() {
        //6.0动态权限申请
        if(EasyPermissions.hasPermissions(this, Constant.ALL_PERMISSIONS)){
            initWallet();
        }else{
            EasyPermissions.requestPermissions(this,"",Constant.ALL_RERMISSIONS_REQUEST_CODE,Constant.ALL_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    private void initWallet() {
        try {
            String walletDir = getApplicationContext().getFilesDir().toString() + "/spo";
            Mobile.init(walletDir);
            //
            Mobile.registerNewCoin("spocoin", "182.92.180.92:8620");
            Mobile.registerNewCoin("skycoin", "182.92.180.92:8620");
            Mobile.registerNewCoin("suncoin", "182.92.180.92:8620");

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    launchToNextAcitivity();
                    finish();
                }
            }, 1500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(requestCode == Constant.ALL_RERMISSIONS_REQUEST_CODE && perms.size() == Constant.ALL_PERMISSIONS.length){
            initWallet();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms) && requestCode == Constant.ALL_RERMISSIONS_REQUEST_CODE) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    /**
     * 如果pin没有设定的话，跳到pin设置页面
     * 如果没有创建钱包，则跳转到pin输入页面
     */
    private void launchToNextAcitivity() {
        if (SpacoWalletUtils.isPinSet()) {
            launchToPinInputActvity();
        } else {
            launchToPinSetActivity();
        }
    }

    private void launchToPinSetActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void launchToPinInputActvity() {
        Intent intent = new Intent(this, PinSetActivity.class);
        startActivity(intent);
    }
}
