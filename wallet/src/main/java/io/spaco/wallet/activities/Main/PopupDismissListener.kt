package io.spaco.wallet.activities.Main

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import io.spaco.wallet.R

/**
 * @author: lh on 2018/4/8 16:52.
 * Email:luchefg@gmail.com
 * Description: popwindow右侧菜单
 */
class PopupDismissListener : PopupWindow.OnDismissListener {

    private var context: Activity? = null
    private var popupWindow: PopupWindow? = null
    private val from = 0


    protected fun initPopupWindow(context: Activity) {
        this.context = context
        val popupWindowView = context.layoutInflater.inflate(R.layout.pop_main_right, null)
        //内容，高度，宽度
        popupWindow = PopupWindow(popupWindowView, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.FILL_PARENT, true)
        //动画效果
        popupWindow!!.animationStyle = R.style.AnimationLeftFade

        //菜单背景色
        val dw = ColorDrawable(-0x1)
        popupWindow!!.setBackgroundDrawable(dw)
        //显示位置
        popupWindow!!.showAtLocation(context.layoutInflater.inflate(R.layout
                .activity_main, null),
                Gravity.LEFT, 0, 500)
        //设置背景半透明
        backgroundAlpha(0.5f)
        //关闭事件
        popupWindow!!.setOnDismissListener(this)

        popupWindowView.setOnTouchListener { v, event -> false }

        val ls = popupWindowView.findViewById<View>(R.id.btn_ls) as Button


        ls.setOnClickListener {
            Toast.makeText(context, "Open", Toast.LENGTH_LONG).show()
            popupWindow!!.dismiss()
        }


    }

    /**
     * 设置添加屏幕的背景透明度
     */
    fun backgroundAlpha(bgAlpha: Float) {
        try {
            val lp = context!!.window.attributes
            lp.alpha = bgAlpha //0.0-1.0
            context!!.window.attributes = lp
        } catch (e: Exception) {
        }

    }

    override fun onDismiss() {
        backgroundAlpha(1f)
    }

    /**
     * 菜单弹出方向
     */
    enum class Location {

        LEFT,
        RIGHT,
        TOP,
        BOTTOM

    }
}
