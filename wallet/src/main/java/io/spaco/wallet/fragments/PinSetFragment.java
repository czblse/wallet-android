package io.spaco.wallet.fragments;

import android.os.Bundle;
import android.view.View;

import io.spaco.wallet.R;
import io.spaco.wallet.base.BaseFragment;
import io.spaco.wallet.utils.ToastUtils;
import io.spaco.wallet.widget.KeyboardProgress;
import io.spaco.wallet.widget.NumberKeyboard;
import io.spaco.wallet.widget.NumberKeyboardAdapter;
import mobile.Mobile;

/**
 * Pin界面碎片
 * Created by kimi on 2018/1/23.
 */

public class PinSetFragment extends BaseFragment {

    NumberKeyboard numberKeyboard;
    KeyboardProgress keyboardTips;

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
        numberKeyboard = rootView.findViewById(R.id.numberkeyboard);
        keyboardTips = rootView.findViewById(R.id.keyboardtips);
        numberKeyboard.setOnNumberKeyboardDownListener(onNumberKeyboardDownListener);
    }

    NumberKeyboardAdapter.OnNumberKeyboardDownListener onNumberKeyboardDownListener = new NumberKeyboardAdapter.OnNumberKeyboardDownListener(){

        @Override
        public void onNumberKeyboardDown(int number) {
            keyboardTips.insetUpdate();
            if(keyboardTips.isPassVerify()){
                ToastUtils.show("pass verify");
            }else{
                ToastUtils.show(number + "");
            }
        }

        @Override
        public void onNumberKeyboardDelete() {
            keyboardTips.deleteUpdate();
            ToastUtils.show("delete");
        }
    };

    @Override
    protected void initData() {
//        String result = Mobile.newSeed();
//        ToastUtils.show(result);
    }
}
