package io.spaco.wallet.viewmodel;

import com.google.gson.Gson;

import java.util.ArrayList;

import io.reactivex.Observable;
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
        return  Observable.just(TransactionManager.getInstance().getAllTransaction())
                .subscribeOn(Schedulers.io())
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
}
