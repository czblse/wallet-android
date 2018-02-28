package io.spaco.wallet.activities;

import android.support.v4.app.FragmentTransaction;

import io.spaco.wallet.R;
import io.spaco.wallet.activities.Transaction.TransactionFragment;
import io.spaco.wallet.base.BaseActivity;

/**
 * 钱包交易记录的界面
 */
public class TransactionActivity extends BaseActivity {


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_transaction;
    }

    @Override
    protected void initViews() {
        if(savedInstanceState == null){
            TransactionFragment transactionFragment = TransactionFragment.newInstance(getIntent().getExtras());
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container,transactionFragment,transactionFragment.getClass().getSimpleName());
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void initData() {

    }
}
