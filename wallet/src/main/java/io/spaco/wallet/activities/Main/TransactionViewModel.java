package io.spaco.wallet.activities.Main;

import com.google.gson.Gson;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
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
     * @return
     */
    public Observable<ArrayList<TransactionInfo>> getAllTransaction(){
        return Observable.create(new ObservableOnSubscribe<ArrayList<Transaction>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<Transaction>> emitter) throws Exception {
                emitter.onNext(TransactionManager.getInstance().getAllTransaction());
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<ArrayList<Transaction>, ArrayList<TransactionInfo>>() {
                    @Override
                    public ArrayList<TransactionInfo> apply(ArrayList<Transaction> transactions) throws Exception {
                        ArrayList<TransactionInfo> result = new ArrayList<>();
                        Gson gson = new Gson();
                        for(Transaction transaction : transactions){
                            String valuesJson = Mobile.getTransactionByID(transaction.coinType, transaction.txid);
                            result.add(gson.fromJson(valuesJson,TransactionInfo.class));
                        }
                        return result;
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 发送交易
     */
    public Observable<Transaction> sendTransaction(final Transaction transaction){
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
