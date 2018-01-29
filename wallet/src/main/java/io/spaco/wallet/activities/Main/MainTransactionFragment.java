package io.spaco.wallet.activities.Main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import io.spaco.wallet.R;
import io.spaco.wallet.base.BaseFragment;
import io.spaco.wallet.beans.MainTransactionBean;
import io.spaco.wallet.common.OnItemClickListener;
import io.spaco.wallet.utils.StatusBarUtils;
import io.spaco.wallet.utils.ToastUtils;
import io.spaco.wallet.widget.ShowQrDialog;

/**
 * 交易记录视图碎片
 * Created by kimi on 2018/1/25.
 */

public class MainTransactionFragment extends BaseFragment implements OnItemClickListener<MainTransactionBean> {

    RecyclerView recyclerView;
    MainTransactionAdapter mainTransactionAdapter;

    public static MainTransactionFragment newInstance(Bundle args){
        MainTransactionFragment instance = new MainTransactionFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_main_transaction;
    }

    @Override
    protected void initViews(View rootView) {
        StatusBarUtils.statusBarCompat(this);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        ArrayList<MainTransactionBean> mainWalletBeans = new ArrayList<>();
        mainWalletBeans.add(new MainTransactionBean());
        mainWalletBeans.add(new MainTransactionBean());
        mainWalletBeans.add(new MainTransactionBean());
        mainTransactionAdapter = new MainTransactionAdapter(mainWalletBeans);
        mainTransactionAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mainTransactionAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(int position, MainTransactionBean bean) {
        ShowQrDialog showQrDialog = new ShowQrDialog(getActivity());
        showQrDialog.setKey("http://www.taobao.com");
        showQrDialog.show();
    }
}
