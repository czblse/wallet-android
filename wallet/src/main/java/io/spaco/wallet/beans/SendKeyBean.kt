package io.spaco.wallet.beans

/**
 * @author: lh on 2018/1/25 15:15.
 * Email:luchefg@gmail.com
 * Description: SendKey弹出框信息展示
 */

class SendKeyBean {

    var skyNum: String? = null
    var skyDollar: String? = null
    var status: String? = null
    var form: String? = null
    var date: String? = null
    var notes: String? = null
    var to: String? = null
    var time: String? = null

    override fun toString(): String {
        return "SendKeyBean{" +
                "skyNum='" + skyNum + '\'' +
                ", skyDollar='" + skyDollar + '\'' +
                ", status='" + status + '\'' +
                ", form='" + form + '\'' +
                ", date='" + date + '\'' +
                ", notes='" + notes + '\'' +
                ", to='" + to + '\'' +
                ", time='" + time + '\'' +
                '}'
    }
}
