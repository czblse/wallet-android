package io.spaco.wallet.activities.pin;

import android.os.Bundle;
import android.view.View;

import io.spaco.wallet.R;
import io.spaco.wallet.base.BaseFragment;
import io.spaco.wallet.utils.StatusBarUtils;
import io.spaco.wallet.utils.ToastUtils;
import io.spaco.wallet.widget.KeyboardProgress;
import io.spaco.wallet.widget.NumberKeyboard;
import io.spaco.wallet.widget.NumberKeyboardAdapter;

/**
 * Pin确认界面碎片
 * Created by kimi on 2018/1/23.
 */

public class VerifyPinSetFragment extends BaseFragment {

    NumberKeyboard numberKeyboard;
    KeyboardProgress keyboardProgress;

    public static VerifyPinSetFragment newInstance(Bundle args){
        VerifyPinSetFragment instance = new VerifyPinSetFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_verify_pin_set;
    }

    @Override
    protected void initViews(View rootView) {
        StatusBarUtils.statusBarCompat(this);
        numberKeyboard = rootView.findViewById(R.id.numberkeyboard);
        keyboardProgress = rootView.findViewById(R.id.keyboardtips);
        numberKeyboard.setOnNumberKeyboardDownListener(onNumberKeyboardDownListener);
    }

    NumberKeyboardAdapter.OnNumberKeyboardDownListener onNumberKeyboardDownListener = new NumberKeyboardAdapter.OnNumberKeyboardDownListener(){

        @Override
        public void onNumberKeyboardDown(int number) {
            keyboardProgress.insetUpdate();
        }

        @Override
        public void onNumberKeyboardDelete() {
            keyboardProgress.deleteUpdate();
            ToastUtils.show("delete");
        }
    };

    @Override
    protected void initData() {

    }
}
