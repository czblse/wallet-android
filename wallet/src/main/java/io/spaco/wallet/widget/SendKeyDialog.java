package io.spaco.wallet.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import io.spaco.wallet.R;
import io.spaco.wallet.beans.SendKeyBean;

/**
 * @author: lh on 2018/1/25 13:59.
 * Email:luchefg@gmail.com
 * Description: 发送密钥
 */

public class SendKeyDialog extends Dialog {

    private Context context;
    private View mainView;
    private View.OnClickListener onClickListener;


    private TextView mTxTitle;
    private ImageView mImgExit;
    private TextView mTxSky;
    private TextView mTxDollar;
    private TextView mTxDate;
    private TextView mTxStatus;
    private TextView mTxForm;
    private TextView mTxTo;
    private TextView mTxNotes;
    private TextView mTxTime;


    public SendKeyDialog(@NonNull Context context) {
        super(context, R.style.customDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainView = LayoutInflater.from(context).inflate(R.layout.dialog_sendkey, null);

        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.8);
        this.setContentView(mainView,
                new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
        //点击空白区域可以取消dialog
        this.setCanceledOnTouchOutside(true);
        //点击back键可以取消dialog
        this.setCancelable(true);
        Window window = this.getWindow();
        //让Dialog显示在屏幕的底部
        window.setGravity(Gravity.CENTER);
        //设置BottomDialog的宽高属性
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


        mTxTitle = (TextView) findViewById(R.id.tx_title);
        mImgExit = (ImageView) findViewById(R.id.img_exit);
        mTxSky = (TextView) findViewById(R.id.tx_sky);
        mTxDollar = (TextView) findViewById(R.id.tx_dollar);
        mTxDate = (TextView) findViewById(R.id.tx_date);
        mTxStatus = (TextView) findViewById(R.id.tx_status);
        mTxForm = (TextView) findViewById(R.id.tx_form);
        mTxTo = (TextView) findViewById(R.id.tx_to);
        mTxNotes = (TextView) findViewById(R.id.tx_notes);
        mTxTime = (TextView) findViewById(R.id.tx_time);

        mImgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    /**
     * 设置数据
     * @param bean
     */
    public void setData(SendKeyBean bean) {
        mTxSky.setText(bean.getSkyNum());
        mTxDollar.setText(bean.getSkyDollar());
        mTxDate.setText(bean.getDate());
        mTxStatus.setText(bean.getStatus());
        mTxForm.setText(bean.getForm());
        mTxTo.setText(bean.getTo());
        mTxNotes.setText(bean.getNotes());
        mTxTime.setText(bean.getTime());

    }


}
