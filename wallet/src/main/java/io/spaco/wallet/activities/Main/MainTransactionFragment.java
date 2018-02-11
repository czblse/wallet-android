package io.spaco.wallet.activities.Main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.spaco.wallet.R;
import io.spaco.wallet.base.BaseFragment;
import io.spaco.wallet.beans.TransactionInfo;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.common.OnItemClickListener;
import io.spaco.wallet.datas.Transaction;
import io.spaco.wallet.datas.TransactionManager;
import io.spaco.wallet.utils.StatusBarUtils;
import io.spaco.wallet.viewmodel.TransactionViewModel;
import io.spaco.wallet.widget.ShowQrDialog;
import mobile.Mobile;

/**
 * 交易记录视图碎片
 * Created by kimi on 2018/1/25.
 */

public class MainTransactionFragment extends BaseFragment implements OnItemClickListener<TransactionInfo> {

    RecyclerView recyclerView;

    /**
     * 适配器
     */
    MainTransactionAdapter mainTransactionAdapter;

    /**
     * 控制层
     */
    TransactionViewModel transactionViewModel = new TransactionViewModel();

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
        mainTransactionAdapter = new MainTransactionAdapter(null);
        mainTransactionAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mainTransactionAdapter);
    }

    @Override
    protected void initData() {
        mActivity.showDialog(1);
        transactionViewModel.getAllTransaction().subscribe(new Observer<ArrayList<TransactionInfo>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ArrayList<TransactionInfo> transactionInfos) {
                mainTransactionAdapter.getDatas().addAll(transactionInfos);
                mainTransactionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                mActivity.dismissDialog(1);
            }
        });
    }

    @Override
    public void onClick(int position, TransactionInfo bean) {
        ShowQrDialog showQrDialog = new ShowQrDialog(getActivity());
        showQrDialog.setKey("http://www.taobao.com");
        showQrDialog.show();
    }
}
