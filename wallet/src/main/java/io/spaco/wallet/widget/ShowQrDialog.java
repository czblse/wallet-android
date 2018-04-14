package io.spaco.wallet.widget;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.disposables.Disposable;
import io.spaco.wallet.R;
import io.spaco.wallet.utils.ToastUtils;
import io.spaco.wallet.utils.ZxingUtils;

/**
 * @author: lh on 2018/1/25 13:59.
 * Email:luchefg@gmail.com
 * Description: 展示二维码
 */

public class ShowQrDialog extends Dialog {

    private Context context;
    private View mainView;
    private View.OnClickListener onClickListener;

    private TextView mTxTitle;
    private ImageView mImgExit;
    private ImageView mImgQrcode;
    private TextView mTxQrAdress;
    private ImageView mImgCopy;


    public ShowQrDialog(@NonNull Context context) {
        super(context, R.style.customDialog);
        this.context = context;

        mainView = LayoutInflater.from(context).inflate(R.layout.dialog_qrcode, null);

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
        mImgQrcode = (ImageView) findViewById(R.id.img_qrcode);
        mTxQrAdress = (TextView) findViewById(R.id.tx_qr_adress);
        mImgCopy = (ImageView) findViewById(R.id.img_copy);

        mImgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context
                        .CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(mTxQrAdress.getText().toString());
                Toast.makeText(getContext(), R.string.str_copy_success, Toast.LENGTH_LONG).show();
            }
        });
        mImgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mainView = LayoutInflater.from(context).inflate(R.layout.dialog_qrcode, null);
//
//        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.8);
//        this.setContentView(mainView,
//                new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
//        //点击空白区域可以取消dialog
//        this.setCanceledOnTouchOutside(true);
//        //点击back键可以取消dialog
//        this.setCancelable(true);
//        Window window = this.getWindow();
//        //让Dialog显示在屏幕的底部
//        window.setGravity(Gravity.CENTER);
//        //设置BottomDialog的宽高属性
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(lp);
//
//
//        mTxTitle = (TextView) findViewById(R.id.tx_title);
//        mImgExit = (ImageView) findViewById(R.id.img_exit);
//        mImgQrcode = (ImageView) findViewById(R.id.img_qrcode);
//        mTxQrAdress = (TextView) findViewById(R.id.tx_qr_adress);
//        mImgCopy = (ImageView) findViewById(R.id.img_copy);
//
//        mImgCopy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context
//                        .CLIPBOARD_SERVICE);
//                // 将文本内容放到系统剪贴板里。
//                cm.setText(mTxQrAdress.getText().toString());
//                Toast.makeText(context, "复制成功", Toast.LENGTH_LONG).show();
//            }
//        });
//        mImgExit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//            }
//        });
//
//    }


    public void setKey(String type, String key) {
        JSONObject finalShowStringJson = new JSONObject();
        try {
            finalShowStringJson.put(type, key);
            mTxQrAdress.setText(type + "：" + key);
            ZxingUtils.createQRCode(finalShowStringJson.toString(),500).subscribe(new io.reactivex.Observer<Bitmap>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Bitmap bitmap) {
                    mImgQrcode.setImageBitmap(bitmap);
                }

                @Override
                public void onError(Throwable e) {
                    ToastUtils.show("出错了");
                }

                @Override
                public void onComplete() {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
