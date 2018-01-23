package io.spaco.wallet.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * 碎片基类
 * Created by kimi on 2018/1/23.
 */

public abstract class BaseFragment extends Fragment {

    protected Bundle savedInstanceState;
    protected View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState == null){
            rootView = inflater.inflate(attachLayoutRes(),container,false);
            initViews(rootView);
        }else{
            ViewParent parent = rootView.getParent();
            if(parent != null && parent instanceof ViewGroup){
                ViewGroup vp = (ViewGroup) parent;
                vp.removeAllViews();
            }
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(this.savedInstanceState == null){
            initData();
        }
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int attachLayoutRes();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews(View rootView);

    /**
     * 初始化数据
     */
    protected abstract void initData();
}