package io.spaco.wallet.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.zbar.lib.CaptureActivity;

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
    EditText fromWallet, toWallet, amount, nodes;
    TextView cancle, send;

    /**
     * 钱包控制层
     */
    WalletViewModel walletViewModel = new WalletViewModel();
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
        fromWallet = findViewById(R.id.from_wallet);
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
            LogUtils.d(code);
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
                    transaction.setFromWallet(fromWallet.getText().toString());
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
        if (TextUtils.isEmpty(fromWallet.getText())) {
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
        if (Double.parseDouble(wallet.getBalance()) < Double.parseDouble(amount.getText().toString())) {
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
        walletViewModel.getAllWallets()
                .compose(this.<List<Wallet>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<List<Wallet>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Wallet> wallets) {
                        walletViewModel.wallets = wallets;
                        wallet = wallets.get(0);
                        fromWallet.setText(wallet.getWalletID());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onComplete();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


}
