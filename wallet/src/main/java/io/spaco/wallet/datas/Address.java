package io.spaco.wallet.datas;

import java.util.ArrayList;

/**
 * Created by zjy on 2018/2/10.
 */

public class Address {
    String address;
    String pubkey;
    String seckey;
    String addressId;
    String balance;
    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddresBalance() {
        return addresBalance;
    }

    public void setAddresBalance(String addresBalance) {
        this.addresBalance = addresBalance;
    }

    String addresBalance;
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public String getSeckey() {
        return seckey;
    }

    public void setSeckey(String seckey) {
        this.seckey = seckey;
    }
}
