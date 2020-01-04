package com.cdh.wandroid.util

import android.util.Log
import com.cdh.wandroid.BuildConfig

object LogUtils {
    private const val GLOBAL_TAG = "wan"
    private const val MAKE_VERSION = BuildConfig.VERSION_NAME
    private const val INDEX = 6

    var isEnabled = BuildConfig.DEBUG

    fun setEnableLog(enable: Boolean) {
        isEnabled = enable
    }

    @JvmOverloads
    fun v(tag: String, msg: String?, deep: Int = INDEX) {
        if (isEnabled) {
            Log.v(GLOBAL_TAG, formatMsg(tag, msg, deep))
        }
    }

    /**
     * 由于华为手机打印不出debug类型的日志，所以所有的Log.d的全部改为Log.i
     */
    @JvmOverloads
    fun d(tag: String, msg: String?, deep: Int = INDEX) {
        if (isEnabled) {
            Log.d(GLOBAL_TAG, formatMsg(tag, msg, deep))
        }
    }

    @JvmOverloads
    fun i(tag: String, msg: String?, deep: Int = INDEX) {
        if (isEnabled) {
            Log.i(GLOBAL_TAG, formatMsg(tag, msg, deep))
        }
    }

    @JvmOverloads
    fun e(tag: String, msg: String?, deep: Int = INDEX) {
        if (isEnabled) {
            Log.e(GLOBAL_TAG, formatMsg(tag, msg, deep))
        }
    }

    @JvmOverloads
    fun w(tag: String, msg: String?, deep: Int = INDEX) {
        if (isEnabled) {
            Log.w(GLOBAL_TAG, formatMsg(tag, msg, deep))
        }
    }

    @JvmOverloads
    fun e(
        subTag: String,
        pMessage: String?,
        e: Throwable?,
        deep: Int = INDEX
    ) {
        var pMessage = pMessage
        if (isEnabled) {
            if (pMessage == null) {
                pMessage = "noMsg"
            }
            if (e == null) {
                Log.e(GLOBAL_TAG, formatMsg(subTag, pMessage, deep))
            } else {
                Log.e(
                    GLOBAL_TAG,
                    formatMsg(subTag, pMessage, deep),
                    e
                )
            }
        }
    }

    private fun formatMsg(tag: String, msg: String?, deep: Int): String {
        return String.format(
            "%s[%s][%s]%s%s",
            MAKE_VERSION,
            Thread.currentThread().name,
            tag,
            msg,
            getTrace(deep)
        )
    }

    private fun getTrace(index: Int): String {
        var index = index
        val stacks =
            Thread.currentThread().stackTrace
        if (index <= 0) {
            index = INDEX
        }
        return if (stacks.size <= index) {
            ""
        } else {
            String.format(
                "(%s:%d)",
                stacks[index].fileName,
                stacks[index].lineNumber
            )
        }
    }

    fun printStackTrace(t: Throwable?) {
        if (isEnabled && t != null) {
            t.printStackTrace()
            e(GLOBAL_TAG, t.message, INDEX)
        }
    }

    fun d(tag: String, msg: String?, time: Long) {
        if (isEnabled) {
            Log.i(
                GLOBAL_TAG,
                formatMsg(
                    tag,
                    String.format(
                        "%s [t1=%d][t2=%d]",
                        msg,
                        time,
                        System.currentTimeMillis() - time
                    ),
                    INDEX
                )
            )
        }
    }
}