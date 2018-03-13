package io.spaco.wallet.activities.Transaction;

import com.google.gson.Gson;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.spaco.wallet.beans.TransactionInfo;
import io.spaco.wallet.datas.Transaction;
import io.spaco.wallet.datas.TransactionManager;
import mobile.Mobile;

/**
 * Created by kimi on 2018/2/11.</br>
 */

public class TransactionViewModel {


    /**
     * 查询本地缓存的所有交易记录
     */
    public Observable<ArrayList<TransactionInfo>> getAllTransaction(final String walletId,
            final String cointype) {
        return Observable.create(new ObservableOnSubscribe<ArrayList<Transaction>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<Transaction>> emitter)
                    throws Exception {
                emitter.onNext(
                        TransactionManager.getInstance().getAllTransaction(walletId, cointype));
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<ArrayList<Transaction>, ArrayList<TransactionInfo>>() {
                    @Override
                    public ArrayList<TransactionInfo> apply(ArrayList<Transaction> transactions)
                            throws Exception {
                        ArrayList<TransactionInfo> result = new ArrayList<>();
                        Gson gson = new Gson();
                        for (Transaction transaction : transactions) {
                            String valuesJson = Mobile.getTransactionByID(transaction.coinType,
                                    transaction.txid);
                            TransactionInfo transactionInfo = gson.fromJson(valuesJson,
                                    TransactionInfo.class);
                            transactionInfo.setTime(transaction.time);
                            transactionInfo.setToWallet(transaction.toWallet);
                            transactionInfo.setAmount(transaction.amount);
                            transactionInfo.setCoinType(transaction.coinType);
                            result.add(transactionInfo);
                        }
                        return result;
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 发送交易
     */
    public Observable<Transaction> sendTransaction(final Transaction transaction) {
        return Observable.create(new ObservableOnSubscribe<Transaction>() {
            @Override
            public void subscribe(ObservableEmitter<Transaction> emitter) throws Exception {
                emitter.onNext(TransactionManager.getInstance().sendTransaction(transaction));
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
