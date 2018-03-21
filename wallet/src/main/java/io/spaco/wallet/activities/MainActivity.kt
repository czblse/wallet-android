package io.spaco.wallet.activities

import android.content.Intent
import android.graphics.Color
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.View
import android.widget.AdapterView
import io.spaco.wallet.R
import io.spaco.wallet.activities.Main.WalletFragment
import io.spaco.wallet.activities.Transaction.TransactionFragment
import io.spaco.wallet.base.BaseActivity
import io.spaco.wallet.common.Constant
import io.spaco.wallet.dialogs.OptionsDialog

/**
 * 主界面，一级界面
 */
class MainActivity : BaseActivity() {

    internal lateinit var mainWalletFragment: WalletFragment
    internal lateinit var mainTransactionFragment: TransactionFragment
    private var createWallet: View? = null
    private var importWallet: View? = null
    override fun attachLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        mainWalletFragment = WalletFragment.newInstance(null)
        mainTransactionFragment = TransactionFragment.newInstance(null)
        createWallet = findViewById(R.id.new_wallet)
        importWallet = findViewById(R.id.import_wallet)
        createWallet!!.setOnClickListener { onCreateWallet() }
        importWallet!!.setOnClickListener { onImportWallet() }
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
                if (item.equals(instance)) {
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

    fun onCreateWallet() {

        val intent = Intent(this, WalletCreatActivity::class.java)
        startActivity(intent)


    }

    fun onImportWallet() {
        val intent = Intent(this, WalletCreatActivity::class.java)
        intent.putExtra(Constant.KEY_PAGE, 1)
        startActivity(intent)
    }
}
