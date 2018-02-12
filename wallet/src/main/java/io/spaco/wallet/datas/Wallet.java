package io.spaco.wallet.datas;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import mobile.Mobile;

/**
 * Created by zjy on 2018/1/22.
 */

public class Wallet implements Serializable{

    String walletID;
    String walletType;
    String walletName;
    int walletDeep;
    String balance = "0.00";

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


    @Override
    public String toString() {
        return "Wallet{" +
                "walletID='" + walletID + '\'' +
                ", walletType='" + walletType + '\'' +
                ", walletName='" + walletName + '\'' +
                ", walletDeep=" + walletDeep +
                ", balance='" + balance + '\'' +
                '}';
    }

    public static Wallet buildTestData(){
        Wallet wallet = new Wallet();
        wallet.setWalletType("sky");
        wallet.setWalletName("test");
        wallet.setWalletDeep(15);
        return wallet;
    }

    public static double getBalanceFromRawData(String rawString){
        try {
            JSONObject jsonObject = new JSONObject(rawString);
            return  jsonObject.optDouble("balance");
        } catch (JSONException e) {
            e.printStackTrace();
            return 0d;
        }
    }

}
