package io.spaco.wallet.activities;

import android.content.Intent;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.zxing.lib.CaptureActivity;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.spaco.wallet.R;
import io.spaco.wallet.activities.Main.TransactionViewModel;
import io.spaco.wallet.activities.Main.WalletViewModel;
import io.spaco.wallet.base.BaseActivity;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.datas.Transaction;
import io.spaco.wallet.datas.Wallet;
import io.spaco.wallet.utils.LogUtils;
import io.spaco.wallet.utils.StatusBarUtils;
import io.spaco.wallet.utils.ToastUtils;

/**
 * 发送界面
 */
public class SendCostActivity extends BaseActivity {

    ImageView close, qrcode;
    AppCompatSpinner appCompatSpinner;
    EditText  toWallet, amount, nodes;
    TextView cancle, send;

    TransactionViewModel transactionViewModel = new TransactionViewModel();
    Wallet wallet;
    Transaction transaction;


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_send_cost;
    }

    @Override
    protected void initViews() {
        int topPadding = StatusBarUtils.getActionBarSize(this) + StatusBarUtils.getStatusBarHeight(this);
        getWindow().getDecorView().setPadding(0, topPadding, 0, 0);
        close = findViewById(R.id.close);
        qrcode = findViewById(R.id.img_qrcode);
        appCompatSpinner = findViewById(R.id.from_wallet);
        toWallet = findViewById(R.id.to_wallet);
        amount = findViewById(R.id.amount);
        nodes = findViewById(R.id.nodes);
        cancle = findViewById(R.id.cancle);
        send = findViewById(R.id.send);

        cancle.setOnClickListener(createClose());
        close.setOnClickListener(createClose());
        send.setOnClickListener(createSend());
        qrcode.setOnClickListener(createQrcode());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.send_cost_out);
    }

    private View.OnClickListener createQrcode() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendCostActivity.this, CaptureActivity.class);
                startActivityForResult(intent, Constant.REQUEST_QRCODE);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_QRCODE && resultCode == RESULT_OK) {
            String code = data.getData().toString();
            toWallet.setText(code);
        }
    }

    private View.OnClickListener createSend() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkEmpty()) {
                    transaction = new Transaction();
                    transaction.setAmount(amount.getText().toString());
                    transaction.setCoinType(Constant.COIN_TYPE_SKY);
                    transaction.setFromWallet(appCompatSpinner.getSelectedItem().toString());
                    transaction.setToWallet(toWallet.getText().toString());
                    transaction.setNodes(nodes.getText().toString());
                    transactionViewModel.sendTransaction(transaction)
                            .subscribe(new Observer<Transaction>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(Transaction transaction) {
                                    if (transaction != null) {
                                        LogUtils.d(transaction.getState());
                                        ToastUtils.show(transaction.state + "");
                                    }
                                    finish();
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }

            }
        };
    }

    private boolean checkEmpty() {
        if (TextUtils.isEmpty(appCompatSpinner.getSelectedItem().toString())) {
            ToastUtils.show("请输入钱包ID");
            return false;
        }
        if (TextUtils.isEmpty(toWallet.getText())) {
            ToastUtils.show("请输入地址");
            return false;
        }
        if (TextUtils.isEmpty(amount.getText())) {
            ToastUtils.show("请输入金额");
            return false;
        }
        if(wallet == null){
            return false;
        }
        if (Double.parseDouble(wallet.getBalance().trim()) < Double.parseDouble(amount.getText().toString().trim())) {
            ToastUtils.show("余额不足");
            return false;
        }
        return true;
    }

    private View.OnClickListener createClose() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        };
    }

    @Override
    protected void initData() {
        //初始化发送钱包
        final List<Wallet> wallets = WalletViewModel.wallets;
        if(wallets != null && wallets.size() > 0){
            ArrayAdapter<Wallet> walletArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, wallets);
            appCompatSpinner.setAdapter(walletArrayAdapter);
            appCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    wallet = wallets.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }


}
