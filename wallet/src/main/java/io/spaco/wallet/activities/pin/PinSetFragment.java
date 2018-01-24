package io.spaco.wallet.activities.pin;

import android.os.Bundle;
import android.view.View;

import java.util.LinkedList;
import java.util.Stack;

import io.spaco.wallet.R;
import io.spaco.wallet.base.BaseFragment;
import io.spaco.wallet.utils.LogUtils;
import io.spaco.wallet.utils.StatusBarUtils;
import io.spaco.wallet.utils.ToastUtils;
import io.spaco.wallet.widget.KeyboardProgress;
import io.spaco.wallet.widget.NumberKeyboard;
import io.spaco.wallet.widget.NumberKeyboardAdapter;

/**
 * Pin界面碎片
 * Created by kimi on 2018/1/23.
 */

public class PinSetFragment extends BaseFragment {

    /**
     * 数字键盘
     */
    NumberKeyboard numberKeyboard;

    /**
     * 数字键盘进度控件
     */
    KeyboardProgress keyboardProgress;

    /**
     * PIN监听器
     */
    PinSetListener pinSetListener;

    /**
     * PIN安全码
     */
    StringBuilder pinCode = new StringBuilder();

    public static PinSetFragment newInstance(Bundle args){
        PinSetFragment instance = new PinSetFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_pin_set;
    }

    @Override
    protected void initViews(View rootView) {
        StatusBarUtils.statusBarCompat(this);
        numberKeyboard = rootView.findViewById(R.id.numberkeyboard);
        keyboardProgress = rootView.findViewById(R.id.keyboardtips);
        numberKeyboard.setOnNumberKeyboardDownListener(onNumberKeyboardDownListener);
        if(mActivity instanceof PinSetListener){
            pinSetListener = (PinSetListener) mActivity;
        }
    }

    /**
     * 数字键盘结果回调
     */
    NumberKeyboardAdapter.OnNumberKeyboardDownListener onNumberKeyboardDownListener = new NumberKeyboardAdapter.OnNumberKeyboardDownListener(){

        @Override
        public void onNumberKeyboardDown(int number) {
            keyboardProgress.insetUpdate();
            if(pinCode.length() < 4){
                pinCode.append(number);
            }
            if(pinCode.length() == 4){
                pinSetListener.onPinSetSuccess(pinCode.toString());
            }
        }

        @Override
        public void onNumberKeyboardDelete() {
            keyboardProgress.deleteUpdate();
            if(pinCode.length() > 0){
                pinCode.deleteCharAt(pinCode.length() - 1);
            }
        }
    };

    @Override
    protected void initData() {

    }
}
