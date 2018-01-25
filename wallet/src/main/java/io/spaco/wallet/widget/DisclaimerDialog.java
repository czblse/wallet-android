package io.spaco.wallet.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import io.spaco.wallet.R;

/**
 * @author: lh on 2018/1/25 13:59.
 * Email:luchefg@gmail.com
 * Description: 免责声明弹出框
 */

public class DisclaimerDialog extends Dialog {

    private Context context;
    private View mainView;
    private View.OnClickListener onClickListener;
    private TextView mTxTitle;
    private ImageView mImgExit;
    private TextView mTxContent;
    private AppCompatCheckBox mCheckboxKnow;
    private TextView mTxContinue;
    private boolean isCheck;

    public DisclaimerDialog(@NonNull Context context,
            View.OnClickListener onClickListener) {
        super(context, R.style.customDialog);
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainView = LayoutInflater.from(context).inflate(R.layout.dialog_agreetext, null);

        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.8);
        this.setContentView(mainView,
                new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
        //点击空白区域可以取消dialog
        this.setCanceledOnTouchOutside(false);
        //点击back键可以取消dialog
        this.setCancelable(true);
        Window window = this.getWindow();
        //让Dialog显示在屏幕的底部
        window.setGravity(Gravity.CENTER);
        //设置窗口出现和窗口隐藏的动画
        // window.setWindowAnimations(R.style.ios_bottom_dialog_anim);
        //设置BottomDialog的宽高属性
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


        mTxTitle = (TextView) findViewById(R.id.tx_title);
        mImgExit = (ImageView) findViewById(R.id.img_exit);
        mTxContent = (TextView) findViewById(R.id.tx_content);
        mCheckboxKnow = (AppCompatCheckBox) findViewById(R.id.checkbox_know);
        mTxContinue = (TextView) findViewById(R.id.tx_continue);

        mTxContinue.setOnClickListener(onClickListener);
        mImgExit.setOnClickListener(onClickListener);
        mCheckboxKnow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheck = b;
            }
        });

    }


    public boolean isCheck() {
        return isCheck;
    }

    public void setContent(String msg) {
        mTxContent.setText(msg);
    }

    public void setTitle(String msg, int color) {
        mTxTitle.setText(msg);
        if (color != 0) {
            mTxTitle.setTextColor(0xffFF0000);
        }
    }
}
