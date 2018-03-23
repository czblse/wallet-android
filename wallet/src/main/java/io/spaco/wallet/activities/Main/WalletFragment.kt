package io.spaco.wallet.activities.Main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.tencent.bugly.beta.Beta
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.spaco.wallet.R
import io.spaco.wallet.activities.SendCostActivity
import io.spaco.wallet.activities.WalletCreatActivity
import io.spaco.wallet.activities.WalletDetailsActivity
import io.spaco.wallet.base.BaseFragment
import io.spaco.wallet.common.Constant
import io.spaco.wallet.datas.Wallet
import io.spaco.wallet.push.WalletPush
import io.spaco.wallet.push.WalletPushListener
import io.spaco.wallet.utils.SharePrefrencesUtil
import java.text.DecimalFormat
import java.util.*

/**
 * 钱包主页视图碎片
 * Created by kimi on 2018/1/25.
 */

class WalletFragment : BaseFragment(), WalletListener {

    internal lateinit var recyclerView: RecyclerView
    internal lateinit var mainWalletAdapter: WalletAdapter
    internal lateinit var tvBalance: TextView
    internal lateinit var tvHours: TextView
    internal lateinit var tvExchangeCoin: TextView
    internal var mainWalletBeans: List<Wallet> = ArrayList()
    internal lateinit var refresh: SwipeRefreshLayout

    /**
     * 标记，true表示cny，false表示usd
     */
    internal var exchangeCoinZH = SharePrefrencesUtil.getInstance().getBoolean(Constant.IS_LANGUAGE_ZH)
    internal var decimalFormat = DecimalFormat(".00")

    /**
     * 钱包控制层
     */
    internal var walletViewModel = WalletViewModel()

    /**
     * 定时任务专用handler
     */
    internal var taskHandler = Handler()

    private val listener = object : WalletPushListener() {
        override fun walletUpdate() {
            taskHandler.removeCallbacksAndMessages(null)
            taskHandler.postDelayed({ refreshDatas() }, 8000)
        }

        override fun transactionUpdate() {

        }

        override fun balanceUpdate() {

        }
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_main_wallet
    }

    override fun initViews(rootView: View) {
        val toolbar = rootView.findViewById<Toolbar>(R.id.id_toolbar)
        refresh = rootView.findViewById(R.id.refresh)
        toolbar.inflateMenu(R.menu.wallet_fragment)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.refresh -> refresh.post {
                    refresh.isRefreshing = true
                    refreshDatas()
                }
                R.id.send -> startSendCost()
                R.id.version -> chcekUp()
            }
            true
        }
        rootView.findViewById<View>(R.id.coin_view).setOnClickListener {
            //切换对应的钱包汇率
            exchangeCoinZH = !exchangeCoinZH
            setExchangeCoinValue()
        }
        tvExchangeCoin = rootView.findViewById(R.id.exchange_coin)
        recyclerView = rootView.findViewById(R.id.recyclerview)
        tvBalance = rootView.findViewById(R.id.tv_balance)
        tvHours = rootView.findViewById(R.id.tv_hours)
        recyclerView.layoutManager = LinearLayoutManager(rootView.context)
        mainWalletAdapter = WalletAdapter(mainWalletBeans)
        mainWalletAdapter.setMainWalletListener(this)
        recyclerView.adapter = mainWalletAdapter
        //添加观察者
        WalletPush.getInstance().addObserver(listener)
        refresh.setColorSchemeColors(Color.parseColor("#0075FF"), Color.parseColor("#00B9FF"))

        refresh.setOnRefreshListener { refreshDatas() }
    }

    override fun initData() {}

    override fun onDestroyView() {
        super.onDestroyView()
        //移除观察者
        WalletPush.getInstance().deleteObserver(listener)
        taskHandler.removeCallbacksAndMessages(null)
    }

    /**
     * 刷新数据
     */
    private fun refreshDatas() {
        walletViewModel.allWallets
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(object : Observer<List<Wallet>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(wallets: List<Wallet>) {
                        WalletViewModel.wallets = wallets
                        tvBalance.text = WalletViewModel.totalBalance.toString()
                        tvHours.text = (WalletViewModel.totalHours.toString() + " SKY Hours").toString()
                        mainWalletAdapter.setWallets(wallets)
                        mainWalletAdapter.notifyDataSetChanged()
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        refresh.isRefreshing = false
                        setExchangeCoinValue()
                    }
                })
    }

    /**
     * 设置汇率
     */
    private fun setExchangeCoinValue() {
        if (exchangeCoinZH) {
            val value = WalletViewModel.totalBalance * java.lang.Double.parseDouble(SharePrefrencesUtil.getInstance().getString(Constant.CNYEXCHAGECOIN))
            tvExchangeCoin.text = "￥" + decimalFormat.format(value)
        } else {
            val value = WalletViewModel.totalBalance * java.lang.Double.parseDouble(SharePrefrencesUtil.getInstance().getString(Constant.USDEXCHAGECOIN))
            tvExchangeCoin.text = "$" + decimalFormat.format(value)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh.isRefreshing = true
        refreshDatas()
    }

    /**
     * 检测更新
     */
    private fun chcekUp() {
        Beta.checkUpgrade()
        //loadUpgradeInfo();
    }


    override fun onItemClick(position: Int, bean: Wallet) {
        val intent = Intent(activity, WalletDetailsActivity::class.java)
        intent.putExtra(Constant.KEY_WALLET, bean)
        startActivity(intent)
    }

    override fun onCreateWallet() {
        val intent = Intent(activity, WalletCreatActivity::class.java)
        startActivityForResult(intent, 1)

    }

    override fun onImportWallet() {
        val intent = Intent(activity, WalletCreatActivity::class.java)
        intent.putExtra(Constant.KEY_PAGE, 1)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        initData()
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 开始发送交易
     */
    private fun startSendCost() {
        try {
            val intent = Intent(activity, SendCostActivity::class.java)
            intent.putExtra(Constant.KEY_WALLET, WalletViewModel.wallets.get(0))
            startActivity(intent)
            activity!!.overridePendingTransition(R.anim.send_cost_in, 0)
        } catch (e: Exception) {

        }

    }

    companion object {

        fun newInstance(args: Bundle?): WalletFragment {
            val instance = WalletFragment()
            instance.arguments = args
            return instance
        }
    }
}
