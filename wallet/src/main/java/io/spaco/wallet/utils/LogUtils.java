package io.spaco.wallet.utils;

import android.util.Log;

/**
 * 日志打印工具类
 * Created by kimi on 2018/1/24.</br>
 */

public class LogUtils {

    public static boolean DEBUG = true;
    public static final String LOGS_PREFIX = "wallet_logs";

    public static void d(String text){
        if(DEBUG){
            Log.d(LOGS_PREFIX,text);
        }
    }
}
