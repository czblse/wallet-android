package io.spaco.wallet.datas;

/**
 * Created by zjy on 2018/2/10.
 */

public class Address {
    String address;
    String pubkey;
    String seckey;

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
