package io.spaco.wallet.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import io.spaco.wallet.R;
import io.spaco.wallet.utils.AppManager;

/**
 * Created by zjy on 2018/1/20.
 * 基类Activity
 */

public abstract class BaseActivity extends Activity implements View.OnClickListener{



    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int attachLayoutRes();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    /**
     * 初始化数据
     */
    protected abstract void initData();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(attachLayoutRes());
            AppManager.getAppManager().addActivity(this);
            initViews();
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    protected void onDestroy() {
        try {
            AppManager.getAppManager().finishActivity(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

}
