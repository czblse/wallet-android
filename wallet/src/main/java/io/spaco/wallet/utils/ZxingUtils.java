package io.spaco.wallet.utils;


import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.graphics.Color.BLACK;

/**
 * Zxing工具类
 * Created by kimi on 2018/1/29.</br>
 */

public class ZxingUtils {


    /**
     * 生成二维码图片（不带图片）
     * @param text 文本
     * @param widthAndHeight 二维码的宽高
     */
    public static Observable<Bitmap> createQRCode(final String text, final int widthAndHeight){
        ObservableOnSubscribe<Bitmap> observableOnSubscribe = new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
                try {
                    Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
                    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                    BitMatrix matrix = new MultiFormatWriter().encode(text,
                            BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);

                    int width = matrix.getWidth();
                    int height = matrix.getHeight();
                    int[] pixels = new int[width * height];
                    //画黑点
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            if (matrix.get(x, y)) {
                                pixels[y * width + x] = BLACK; //0xff000000
                            }
                        }
                    }
                    Bitmap bitmap = Bitmap.createBitmap(width, height,
                            Bitmap.Config.ARGB_8888);
                    bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
                    emitter.onNext(bitmap);
                    emitter.onComplete();
                }catch (Exception e){
                    emitter.onError(e);
                }
            }
        };
        return  Observable.create(observableOnSubscribe).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
