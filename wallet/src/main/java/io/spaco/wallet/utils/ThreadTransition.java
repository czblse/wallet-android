package io.spaco.wallet.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * ProjectName CarStewardExtension
 * ClassName ThreadTransition
 * Created by kimi on 2017/12/15.
 * Emaile 24750@163.com
 */

public final class ThreadTransition {

    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    /**
     * 同步线程
     * @param runnable
     */
    public static void syncThread(Runnable runnable) {
        if(runnable == null){
            return;
        }
        if(Looper.myLooper() == Looper.getMainLooper()){
            runnable.run();
            return;
        }
        Message obtain = Message.obtain(mainHandler, runnable);
        mainHandler.sendMessage(obtain);
    }
}
