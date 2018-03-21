package io.spaco.wallet.activities.Transaction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        setHasOptionsMenu(true);
        Toolbar toolbar = rootView.findViewById(R.id.id_toolbar);
        toolbar.setTitle("");
        ((TextView)rootView.findViewById(R.id.id_toolbar_title)).setText(R.string.transaction);
        ((AppCompatActivity)mActivity).setSupportActionBar(toolbar);
        ((AppCompatActivity)mActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = rootView.findViewById(R.id.recyclerview);
        emptyContainer = rootView.findViewById(R.id.empty_container);
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
        showQrDialog.setKey(bean.getToWallet());
        showQrDialog.show();
    }

    /**
     * 显示交易详细信息
     */
    private void showTransactionInfoDialog(TransactionInfo bean) {
        SendKeyBean sendKeyBean = new SendKeyBean();
        sendKeyBean.setDate("暂无时间字段");
        sendKeyBean.setStatus(bean.getStatus().getStatusValues());
        sendKeyBean.setForm(bean.getTxn().getInputs().get(0));
        sendKeyBean.setTo(bean.getTxn().getOutputs().get(0).getDst());
        sendKeyBean.setNotes(bean.getStatus().getBlock_seq());
        sendKeyBean.setTime(bean.getTxn().getOutputs().get(0).getHours());
        sendKeyBean.setSkyNum(bean.getTxn().getOutputs().get(0).getCoins());
        SendKeyDialog sendKeyDialog = new SendKeyDialog(mActivity);
        sendKeyDialog.setData(sendKeyBean);
        sendKeyDialog.setCancelable(false);
        sendKeyDialog.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.wallet_transaction,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.refresh){
            initData();
            return true;
        }else if(item.getItemId() == android.R.id.home){
            mActivity.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
