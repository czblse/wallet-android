package io.spaco.wallet.activities;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zbar.lib.CaptureActivity;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.Main.WalletViewModel;
import io.spaco.wallet.base.BaseActivity;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.utils.LogUtils;
import io.spaco.wallet.utils.StatusBarUtils;

/**
 * 发送界面
 */
public class SendCostActivity extends BaseActivity {

    ImageView close,qrcode;
    EditText fromWallet,toWallet,amount,nodes;
    TextView cancle,send;

    /**
     * 钱包控制层
     */
    WalletViewModel walletViewModel = new WalletViewModel();

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_send_cost;
    }

    @Override
    protected void initViews() {
        int topPadding = StatusBarUtils.getActionBarSize(this) + StatusBarUtils.getStatusBarHeight(this);
        getWindow().getDecorView().setPadding(0,topPadding,0,0);
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
        return new View.OnClickListener(){

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
        if(requestCode == Constant.REQUEST_QRCODE && resultCode == RESULT_OK){
            String code = data.getData().toString();
            LogUtils.d(code);
        }
    }

    private View.OnClickListener createSend() {
        return new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                
            }
        };
    }

    private View.OnClickListener createClose() {
        return new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        };
    }

    @Override
    protected void initData() {

    }

}
