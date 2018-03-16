package io.spaco.wallet.base;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.trello.rxlifecycle2.components.RxActivity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.PinSetActivity;
import io.spaco.wallet.activities.SplashActivity;
import io.spaco.wallet.utils.AppManager;
import io.spaco.wallet.utils.StatusBarUtils;

/**
 * Created by zjy on 2018/1/20.
 * 基类Activity
 */

public abstract class BaseActivity extends RxAppCompatActivity implements View.OnClickListener{

    protected Bundle savedInstanceState;
    private boolean isActive = true;

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

    @Override
    protected void onStop() {
        super.onStop();
        if(!isAppOnFreground()){
            isActive = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isActive){
            //从后台唤醒
            if (this instanceof PinSetActivity || this instanceof SplashActivity){
                return;
            }
            isActive = true;
            Intent n = new Intent(this, PinSetActivity.class);
            startActivity(n);
        }
    }

    public boolean isAppOnFreground(){
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        String curPackageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> app = am.getRunningAppProcesses();
        if(app==null){
            return false;
        }
        for(ActivityManager.RunningAppProcessInfo a:app){
            if(a.processName.equals(curPackageName)&&
                    a.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                return true;
            }
        }
        return false;
        /*ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        if(!TextUtils.isEmpty(curPackageName)&&curPackageName.equals(getPackageName())){
            return true;
        }
        return false;*/
    }

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

        }
    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
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

        }

        super.onDestroy();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        super.onCreateDialog(id);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.app_name);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading_str));
        return progressDialog;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (this instanceof PinSetActivity){
                finish();
                System.exit(0);
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
