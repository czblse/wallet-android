package io.spaco.wallet.push;


import java.util.Observable;

/**
 * Created by kimi on 2018/2/22.
 */

public class WalletPush extends Observable {

    public static final int WALLET_UPDATE = 0;//create or delete wallet update
    public static final int TRANSACTION_UPDATE = 1;//transaction update
    public static final int BALANCE_UPDATE  = 2;//balance update

    private WalletPush(){}

    public static WalletPush getInstance(){
        return WalletPushHolder.walletPush;
    }

    /**
     * 钱包更新时发送通知
     */
    public void walletUpdate(){
        setChanged();
        notifyObservers(WALLET_UPDATE);
    }

    /**
     * 交易更新时发送通知
     */
    public void transactionUpdate(){
        setChanged();
        notifyObservers(TRANSACTION_UPDATE);
    }

    /**
     * 余额更新时发送通知
     */
    public void balanceUpdate(){
        setChanged();
        notifyObservers(BALANCE_UPDATE);
    }

    private static class WalletPushHolder{
        private static final WalletPush walletPush = new WalletPush();
    }
}
