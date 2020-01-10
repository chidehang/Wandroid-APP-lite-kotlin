package com.cdh.wandroid.ui

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.cdh.wandroid.util.ProgressDialogUtil

/**
 * Created by chidehang on 2020-01-05
 */
open class BaseActivity: AppCompatActivity() {

    private var mProgressDialog: ProgressDialogUtil ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStatusBar()
        mProgressDialog = ProgressDialogUtil()
    }

    protected fun initStatusBar() {
        setSpecialColorStatusBar()
    }

    protected fun setTranslucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    protected fun setSpecialColorStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.statusBarColor = Color.parseColor("#ffffffff")
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mProgressDialog?.dismissProgressDlg()
    }


    protected fun showProgress(msg: String) {
        mProgressDialog?.showProgressDlg(this, msg)
    }

    protected fun dismissProgress() {
        mProgressDialog?.dismissProgressDlg()
    }
}