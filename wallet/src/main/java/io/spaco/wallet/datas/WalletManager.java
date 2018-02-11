package io.spaco.wallet.datas;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import io.spaco.wallet.base.SpacoAppliacation;

/**
 * 钱包管理
 * Created by kimi on 2018/2/11.</br>
 */

public class WalletManager {

    /**
     * 存储钱包的sp文件
     */
    private SharedPreferences walletSharedPreferences;
    /**
     * 钱包集合
     */
    private List<Wallet> wallets = new ArrayList<>();
    /**
     * 监听器集合
     */
    private List<OnWalletChangeListener> onWalletChangeListenerList = new ArrayList<>();

    private Gson gosn = new Gson();

    private WalletManager(){
        String name = "wallet_preferences";
        walletSharedPreferences = SpacoAppliacation.mInstance.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * 静态内部类单利
     */
    private static class WalletManagerHolder{
        private static WalletManager minstance = new WalletManager();
    }

    public static WalletManager getInstance(){
        return WalletManagerHolder.minstance;
    }

    /**
     * 添加监听器
     * @param onWalletChangeListener
     */
    public void addOnWalletChangeListener(OnWalletChangeListener onWalletChangeListener){
        if(!onWalletChangeListenerList.contains(onWalletChangeListener))
            onWalletChangeListenerList.add(onWalletChangeListener);
    }

    /**
     * 移除监听器
     * @param onWalletChangeListener
     */
    public void deleteOnWalletChangeListener(OnWalletChangeListener onWalletChangeListener){
        if(onWalletChangeListenerList.contains(onWalletChangeListener))
            onWalletChangeListenerList.remove(onWalletChangeListener);
    }

    /**
     * 增加一个钱包
     * @param wallet
     * @return true代表增加成功，false表示失败,或者已经存在此钱包
     */
    public boolean saveWallet(Wallet wallet){
        SharedPreferences.Editor edit = walletSharedPreferences.edit();
        edit.putString(wallet.getWalletID(),gosn.toJson(wallet));
        edit.commit();
        //通知监听器
        for(OnWalletChangeListener onWalletChangeListener : onWalletChangeListenerList){
            onWalletChangeListener.onSaveWallet(wallet);
        }
        return true;
    }

    /**
     * 删除一个钱包
     * @param wallet
     * @return true代表删除成功，false表示失败,或者此钱包不存在
     */
    public boolean removeWallet(Wallet wallet){
        String walletJson = walletSharedPreferences.getString(wallet.getWalletID(),"");
        if(TextUtils.isEmpty(walletJson)){
            return false;
        }
        SharedPreferences.Editor edit = walletSharedPreferences.edit();
        edit.remove(wallet.getWalletID());
        edit.commit();
        //通知监听器
        for(OnWalletChangeListener onWalletChangeListener : onWalletChangeListenerList){
            onWalletChangeListener.onRemoveWallet(wallet);
        }
        return true;
    }

    /**
     * 获取所有的钱包
     * @return
     */
    public List<Wallet> getAllWallet(){
        wallets.clear();
        Map<String,String> all = (Map<String, String>) walletSharedPreferences.getAll();
        if(all.isEmpty()){
            return wallets;
        }
        Iterator<Map.Entry<String, String>> iterator = all.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            wallets.add(gosn.fromJson(next.getValue(),Wallet.class));
        }
        return wallets;
    }


    /**
     * 监听器
     */
    public interface OnWalletChangeListener{
        void onSaveWallet(Wallet wallet);
        void onRemoveWallet(Wallet wallet);
    }

}
