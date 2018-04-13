package io.spaco.wallet.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.Main.WalletViewModel;
import io.spaco.wallet.api.Const;
import io.spaco.wallet.base.BaseActivity;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.utils.AppUtils;
import io.spaco.wallet.utils.DES;
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
        mVersionName.setText("v" + AppUtils.getVersionName(getApplicationContext()));
    }

    @Override
    protected void initData() {
        //获取汇率，不需要关心结果和生命周期
        new WalletViewModel().initExchangeCoin();
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
            if (!TextUtils.isEmpty(SpacoWalletUtils.getPin())) {
                Mobile.init(walletDir, SpacoWalletUtils.getPin16());
                Mobile.registerNewCoin("spocoin", "i.spo.network:8620");
                Mobile.registerNewCoin("skycoin", "i.spo.network:6420");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //钱包初始化完成后执行下一步业务操作
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                launchToPinInputActvity();
                finish();
            }
        }, 1500);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            //6.0动态权限申请
            if(EasyPermissions.hasPermissions(this, Constant.ALL_PERMISSIONS)){
                initWallet();
            }else{
                EasyPermissions.requestPermissions(this,"",Constant.ALL_RERMISSIONS_REQUEST_CODE,Constant.ALL_PERMISSIONS);
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
