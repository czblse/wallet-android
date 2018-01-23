package io.spaco.wallet.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * App工具类
 * Created by kimi on 2018/1/23.</br>
 */

public class AppUtils {

    /**
     * 获取应用版本号名称
     * @param context
     * @return
     */
    public static String getVersionName(Context context){
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
