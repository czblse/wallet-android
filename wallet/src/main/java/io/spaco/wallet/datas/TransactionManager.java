
package io.spaco.wallet.datas;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import io.spaco.wallet.base.SpacoAppliacation;
import io.spaco.wallet.utils.LogUtils;
import mobile.Mobile;

/**
 * 交易管理
 * Created by kimi on 2018/2/11.</br>
 */

public class TransactionManager {

    private static final String SEPARATOR = "-";
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
        SharedPreferences.Editor edit = transactionSharedPreferences.edit();
        edit.putString(transaction.fromWallet + SEPARATOR + transaction.coinType + SEPARATOR + transaction.txid,gson.toJson(transaction));
        edit.commit();
        return true;
    }

    /**
     * 获取交易记录
     * @return
     */
    public ArrayList<Transaction> getAllTransaction(String walletId,String cointype){
        transactions.clear();
        Map<String, String> all = (Map<String, String>) transactionSharedPreferences.getAll();
        Iterator<Map.Entry<String, String>> iterator = all.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            if(key.startsWith(walletId + SEPARATOR + cointype)){
                transactions.add(gson.fromJson(next.getValue(),Transaction.class));
            }
        }
        return transactions;
    }

    /**
     * 发送交易
     * walletID：钱包ID
     *toAddr：收件人地址
     *金额：你将发送的硬币，它的值必须是0.001的倍数。
     */
    public Transaction sendTransaction(Transaction transaction){
        try {
            String state = Mobile.send(transaction.coinType,transaction.fromWallet,transaction.toWallet,transaction.amount);
            LogUtils.d("send transaction result = " + state);
            //发送成功的话，缓存到本地
            transaction.txid = gson.fromJson(state, Transaction.class).txid;
            transaction.time = System.currentTimeMillis()+"";
            saveTransaction(transaction);
            transaction.setState(state);
        } catch (Exception e) {
            e.printStackTrace();
            return transaction;
        }
        return transaction;
    }


    private static class TransactionManagerHolder{
        private static final TransactionManager mInstance = new TransactionManager();
    }
}
