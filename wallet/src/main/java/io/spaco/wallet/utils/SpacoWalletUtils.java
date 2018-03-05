package io.spaco.wallet.utils;

/**
 * Created by zjy on 2018/1/20.
 */

import android.text.TextUtils;

import io.spaco.wallet.api.Const;
import io.spaco.wallet.datas.WalletManager;

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
        return !TextUtils.isEmpty(getPin());
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
        try {
            String value = SharePrefrencesUtil.getInstance().getString(PIN_KEY);
            String pincode = DES.decryptDES(value,Const.DESKey);
            LogUtils.d("decrypt = " + pincode);
            return pincode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setPin(String pin) {
        try {
            String value = DES.encryptDES(pin, Const.DESKey);
            SharePrefrencesUtil.getInstance().putString(PIN_KEY, value);
            LogUtils.d("encryp = " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 因为密码是16位的，所以取pin的hash的前16位作为加密的数据
     * @return
     */
    public static String getPin16(){
        String pinTemp = String.valueOf(getPin().hashCode());
        return getPin() + pinTemp;
    }

    /**
     * 判断钱包是否已经创建过
     *
     * @param coinType
     * @param walletName
     * @return
     */
    public static boolean isWalletExist(String coinType, String walletName) {
        return WalletManager.getInstance().isExitWallet(walletName);
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
