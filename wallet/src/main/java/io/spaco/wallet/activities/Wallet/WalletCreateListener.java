package io.spaco.wallet.activities.Wallet;

/**
 * 创建或导入钱包监听器
 * Created by kimi on 2018/1/24.
 */

public interface WalletCreateListener {

    /**
     * 开始创建钱包
     * @param walletName
     * @param walletyType 币种
     * @param seed
     */
    boolean createWallet(String walletyType, String walletName,String seed);

    /**
     * 开始导入钱包
     * @param walletName
     * @param seed
     */
    boolean importWallet(String walletType, String walletName,String seed);

}
