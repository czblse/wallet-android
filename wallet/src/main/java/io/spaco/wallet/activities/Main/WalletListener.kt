package io.spaco.wallet.activities.Main

import io.spaco.wallet.datas.Wallet

/**
 * Created by kimi on 2018/1/29.
 */

interface WalletListener {

    /**
     * 点击事件
     * @param position
     * @param bean
     */
    fun onItemClick(position: Int, bean: Wallet)

    /**
     * 创建钱包
     */
    fun onCreateWallet()

    /**
     * 导入钱包
     */
    fun onImportWallet()
}
