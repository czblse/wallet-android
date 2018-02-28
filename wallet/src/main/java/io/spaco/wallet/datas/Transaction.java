package io.spaco.wallet.datas;

/**
 * Created by zjy on 2018/2/10.
 */

public class Transaction {
    public String coinType;
    public String txid;
    public String fromWallet;
    public String toWallet;
    public String amount;
    public String nodes;
    public String state;
    public String time;

    @Override
    public String toString() {
        return "Transaction{" +
                "coinType='" + coinType + '\'' +
                ", txid='" + txid + '\'' +
                ", fromWallet='" + fromWallet + '\'' +
                ", toWallet='" + toWallet + '\'' +
                ", amount='" + amount + '\'' +
                ", nodes='" + nodes + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getFromWallet() {
        return fromWallet;
    }

    public void setFromWallet(String fromWallet) {
        this.fromWallet = fromWallet;
    }

    public String getToWallet() {
        return toWallet;
    }

    public void setToWallet(String toWallet) {
        this.toWallet = toWallet;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
