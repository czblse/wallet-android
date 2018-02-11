
package io.spaco.wallet.datas;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import io.spaco.wallet.base.SpacoAppliacation;

/**
 * 交易管理
 * Created by kimi on 2018/2/11.</br>
 */

public class TransactionManager {

    SharedPreferences transactionSharedPreferences;
    ArrayList<Transaction> transactions = new ArrayList<>();
    Gson gson = new Gson();

    private TransactionManager(){
        String name = "transaction_preferences";
        transactionSharedPreferences = SpacoAppliacation.mInstance.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static TransactionManager getInstance(){
        return TransactionManagerHolder.mInstance;
    }

    /**
     * 保存交易记录
     * @param transaction
     * @return
     */
    public boolean saveTransaction(Transaction transaction){
        if(!transactionSharedPreferences.contains(transaction.txid)){
            SharedPreferences.Editor edit = transactionSharedPreferences.edit();
            edit.putString(transaction.coinType + transaction.txid,gson.toJson(transaction));
            edit.commit();
            return true;
        }
        return false;
    }

    /**
     * 获取交易记录
     * @return
     */
    public ArrayList<Transaction> getAllTransaction(){
        transactions.clear();
        Map<String, String> all = (Map<String, String>) transactionSharedPreferences.getAll();
        Iterator<Map.Entry<String, String>> iterator = all.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            transactions.add(gson.fromJson(next.getValue(),Transaction.class));
        }
        return transactions;
    }


    private static class TransactionManagerHolder{
        private static final TransactionManager mInstance = new TransactionManager();
    }
}
