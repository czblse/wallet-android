package io.spaco.wallet.push;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by kimi on 2018/2/22.
 */

public abstract class WalletPushListener implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        try {
            int value = (int) arg;
            switch (value){
                case WalletPush.WALLET_UPDATE:
                    walletUpdate();
                    break;
                case WalletPush.TRANSACTION_UPDATE:
                    transactionUpdate();
                    break;
                case WalletPush.BALANCE_UPDATE:
                    balanceUpdate();
                    break;
            }
        }catch (Exception e){

        }
    }

    protected abstract void walletUpdate();
    protected abstract void transactionUpdate();
    protected abstract void balanceUpdate();
}
