package io.spaco.wallet.utils;

/**
 * Created by zjy on 2018/1/20.
 */

/**
 * 目前没有啥需要的类，所以以后复杂了再拆分
 */
public class SpacoWalletUtils {
    public static final String PINSETTED = "PIN_SET";
    public static final String PIN_KEY = "PIN_SET";
    /**
     * 是否同意免责声明
     */
    public static final String GGREE = "GRAEE_SELE";


    public static boolean isPinSet(){
        return true;
    }

    public static void setPinSetted(){
        SharePrefrencesUtil.getInstance().putBoolean(PINSETTED,true);
    }

    public static boolean isHasWallet(){
        return false;
    }

    public static boolean getGgreeState(){
        return SharePrefrencesUtil.getInstance().getBoolean(GGREE);
    }

    public static void setGgreeState(Boolean state){
        SharePrefrencesUtil.getInstance().putBoolean(GGREE, state);
    }


    public static String getPin(){
        return SharePrefrencesUtil.getInstance().getString(PIN_KEY);
    }

    public static void setPin(String pin){
        SharePrefrencesUtil.getInstance().putString(PIN_KEY, pin);
    }

    /**
     * 判断钱包是否已经创建过
     * @param coinType
     * @param walletName
     * @return
     */
    public static boolean isWalletExist(String coinType, String walletName){
        return false;
    }

    /**
     * 保存生成过的钱包
     * @param coinType
     * @param walletName
     */
    public static void saveNewWallet(String coinType, String walletName){
    }
}
