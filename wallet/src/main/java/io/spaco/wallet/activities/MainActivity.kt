package io.spaco.wallet.activities

import android.support.v4.app.Fragment
import android.view.View
import io.spaco.wallet.R
import io.spaco.wallet.activities.Main.MeFragment
import io.spaco.wallet.activities.Main.WalletFragment
import io.spaco.wallet.base.BaseActivity

/**
 * 主界面，一级界面
 */
class MainActivity : BaseActivity() {

    internal lateinit var mainWalletFragment: WalletFragment
    internal lateinit var meFragment: MeFragment
    private var lin_wallet: View? = null
    private var lin_me: View? = null
    override fun attachLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {

        mainWalletFragment = WalletFragment.newInstance(null)
        meFragment = MeFragment.newInstance(null)
        lin_wallet = findViewById(R.id.lin_wallet)
        lin_me = findViewById(R.id.lin_me)
        lin_wallet!!.setOnClickListener { switchFragment(mainWalletFragment) }
        lin_me!!.setOnClickListener { switchFragment(meFragment) }
        switchFragment(mainWalletFragment)
    }

    /**
     * 切换碎片视图
     * @param fragment
     */
    private fun switchFragment(fragment: Fragment) {
        val tag = fragment.javaClass.simpleName
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val instance = supportFragmentManager.findFragmentByTag(tag)
        if (instance == null) {
            fragmentTransaction.add(R.id.main_container, fragment, tag)
        }
        val fragments = supportFragmentManager.fragments
        if (fragments != null) {
            for (item in fragments) {
                if (item == fragment) {
                    fragmentTransaction.show(item)
                } else {
                    fragmentTransaction.hide(item)
                }
            }
        }
        fragmentTransaction.commit()
    }

    override fun initData() {

    }

}
