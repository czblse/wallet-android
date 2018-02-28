package io.spaco.wallet.activities.BackupsWallet;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.spaco.wallet.R;
import io.spaco.wallet.activities.Main.WalletViewModel;
import io.spaco.wallet.base.BaseFragment;
import io.spaco.wallet.common.Constant;
import io.spaco.wallet.datas.Wallet;

/**
 * Created by kimi on 2018/2/28.</br>
 */

public class BackupsWalletFragment extends BaseFragment {

    Wallet wallet;
    TextView tvSeed;

    /**
     * viewmodel
     */
    WalletViewModel walletViewModel = new WalletViewModel();

    public static BackupsWalletFragment newInstance(Bundle bundle) {
        BackupsWalletFragment instance = new BackupsWalletFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_backups_wallet;
    }

    @Override
    protected void initViews(View rootView) {
        tvSeed = rootView.findViewById(R.id.seed);
        tvSeed.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context
                        .CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tvSeed.getText().toString());
                Toast.makeText(getContext(), R.string.str_copy_success, Toast.LENGTH_LONG).show();
                return true;
            }
        });
        wallet = (Wallet) getArguments().getSerializable(Constant.KEY_WALLET);
    }

    @Override
    protected void initData() {
        mActivity.showDialog(1);
        walletViewModel.getWalletSeed(wallet.getWalletID())
                .compose(this.<String>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        tvSeed.setText(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mActivity.dismissDialog(1);
                    }
                });
    }
}
