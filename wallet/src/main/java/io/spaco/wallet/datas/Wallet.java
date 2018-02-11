package io.spaco.wallet.datas;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mobile.Mobile;

/**
 * Created by zjy on 2018/1/22.
 */

public class Wallet {

    String walletID;
    String walletType;
    String walletName;
    int walletDeep;
    String balance;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getWalletID() {
        return walletID;
    }


    public Wallet(String walletType, String walletName, String walletID) {
        this.walletType = walletType;
        this.walletName = walletName;
        this.walletID = walletID;
    }

    private Wallet(){

    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public int getWalletDeep() {
        return walletDeep;
    }

    public void setWalletDeep(int walletDeep) {
        this.walletDeep = walletDeep;
    }

    public Wallet restoreWalletFromLocal() {
        Wallet wallet = new Wallet();
        return wallet;
    }

    public void save() {
        WalletManager.getInstance().saveWallet(this);
    }

    public static Wallet buildTestData(){
        Wallet wallet = new Wallet();
        wallet.setWalletType("sky");
        wallet.setWalletName("test");
        wallet.setWalletDeep(15);
        return wallet;
    }

    public static float getBalanceFromRawData(String rawString){
        try {
            JSONObject jsonObject = new JSONObject(rawString);
            return (float) jsonObject.optDouble("balance");
        } catch (JSONException e) {
            e.printStackTrace();
            return 0f;
        }
    }

}
