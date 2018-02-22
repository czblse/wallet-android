package io.spaco.wallet.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.spaco.wallet.R;


/**
 * Created by jinyang.zheng on 2017/11/6.
 */

public class SimpleDialog extends Dialog{
    private CharSequence mBtnLeftStr;
    private CharSequence mBtnRightStr;
    protected CharSequence mMessageStr;
    private SimpleListener mListener;
    private View mCustomView;
    private int mGravity;
    private int mMaxLines = 10; // 最大行数
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.txv_left_btn) {
                if (mListener != null) mListener.onBtnLeftClick(SimpleDialog.this);
            } else if (v.getId() == R.id.txv_right_btn) {
                if (mListener != null) mListener.onBtnRightClick(SimpleDialog.this);
            }
        }
    };

    protected SimpleDialog(Context context) {
        super(context, R.style.SimpleDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_dialog);
        TextView txv = (TextView) findViewById(R.id.txv_message);
        if (mCustomView != null) {
            txv.setVisibility(View.GONE);
            mCustomView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            RelativeLayout fl = findViewById(R.id.rl_container);
            fl.addView(mCustomView);
        } else {
            txv.setVisibility(View.VISIBLE);
            txv.setText(mMessageStr);
            txv.setGravity(mGravity);
            txv.setMaxLines(mMaxLines);
        }

        txv = (TextView) findViewById(R.id.txv_left_btn);
        txv.setText(mBtnLeftStr);
        txv.setOnClickListener(getOnClickListener());
        txv.setVisibility(TextUtils.isEmpty(mBtnLeftStr) ? View.GONE : View.VISIBLE);

        txv = (TextView) findViewById(R.id.txv_right_btn);
        txv.setText(mBtnRightStr);
        txv.setOnClickListener(getOnClickListener());
        txv.setVisibility(TextUtils.isEmpty(mBtnRightStr) ? View.GONE : View.VISIBLE);

        findViewById(R.id.middleDivider).setVisibility(
                TextUtils.isEmpty(mBtnLeftStr) || TextUtils.isEmpty(mBtnRightStr) ?
                        View.GONE : View.VISIBLE
        );
    }

    /**
     * 点击按钮响应，子类覆写之
     * @return
     */
    protected View.OnClickListener getOnClickListener(){
        return mOnClickListener;
    }

    public interface SimpleListener {
        void onBtnLeftClick(Dialog dialog);

        void onBtnRightClick(Dialog dialog);
    }

    public static class SimpleDialogBuilder {
        private CharSequence mBtnLeftStr;
        private CharSequence mBtnRightStr;
        private CharSequence mMessageStr;
        private SimpleListener mListener;
        private View mCustomView;
        private int mGravity;
        private int mMaxLines = 10; // 设置最大行数

        public SimpleDialogBuilder setMessage(CharSequence str) {
            mMessageStr = str;
            return this;
        }

        public SimpleDialogBuilder setMessageGravity(int gravity) {
            mGravity = gravity;
            return this;
        }

        public SimpleDialogBuilder setLeftBtnStr(CharSequence str) {
            mBtnLeftStr = str;
            return this;
        }

        public SimpleDialogBuilder setRightBtnStr(CharSequence str) {
            mBtnRightStr = str;
            return this;
        }

        public SimpleDialogBuilder setSimpleListener(SimpleListener listener) {
            mListener = listener;
            return this;
        }

        public SimpleDialogBuilder setMaxLines(int maxLines) {
            mMaxLines = maxLines;
            return this;
        }

        /**
         * 如果设置了CustomView，就会忽略通过{@link #setMessage(CharSequence)}设置的文字消息
         */
        public SimpleDialogBuilder setCustomView(Context context, int layoutId) {
            mCustomView = LayoutInflater.from(context).inflate(layoutId, null, false);
            return this;
        }

        /**
         * 如果设置了CustomView，就会忽略通过{@link #setMessage(CharSequence)}设置的文字消息
         */
        public SimpleDialogBuilder setCustomView(View view) {
            mCustomView = view;
            return this;
        }

        public SimpleDialog createDialog(Context context) {
            if (mMessageStr == null)
                return null;

            SimpleDialog dialog = genereteInstance(context);
            dialog.mMessageStr = mMessageStr;
            dialog.mBtnLeftStr = mBtnLeftStr;
            dialog.mBtnRightStr = mBtnRightStr;
            dialog.mListener = mListener;
            dialog.mGravity = mGravity;
            dialog.mMaxLines = mMaxLines;
            dialog.mCustomView = mCustomView;
            return dialog;
        }

        protected SimpleDialog genereteInstance(Context context){
            return new SimpleDialog(context);
        }
    }
}
