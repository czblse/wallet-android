package io.spaco.wallet.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 配合数字键盘的提醒控件
 * Created by kimi on 2018/1/23.
 */

public class KeyboardProgress extends View {

    private int radius = 18;//默认半径为18
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int normalColor = Color.parseColor("#1E2227");
    private int updateColor = Color.parseColor("#FFC125");
    private int maxSize = 6;
    private int progress = 0;

    public KeyboardProgress(Context context) {
        this(context, null);
    }

    public KeyboardProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(normalColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(radius * 2 + radius * 4 * maxSize, MeasureSpec.EXACTLY);
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(radius * 2, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int minWidth = getMeasuredWidth() / maxSize;
        int cx = minWidth / 2;
        int cy = getHeight() / 2;
        for (int i = 1; i <= maxSize; i++) {
            if (i <= progress) {
                paint.setColor(updateColor);
            } else {
                paint.setColor(normalColor);
            }
            canvas.drawCircle(cx, cy, radius, paint);
            cx += minWidth;
        }
    }

    /**
     * 增加刷新
     */
    public void insetUpdate() {
        if (progress < maxSize) {
            progress++;
            invalidate();
        }
    }

    /**
     * 清楚刷新
     */
    public void clearUpdate() {
        for (int i = 0;i<progress;i++){
            progress--;
            invalidate();
        }
    }

    /**
     * 删除刷新
     */
    public void deleteUpdate() {
        if (progress > 0) {
            progress--;
            invalidate();
        }
    }


    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        progress = bundle.getInt("progress");
        super.onRestoreInstanceState(bundle.getParcelable("super"));
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putInt("progress", progress);
        bundle.putParcelable("super", parcelable);
        return bundle;
    }
}
