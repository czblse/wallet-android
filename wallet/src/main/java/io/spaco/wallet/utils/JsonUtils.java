package io.spaco.wallet.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.spaco.wallet.api.Const;
import io.spaco.wallet.beans.WalletDetailsBean;

/**
 * Created by luch on 2018/2/10.
 */

public class JsonUtils {

    private Gson gson;

    private GsonBuilder builder;

    private  String jsonStr;

    public static JsonUtils jsonUtils;

    public static JsonUtils getInstance() {

        if (jsonUtils == null) {
            jsonUtils = new JsonUtils();
        }
        return jsonUtils;
    }


    /**
     * 钱包数据转换json
     */
    private String toWalletJson(WalletDetailsBean bean) {
        try {
            builder = new GsonBuilder();
            gson = builder.create();
            jsonStr = gson.toJson(bean, WalletDetailsBean.class);
            LogUtils.d("toJson--->" + jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }


    /**
     * 数据加密
     */
    public  String getEncryptStr(WalletDetailsBean bean) {
        try {
            toWalletJson(bean);
            jsonStr = DES.encryptDES(jsonStr, Const.DESKey);
            jsonStr = Base64.encode(jsonStr.getBytes());
            LogUtils.d("EncryptStr--->" + jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    /**
     * 数据解密
     */
    public WalletDetailsBean getDecryptStr(String str) {
        WalletDetailsBean bean = null;
        try {
            byte[] bytes = Base64.decode(str);
            str = DES.decryptDES(new String(bytes), Const.DESKey);
            bean = gson.fromJson(str, WalletDetailsBean.class);
            LogUtils.d("DecryptStr--->" + bean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }


}
