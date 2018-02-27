package io.spaco.wallet.datas;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.spaco.wallet.base.SpacoAppliacation;
import io.spaco.wallet.utils.LogUtils;
import io.spaco.wallet.utils.NetUtil;
import mobile.Mobile;

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

    private Gson gson = new Gson();

    private WalletManager(){
        String name = "wallet_preferences";
        walletSharedPreferences = SpacoAppliacation.mInstance.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * 静态内部类单利
     */
    private static class WalletManagerHolder{
        private static final WalletManager minstance = new WalletManager();
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
        try {
            boolean exist = Mobile.isExist(wallet.getWalletID());
            if(exist){
                SharedPreferences.Editor edit = walletSharedPreferences.edit();
                edit.putString(wallet.getWalletName(),gson.toJson(wallet));
                edit.commit();
                //通知监听器
                for(OnWalletChangeListener onWalletChangeListener : onWalletChangeListenerList){
                    onWalletChangeListener.onSaveWallet(wallet);
                }
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 删除一个钱包
     * @param wallet
     * @return true代表删除成功，false表示失败,或者此钱包不存在
     */
    public boolean removeWallet(Wallet wallet){
        String walletJson = walletSharedPreferences.getString(wallet.getWalletName(),"");
        if(TextUtils.isEmpty(walletJson)){
            return false;
        }
        try {
            if(Mobile.isExist(wallet.getWalletID())){
                Mobile.remove(wallet.getWalletID());
                SharedPreferences.Editor edit = walletSharedPreferences.edit();
                edit.remove(wallet.getWalletName());
                edit.commit();
                //通知监听器
                for(OnWalletChangeListener onWalletChangeListener : onWalletChangeListenerList){
                    onWalletChangeListener.onRemoveWallet(wallet);
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
            Wallet wallet = gson.fromJson(next.getValue(), Wallet.class);
            wallets.add(wallet);
        }
        return wallets;
    }

    /**
     * 判断钱包名称是否已经存在
     * @param walletName
     * @return
     */
    public boolean isExitWallet(String walletName){
        Map<String,String> all = (Map<String, String>) walletSharedPreferences.getAll();
        Iterator<Map.Entry<String, String>> iterator = all.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            if(walletName.equals(next.getKey()))
                return true;
        }
        return false;
    }

    /**
     * 监听器
     */
    public interface OnWalletChangeListener{
        void onSaveWallet(Wallet wallet);
        void onRemoveWallet(Wallet wallet);
    }

    /**
     * 获取钱包已创建的所有地址
     * @param walletType
     * @param walletID
     * @return
     */
    public List<Address> getAddressesByWalletId(String walletType, String walletID){
        List<Address> result = new ArrayList<>();
        try {
            String rawAddresses = Mobile.getAddresses(walletID);
            JSONObject jsonObject = new JSONObject(rawAddresses);
            JSONArray addressArray = jsonObject.optJSONArray("addresses");
            if (addressArray != null && addressArray.length() > 0) {
                int len = addressArray.length();
                for (int i = 0; i< len; i++) {
                    Address address = new Address();
                    address.setAddressId(String.valueOf(i + 1));
                    address.setAddress(String.valueOf(addressArray.get(i)));
                    address.setAddresBalance(String.valueOf(Wallet.getBalanceFromRawData(Mobile.getBalance(walletType, address.getAddress()))));
                    result.add(address);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
