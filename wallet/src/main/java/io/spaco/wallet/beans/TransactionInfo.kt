package io.spaco.wallet.beans

import java.io.Serializable

/**
 * Created by kimi on 2018/1/29.
 */

class TransactionInfo : Serializable {

    var status: Status? = null
    var time: String? = null
    var txn: Txn? = null
    var toWallet: String? = null
    var amount: String? = null
    var coinType: String? = null

    class Status : Serializable {
        var confirmed: Boolean = false
        var unconfirmed: Boolean = false
        var height: String? = null
        var block_seq: String? = null
        var unknown: Boolean = false

        /**
         * 获取交易状态
         * @return
         */
        val statusValues: String
            get() = if (confirmed) {
                "已完成"
            } else if (unconfirmed) {
                "未校验"
            } else if (unknown) {
                "未知的"
            } else {
                ""
            }
    }

    class Txn : Serializable {
        var length: String? = null
        var type: String? = null
        var txid: String? = null
        var inner_hash: String? = null
        var timestamp: String? = null
        var sigs: List<String>? = null
        var inputs: List<String>? = null
        var outputs: List<Outputs>? = null

        class Outputs : Serializable {
            var uxid: String? = null
            var dst: String? = null
            var coins: String? = null
            var hours: String? = null
        }
    }

}
