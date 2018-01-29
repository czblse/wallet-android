package io.spaco.wallet.beans;

import java.io.Serializable;

/**
 * Created by kimi on 2018/1/29.</br>
 */

public class MainTransactionBean implements Serializable {

    public int flag;//0发送 1接受
    public String sendWallet;//发送方的钱包
    public String sendAddress;//发送方地址
    public String toWallet;//接受方的钱包
    public String toAddress;//接受放地址
    public String transaction;//交易金额
    public String balance;//剩余余额
}
