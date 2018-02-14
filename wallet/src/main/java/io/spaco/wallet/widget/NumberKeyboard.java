package io.spaco.wallet.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import io.spaco.wallet.R;

/**
 * 数字键盘
 * Created by kimi on 2018/1/23.
 */

public class NumberKeyboard extends FrameLayout {

    RecyclerView recyclerView;
    NumberKeyboardAdapter numberKeyboardAdapter;

    public NumberKeyboard(@NonNull Context context) {
        this(context,null);
    }

    public NumberKeyboard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.views_number_keyboard,this,true);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        numberKeyboardAdapter = new NumberKeyboardAdapter();
        recyclerView.setAdapter(numberKeyboardAdapter);
    }

    /**
     * 设置数字键盘监听器
     * @param onNumberKeyboardDownListener
     */
    public void setOnNumberKeyboardDownListener(NumberKeyboardAdapter.OnNumberKeyboardDownListener onNumberKeyboardDownListener){
        numberKeyboardAdapter.setOnNumberKeyboardDownListener(onNumberKeyboardDownListener);
    }

    public void  setOnResetEnable(boolean enable){
        numberKeyboardAdapter.setShowBackward(enable);
    }

}
