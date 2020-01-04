package com.cdh.wandroid.util

import android.app.Activity
import android.content.Context
import android.os.Build

object ContextUtils {
    fun isContextValid(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        if (context is Activity) {
            val activity = context
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                !activity.isFinishing && !activity.isDestroyed
            } else {
                !activity.isFinishing
            }
        }
        return true
    }
}