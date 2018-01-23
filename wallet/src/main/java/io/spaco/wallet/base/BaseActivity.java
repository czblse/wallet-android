package io.spaco.wallet.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.spaco.wallet.utils.AppManager;

/**
 * Created by zjy on 2018/1/20.
 * 基类Activity
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

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
    protected void onDestroy() {
        try {
            AppManager.getAppManager().finishActivity(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

}
