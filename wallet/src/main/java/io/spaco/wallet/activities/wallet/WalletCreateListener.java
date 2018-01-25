package io.spaco.wallet.activities.wallet;

/**
 * 创建或导入钱包监听器
 * Created by kimi on 2018/1/24.
 */

public interface WalletCreateListener {

    /**
     * 开始创建钱包
     * @param walletName
     * @param seed
     */
    void createWallet(String walletName,String seed);

    /**
     * 开始导入钱包
     * @param walletName
     * @param seed
     */
    void importWallet(String walletName,String seed);

}
