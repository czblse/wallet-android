package io.spaco.wallet.beans;

import java.io.Serializable;

/**
 * Created by kimi on 2018/1/29.</br>
 */

public class WalletDetailsBean implements Serializable {

    public int id;//钱包地址id
    public String address;//钱包地址
    public String skyHours;//sky
    public String totalBalance;//余额

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSkyHours() {
        return skyHours;
    }

    public void setSkyHours(String skyHours) {
        this.skyHours = skyHours;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    @Override
    public String toString() {
        return "WalletDetailsBean{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", skyHours='" + skyHours + '\'' +
                ", totalBalance='" + totalBalance + '\'' +
                '}';
    }
}
