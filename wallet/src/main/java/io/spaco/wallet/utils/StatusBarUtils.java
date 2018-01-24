package io.spaco.wallet.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import io.spaco.wallet.R;

/**
 * 状态栏适配工具类,注意如果使用自定义toolbar的话，id 请引用@values/ids.xml 中的id_toolbar
 * Created by kimi on 2018/1/24.</br>
 */

public class StatusBarUtils {

    /**
     * activity 状态栏适配
     * @param activity
     */
    public static void statusBarCompat(AppCompatActivity activity){
        int actionBarHeight = getActionBarSize(activity);
        int statusBarHeight = getStatusBarHeight(activity);
        View toolbar = activity.getWindow().getDecorView().findViewById(R.id.id_toolbar);

        if(toolbar == null || toolbar.getVisibility() != View.VISIBLE){
            toolbar = activity.getWindow().getDecorView().findViewById(android.support.v7.appcompat.R.id.action_bar);
        }

        if(toolbar != null && toolbar.getVisibility() == View.VISIBLE){
            ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
            layoutParams.height = actionBarHeight + statusBarHeight;
            toolbar.setPadding(0,statusBarHeight,0,0);
            toolbar.requestLayout();
        }else{
            View contentView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            contentView.setPadding(0,getStatusBarHeight(activity),0,0);
            contentView.requestLayout();
        }
    }

    /**
     * activity 状态栏适配,自定义toolbar_id 为固定的
     * @param fragment
     */
    public static void statusBarCompat(Fragment fragment){
        int actionBarHeight = getActionBarSize(fragment.getContext());
        int statusBarHeight = getStatusBarHeight(fragment.getContext());
        View view = fragment.getView();
        View toolbar = view.findViewById(R.id.id_toolbar);
        if(toolbar != null && toolbar.getVisibility() == View.VISIBLE){
            ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
            layoutParams.height = actionBarHeight + statusBarHeight;
            toolbar.setPadding(0,statusBarHeight,0,0);
            toolbar.requestLayout();
        }else{
            view.setPadding(0,statusBarHeight,0,0);
            view.requestLayout();
        }
    }


    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {
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
    private static int getActionBarSize(Context context) {
        TypedArray actionbarSizeTypedArray = context.obtainStyledAttributes(new int[]{
                android.R.attr.actionBarSize,
        });
        return actionbarSizeTypedArray.getDimensionPixelOffset(0,0);
    }
}
