package io.spaco.wallet.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.tencent.bugly.crashreport.CrashReport;

import io.spaco.wallet.BuildConfig;
//import io.spaco.wallet.api.RetrofitService;
import io.spaco.wallet.utils.AppUtils;

/**
 * Created by zjy on 2018/1/20.
 */

public class SpacoAppliacation extends Application {

    public static SpacoAppliacation mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initNet();
        initBugly();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /**
     * 初始化网络
     */
    private void initNet() {
//        RetrofitService.init();
    }

    /**
     * 初始化bugly
     */
    private void initBugly() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = AppUtils.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setAppChannel(BuildConfig.FLAVOR);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, "2e22e774bc", BuildConfig.DEBUG, strategy);
    }


}
