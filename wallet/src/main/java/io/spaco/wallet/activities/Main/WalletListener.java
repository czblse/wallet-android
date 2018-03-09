package io.spaco.wallet.activities.Main;

import io.spaco.wallet.datas.Wallet;

/**
 * Created by kimi on 2018/1/29.</br>
 */

public interface WalletListener {

    /**
     * 点击事件
     * @param position
     * @param bean
     */
    void onItemClick(int position,Wallet bean);

    /**
     * 创建钱包
     */
    void onCreateWallet();

    /**
     * 导入钱包
     */
    void onImportWallet();
}
