package io.spaco.wallet.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

import io.spaco.wallet.BuildConfig;
//import io.spaco.wallet.api.RetrofitService;
import io.spaco.wallet.R;
import io.spaco.wallet.activities.MainActivity;
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
        initBeta();
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
        Bugly.init(context, "2e22e774bc", BuildConfig.DEBUG, strategy);
    }

    /**
     * Beta初始化
     */
    private void initBeta() {
        /**** Beta高级设置*****/
        /**
         * true表示app启动自动初始化升级模块；
         * false不好自动初始化
         * 开发者如果担心sdk初始化影响app启动速度，可以设置为false
         * 在后面某个时刻手动调用
         */
        Beta.autoInit = true;

        /**
         * true表示初始化时自动检查升级
         * false表示不会自动检查升级，需要手动调用Beta.checkUpgrade()方法
         */
        Beta.autoCheckUpgrade = false;

        /**
         * 设置升级周期为60s（默认检查周期为0s），60s内SDK不重复向后天请求策略
         */
        Beta.initDelay = 1 * 1000;

        /**
         * 设置通知栏大图标，largeIconId为项目中的图片资源；
         */
        Beta.largeIconId = R.drawable.icon_launcher;

        /**
         * 设置状态栏小图标，smallIconId为项目中的图片资源id;
         */
        Beta.smallIconId = R.drawable.icon_launcher;


        /**
         * 设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id;
         * 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
         */
        Beta.defaultBannerId = R.drawable.icon_launcher;

        /**
         * 设置sd卡的Download为更新资源保存目录;
         * 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
         */
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        /**
         * 点击过确认的弹窗在APP下次启动自动检查更新时会再次显示;
         */
        Beta.showInterruptedStrategy = false;

        /**
         * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗;
         * 不设置会默认所有activity都可以显示弹窗;
         */
        Beta.canShowUpgradeActs.add(MainActivity.class);


        /**
         *  设置自定义升级对话框UI布局
         *  注意：因为要保持接口统一，需要用户在指定控件按照以下方式设置tag，否则会影响您的正常使用：
         *  标题：beta_title，如：android:tag="beta_title"
         *  升级信息：beta_upgrade_info  如： android:tag="beta_upgrade_info"
         *  更新属性：beta_upgrade_feature 如： android:tag="beta_upgrade_feature"
         *  取消按钮：beta_cancel_button 如：android:tag="beta_cancel_button"
         *  确定按钮：beta_confirm_button 如：android:tag="beta_confirm_button"
         *  详见layout/upgrade_dialog.xml
         */
//        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog;

        /**
         * 设置自定义tip弹窗UI布局
         * 注意：因为要保持接口统一，需要用户在指定控件按照以下方式设置tag，否则会影响您的正常使用：
         *  标题：beta_title，如：android:tag="beta_title"
         *  提示信息：beta_tip_message 如： android:tag="beta_tip_message"
         *  取消按钮：beta_cancel_button 如：android:tag="beta_cancel_button"
         *  确定按钮：beta_confirm_button 如：android:tag="beta_confirm_button"
         *  详见layout/tips_dialog.xml
         */
//        Beta.tipsDialogLayoutId = R.layout.tips_dialog;

        /**
         * 自定义Activity参考，通过回调接口来跳转到你自定义的Actiivty中。
         */
       /* Beta.upgradeListener = new UpgradeListener() {

            @Override
            public void onUpgrade(int ret, UpgradeInfo strategy, boolean isManual, boolean isSilence) {
                if (strategy != null) {
                    Intent i = new Intent();
                    i.setClass(getApplicationContext(), UpgradeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "没有更新", Toast.LENGTH_SHORT).show();
                }
            }
        };*/
    }
}
