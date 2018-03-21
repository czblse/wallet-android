package io.spaco.wallet.activities.Main

/**
 * @author: lh on 2018/3/21 11:15.
 * Email:luchefg@gmail.com
 * Description:
 */
interface MeListentr {

    /**
     * 管理钱包
     */
    fun managerWallet()

    /**
     * 交易记录
     */
    fun transactionRecord()

    /**
     *消息通知
     */
    fun alerts()

    /**
     *系统设置
     */
    fun systemSettings()

    /**
     *帮助中心
     */
    fun helpCenter()

    /**
     *关于我们
     */
    fun aboutUs()


}