package io.spaco.wallet.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.view.View;

import com.trello.rxlifecycle2.components.RxActivity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.spaco.wallet.R;
import io.spaco.wallet.utils.AppManager;
import io.spaco.wallet.utils.StatusBarUtils;

/**
 * Created by zjy on 2018/1/20.
 * 基类Activity
 */

public abstract class BaseActivity extends RxAppCompatActivity implements View.OnClickListener{

    protected Bundle savedInstanceState;

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
        this.savedInstanceState = savedInstanceState;
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
    public void onClick(View v) {

    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return false;
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

    @Override
    protected Dialog onCreateDialog(int id) {
        super.onCreateDialog(id);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.app_name);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("\n正在努力加载中...\n");
        return progressDialog;
    }

}
