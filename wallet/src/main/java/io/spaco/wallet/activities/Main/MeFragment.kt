package io.spaco.wallet.activities.Main

import android.os.Bundle
import android.view.View
import io.spaco.wallet.R
import io.spaco.wallet.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_me.*

/**
 * @author: lh on 2018/3/21 11:14.
 * Email:luchefg@gmail.com
 * Description: 主页 -我
 */

class MeFragment : BaseFragment(), View.OnClickListener, MeListentr {


    override fun attachLayoutRes(): Int {
        return R.layout.fragment_me
    }

    override fun onClick(p0: View?) {
        when (id) {
            R.id.tv_managerwallet -> {
                managerWallet()
            }
            R.id.lin_jyjl -> {
                transactionRecord()
            }
            R.id.lin_xxtz -> {
                alerts()
            }
            R.id.lin_xtsz -> {
                systemSettings()
            }
            R.id.lin_bzzx -> {
                helpCenter()
            }
            R.id.lin_gywm -> {
                aboutUs()
            }
        }
    }


    override fun initViews(rootView: View?) {
        lin_jyjl.setOnClickListener(this)
        lin_gywm.setOnClickListener(this)
        lin_xxtz.setOnClickListener(this)
        lin_xtsz.setOnClickListener(this)
        lin_bzzx.setOnClickListener(this)
        tv_managerwallet.setOnClickListener(this)
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