package io.spaco.wallet.activities.Transaction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.spaco.wallet.R;
import io.spaco.wallet.base.BaseFragment;
import io.spaco.wallet.beans.SendKeyBean;
import io.spaco.wallet.beans.TransactionInfo;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.common.OnItemClickListener;
import io.spaco.wallet.datas.Wallet;
import io.spaco.wallet.utils.StatusBarUtils;
import io.spaco.wallet.widget.SendKeyDialog;
import io.spaco.wallet.widget.ShowQrDialog;

/**
 * 交易记录视图碎片
 * Created by kimi on 2018/1/25.
 */

public class TransactionFragment extends BaseFragment implements OnItemClickListener<TransactionInfo> {

    RecyclerView recyclerView;
    View emptyContainer;
    ImageView imgRefresh;
    Wallet wallet;

    /**
     * 适配器
     */
    TransactionAdapter mainTransactionAdapter;

    /**
     * 控制层
     */
    TransactionViewModel transactionViewModel = new TransactionViewModel();

    public static TransactionFragment newInstance(Bundle args) {
        TransactionFragment instance = new TransactionFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_main_transaction;
    }

    @Override
    protected void initViews(View rootView) {
        Toolbar toolbar = rootView.findViewById(R.id.id_toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
        recyclerView = rootView.findViewById(R.id.recyclerview);
        emptyContainer = rootView.findViewById(R.id.empty_container);
        imgRefresh = rootView.findViewById(R.id.img_refresh);

        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        //钱包
        wallet = (Wallet) getArguments().getSerializable(Constant.KEY_WALLET);

        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        mainTransactionAdapter = new TransactionAdapter(null);
        mainTransactionAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mainTransactionAdapter);
    }

    @Override
    protected void initData() {
        mActivity.showDialog(1);
        transactionViewModel.getAllTransaction(wallet.getWalletID(),wallet.getWalletType())
                .compose(this.<ArrayList<TransactionInfo>>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new Observer<ArrayList<TransactionInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<TransactionInfo> transactionInfos) {
                        if (transactionInfos.size() == 0) {
                            emptyContainer.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            mainTransactionAdapter.getDatas().clear();
                            mainTransactionAdapter.getDatas().addAll(transactionInfos);
                            mainTransactionAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onComplete();
                    }

                    @Override
                    public void onComplete() {
                        mActivity.dismissDialog(1);
                    }
                });
    }

    @Override
    public void onClick(int viewid, int position, TransactionInfo bean) {
        if (viewid == R.id.tv_address) {
            showQRcodeDialog(bean);
        } else {
            //showTransactionInfoDialog(bean);
        }
    }

    /**
     * 显示钱包二维码地址
     *
     * @param bean
     */
    private void showQRcodeDialog(TransactionInfo bean) {
        ShowQrDialog showQrDialog = new ShowQrDialog(getActivity());
        showQrDialog.setKey(bean.coinType, bean.toWallet);
        showQrDialog.show();
    }

    /**
     * 显示交易详细信息
     */
    private void showTransactionInfoDialog(TransactionInfo bean) {
        SendKeyBean sendKeyBean = new SendKeyBean();
        sendKeyBean.setDate("暂无时间字段");
        sendKeyBean.setStatus(bean.status.getStatusValues());
        sendKeyBean.setForm(bean.txn.inputs.get(0));
        sendKeyBean.setTo(bean.txn.outputs.get(0).dst);
        sendKeyBean.setNotes(bean.status.block_seq);
        sendKeyBean.setTime(bean.txn.outputs.get(0).hours);
        sendKeyBean.setSkyNum(bean.txn.outputs.get(0).coins);
        SendKeyDialog sendKeyDialog = new SendKeyDialog(mActivity);
        sendKeyDialog.setData(sendKeyBean);
        sendKeyDialog.setCancelable(false);
        sendKeyDialog.show();
    }
}
