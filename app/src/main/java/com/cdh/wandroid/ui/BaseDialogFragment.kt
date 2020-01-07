package com.cdh.wandroid.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.cdh.wandroid.R

abstract class BaseDialogFragment : DialogFragment() {

    private var rootView: View ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initWindowAttr()

        if (rootView != null) {
            val parent = rootView!!.parent as ViewGroup
            parent.removeView(rootView)
            return rootView
        }

        rootView = initView(inflater, container, savedInstanceState)
        return rootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }

    /**
     * 设置弹窗属性
     */
    private fun initWindowAttr() {
        if (dialog != null) {
            dialog!!.setCanceledOnTouchOutside(true)
            val window = dialog!!.window
            if (window != null) {
                window.setBackgroundDrawableResource(android.R.color.transparent)
                window.decorView.setPadding(0, 0, 0, 0)
                val lp = window.attributes
                initWindowLayoutParams(lp)
                window.attributes = lp
            }
        }
    }

    override fun show(
        manager: FragmentManager,
        tag: String?
    ) {
        if (isRepeatShow) {
            return
        }
        isRepeatShow = true
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }

    override fun dismiss() {
        dismissAllowingStateLoss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        isRepeatShow = false
    }

    protected abstract fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View

    protected abstract fun initWindowLayoutParams(layoutParams: WindowManager.LayoutParams?)

    companion object {
        @Volatile
        var isRepeatShow = false
    }
}