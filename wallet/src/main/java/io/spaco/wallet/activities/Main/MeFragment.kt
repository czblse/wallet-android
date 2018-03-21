package io.spaco.wallet.activities.Main

import android.os.Bundle
import android.view.View
import io.spaco.wallet.R
import io.spaco.wallet.base.BaseFragment

/**
 * @author: lh on 2018/3/21 11:14.
 * Email:luchefg@gmail.com
 * Description: 主页 -我
 */

class MeFragment : BaseFragment(), MeListentr {


    override fun attachLayoutRes(): Int {
        return R.layout.fragment_me
    }

    override fun initViews(rootView: View?) {
    }

    override fun initData() {
    }

    override fun managerWallet() {
    }

    override fun transactionRecord() {
    }

    override fun alerts() {
    }

    override fun systemSettings() {
    }

    override fun helpCenter() {
    }

    override fun aboutUs() {
    }

    companion object {

        fun newInstance(args: Bundle?): MeFragment {
            val instance = MeFragment()
            instance.arguments = args
            return instance
        }
    }

}