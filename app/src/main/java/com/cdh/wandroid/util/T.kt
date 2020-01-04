package com.cdh.wandroid.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.widget.Toast

/**
 * Toast统一管理类
 */
object T {
    @Volatile
    private var toast: Toast? = null
    @Volatile
    private var handler: Handler? = null

    /**
     * 必须先在UI线程中调用
     */
    fun init(context: Context?) {
        if (handler == null || toast == null) {
            handler = object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message) {
                    if (toast == null) {
                        toast =
                            Toast.makeText(context, "", Toast.LENGTH_LONG)
                    }
                    toast!!.duration = msg.what
                    toast!!.setText(msg.obj.toString())
                    toast!!.show()
                }
            }
            if (Looper.myLooper() == Looper.getMainLooper()) {
                toast = Toast.makeText(context, "", Toast.LENGTH_LONG)
            } else {
                handler?.postAtFrontOfQueue(Runnable {
                    toast =
                        Toast.makeText(context, "", Toast.LENGTH_LONG)
                })
            }
        }
    }

    /**
     * 显示Toast
     *
     * @param shortTime
     */
    fun showOnUIThread(message: CharSequence?, shortTime: Boolean) {
        if (TextUtils.isEmpty(message) || handler == null) {
            return
        }
        val msg = Message.obtain()
        msg.obj = message
        msg.what = if (shortTime) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
        handler!!.sendMessage(msg)
    }

    /**
     * 短时间显示Toast
     */
    fun showShort(message: CharSequence?) {
        show(message, Toast.LENGTH_SHORT)
    }

    /**
     * 短时间显示Toast
     */
    fun showLong(message: CharSequence?) {
        show(message, Toast.LENGTH_LONG)
    }

    /**
     * 短时间显示Toast
     */
    fun show(message: CharSequence?, duration: Int) {
        if (TextUtils.isEmpty(message) || handler == null) {
            return
        }
        if (Looper.myLooper() == Looper.getMainLooper() && toast != null) {
            toast!!.setText(message)
            toast!!.duration = duration
            toast!!.show()
        } else {
            showOnUIThread(message, true)
        }
    }
}