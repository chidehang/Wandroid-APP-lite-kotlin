package com.cdh.wandroid

import android.app.Application
import android.content.Context

/**
 * Created by chidehang on 2020-01-06
 */
class WandroidApp : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        WandroidApp.applicationContext = base
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        var applicationContext: Context ?= null
    }
}