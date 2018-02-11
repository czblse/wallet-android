package io.spaco.wallet.common;


import android.Manifest;

/**
 * Created by kimi on 2018/1/24.
 */

public interface Constant {

    String KEY_PIN = "key_pin";
    String COIN_TYPE_SPACO = "spo";
    String COIN_TYPE_SKY = "skycoin";
    String COIN_TYPE_SUN = "suncoin";
    String KEY_WALLET_ID = "wallet_id";


    /**
     * 6.0动态权限，sd卡读写操作和相机二维码扫描
     */
    String[]  ALL_PERMISSIONS = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    /**
     * 所有动态权限对应的请求码
     */
    int ALL_RERMISSIONS_REQUEST_CODE = 101;
}
