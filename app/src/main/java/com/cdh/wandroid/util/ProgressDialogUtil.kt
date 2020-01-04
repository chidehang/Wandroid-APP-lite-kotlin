package com.cdh.wandroid.util

import android.app.ProgressDialog
import android.content.Context
import android.content.ContextWrapper

class ProgressDialogUtil {
    private var progressDlg: ProgressDialog? = null
    fun showProgressDlg(context: Context?, message: String?) {
        try {
            progressDlg = ProgressDialog(context)
            progressDlg!!.setMessage(message)
            progressDlg!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setCancelable(flag: Boolean) {
        try {
            if (progressDlg != null) {
                progressDlg!!.setCancelable(flag)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismissProgressDlg() {
        try {
            if (progressDlg != null && progressDlg!!.isShowing) {
                val context =
                    (progressDlg!!.context as ContextWrapper).baseContext
                if (ContextUtils.isContextValid(context)) {
                    progressDlg!!.dismiss()
                }
            }
            progressDlg = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}