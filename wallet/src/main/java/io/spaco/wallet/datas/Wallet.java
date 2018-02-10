package io.spaco.wallet.datas;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zjy on 2018/1/22.
 */

public class Wallet {
    String walletType;
    String walletName;
    int walletDeep;

    public String getWalletID() {
        return walletID;
    }

    String walletID;

    public Wallet(String walletType, String walletName) {
        this.walletType = walletType;
        this.walletName = walletName;
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
            return jsonObject.optInt("balance");
        } catch (JSONException e) {
            e.printStackTrace();
            return 0f;
        }
    }
}
