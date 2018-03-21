package io.spaco.wallet.utils;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * 状态栏适配工具类,注意如果使用自定义toolbar的话，id 请引用@values/ids.xml 中的id_toolbar
 * Created by kimi on 2018/1/24.</br>
 */

public class StatusBarUtils {

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    /**
     * 获取actionBar的高度
     * @param context
     * @return
     */
    public static int getActionBarSize(Context context) {
        TypedArray actionbarSizeTypedArray = context.obtainStyledAttributes(new int[]{
                android.R.attr.actionBarSize,
        });
        return actionbarSizeTypedArray.getDimensionPixelOffset(0,0);
    }
}
