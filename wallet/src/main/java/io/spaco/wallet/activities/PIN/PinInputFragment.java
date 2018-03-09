package io.spaco.wallet.activities.PIN;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import io.spaco.wallet.R;
import io.spaco.wallet.base.BaseFragment;
import io.spaco.wallet.utils.SpacoWalletUtils;
import io.spaco.wallet.utils.StatusBarUtils;
import io.spaco.wallet.utils.ToastUtils;
import io.spaco.wallet.widget.KeyboardProgress;
import io.spaco.wallet.widget.NumberKeyboard;
import io.spaco.wallet.widget.NumberKeyboardAdapter;

/**
 * Pin界面碎片
 * Created by kimi on 2018/1/23.
 */

public class PinInputFragment extends BaseFragment {

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

    public static PinInputFragment newInstance(Bundle args) {
        PinInputFragment instance = new PinInputFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_input_pin;
    }

    @Override
    protected void initViews(View rootView) {
        StatusBarUtils.statusBarCompat(this);
        numberKeyboard = rootView.findViewById(R.id.numberkeyboard);
        keyboardProgress = rootView.findViewById(R.id.keyboardtips);
        numberKeyboard.setOnNumberKeyboardDownListener(onNumberKeyboardDownListener);
        numberKeyboard.setOnResetEnable(false);
        if (mActivity instanceof PinSetListener) {
            pinSetListener = (PinSetListener) mActivity;
        }
    }

    public void clearInput() {
        keyboardProgress.clearUpdate();
    }

    /**
     * 数字键盘结果回调
     */
    NumberKeyboardAdapter.OnNumberKeyboardDownListener onNumberKeyboardDownListener =
            new NumberKeyboardAdapter.OnNumberKeyboardDownListener() {

                @Override
                public void onNumberKeyboardDown(int number) {
                    keyboardProgress.insetUpdate();
                    if (pinCode.length() < 6) {
                        pinCode.append(number);
                    }
                    if (pinCode.length() == 6) {
                        pinSetListener.onPinSetSuccess(pinCode.toString());
                    }
                }

                @Override
                public void onNumberKeyboardDelete() {
                    keyboardProgress.deleteUpdate();
                    if (pinCode.length() > 0) {
                        pinCode.deleteCharAt(pinCode.length() - 1);
                    }
                }

                @Override
                public void onNumberKeyboardReset() {

                }
            };


    @Override
    protected void initData() {

    }
}
