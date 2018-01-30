package io.spaco.wallet.utils;

/**
 * Created by zjy on 2018/1/20.
 */

/**
 * 目前没有啥需要的类，所以以后复杂了再拆分
 */
public class SpacoWalletUtils {
    public static final String PINSETTED = "PIN_SET";
    public static boolean isPinSet(){
        return SharePrefrencesUtil.getInstance().getBoolean(PINSETTED);
    }

    public static void setPinSetted(){
        SharePrefrencesUtil.getInstance().putBoolean(PINSETTED,true);
    }

    public static boolean isHasWallet(){
        return false;
    }
}
