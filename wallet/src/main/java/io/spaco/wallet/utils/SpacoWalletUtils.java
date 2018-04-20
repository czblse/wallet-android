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

    /**
     * pin输入超限开始时间
     */
    public static final String PIN_OUT = "PIN_TIME";

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

    /**
     * pin错误次数限制提示
     * 比较两次时间差5分钟
     *
     * @return 布尔值
     */
    public static boolean returnPinHint() {
        int Nm, Om;
        Om = getPinTime();
        Nm = DateUtils.getNowMinute();
        if (Om == 0) {
            return true;
        }
        if (Nm > Om) {
            if ((Nm - Om) >= Const.PINTIME) {
                setPinTime(true);
                return true;
            } else {
                return false;
            }
        } else if (Nm < Om) {
            if (((60 - Om) + Nm) >= Const.PINTIME) {
                setPinTime(true);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * pin错误次数限制提示
     * 比较两次时间差5分钟
     *
     * @return 分钟
     */
    public static int getOutPinTime() {
        int Nm, Om;
        Om = getPinTime();
        Nm = DateUtils.getNowMinute();
        if (Nm > Om) {
            return Const.PINTIME - (Nm - Om);
        } else if (Nm < Om) {
            return Const.PINTIME - ((60 - Om) + Nm);
        } else {
            return Const.PINTIME;
        }

    }

    private static int getPinTime() {
        try {
            Integer value = SharePrefrencesUtil.getInstance().getInt(PIN_OUT);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void setPinTime(boolean isOut) {
        int minutes = DateUtils.getNowMinute();
        SharePrefrencesUtil.getInstance().putInt(PIN_OUT, isOut ? 0 : minutes);
    }

    public static void setPinTime() {
        int minutes = DateUtils.getNowMinute();
        SharePrefrencesUtil.getInstance().putInt(PIN_OUT, minutes);
    }

    public static String getPin() {
        try {
            String value = SharePrefrencesUtil.getInstance().getString(PIN_KEY);
            String pincode = DES.decryptDES(value, Const.DESKey);
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
     */
    public static String getPin16() {
        String pinTemp = String.valueOf(getPin().hashCode());
        return getPin() + pinTemp;
    }

    /**
     * 判断钱包是否已经创建过
     */
    public static boolean isWalletExist(String coinType, String walletName) {
        return WalletManager.getInstance().isExitWallet(walletName);
    }

    /**
     * 保存生成过的钱包
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


    public static String encryptPasswd(String passwd) {
        String pinTemp = String.valueOf(passwd.hashCode());
        return passwd + pinTemp;
    }

}
