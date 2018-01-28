package io.spaco.wallet.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import io.spaco.wallet.api.RetrofitService;

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
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    private void initNet(){
        RetrofitService.init();
    }
}
