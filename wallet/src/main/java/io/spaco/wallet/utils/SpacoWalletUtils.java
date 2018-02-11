package io.spaco.wallet.utils;

/**
 * Created by zjy on 2018/1/20.
 */

import android.text.TextUtils;

/**
 * 目前没有啥需要的类，所以以后复杂了再拆分
 */
public class SpacoWalletUtils {

    public static final String PIN_KEY = "PIN_SET";
    /**
     * 是否同意免责声明
     */
    public static final String GGREE = "GRAEE_SELE";
    /**
     * 存储钱包数据
     */
    public static final String Wallet_KEY = "Wallet_KEY";

    public static boolean isPinSet() {
<<<<<<< HEAD
        return false;
=======
        return !TextUtils.isEmpty(getPin());
>>>>>>> a5bc271e55ccece050d132fb572c70d2eb6c82f5
    }


    public static boolean isHasWallet() {
        return false;
    }

    public static boolean getGgreeState() {
        return SharePrefrencesUtil.getInstance().getBoolean(GGREE);
    }

    public static void setGgreeState(Boolean state) {
        SharePrefrencesUtil.getInstance().putBoolean(GGREE, state);
    }


    public static String getPin() {
        return SharePrefrencesUtil.getInstance().getString(PIN_KEY);
    }

    public static void setPin(String pin) {
        SharePrefrencesUtil.getInstance().putString(PIN_KEY, pin);
    }

    /**
     * 判断钱包是否已经创建过
     *
     * @param coinType
     * @param walletName
     * @return
     */
    public static boolean isWalletExist(String coinType, String walletName) {
        return false;
    }

    /**
     * 保存生成过的钱包
     *
     * @param coinType
     * @param walletName
     */
    public static void saveNewWallet(String coinType, String walletName) {
    }

    /**
     * 获取生成过的钱包数据
     */
    public static String getEncryptWallet() {
        return SharePrefrencesUtil.getInstance().getString(Wallet_KEY);
    }

    /**
     * 保存生成过的钱包数据
     */
    public static void setEncryptWallet(String data) {
        SharePrefrencesUtil.getInstance().putString(Wallet_KEY, data);
    }
}
