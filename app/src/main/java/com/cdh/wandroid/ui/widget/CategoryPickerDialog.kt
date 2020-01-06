package com.cdh.wandroid.ui.widget

import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import com.cdh.wandroid.R
import com.cdh.wandroid.model.bean.CategoryBean
import com.cdh.wandroid.ui.BaseDialogFragment
import com.cdh.wandroid.util.DeviceUtils

/**
 * Created by chidehang on 2020-01-06
 */
class CategoryPickerDialog : BaseDialogFragment() {

    private lateinit var data: MutableList<CategoryBean>
    private var radioGroup: MutableList<CheckBox> ?= null

    private var selectedIndex = -1

    private var onPickListener: OnPickListener ?= null

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_category_picker, null)
        val flowLayout = view.findViewById<FlowLayout>(R.id.flow_category_picker)
        radioGroup = mutableListOf()
        for ((index, bean) in data.withIndex()) {
            var checkBox = CheckBox(context)
            checkBox.setText(bean.name)
            checkBox.setButtonDrawable(null)
            checkBox.setTextColor(resources.getColorStateList(R.color.main_tab_label_selector))
            checkBox.background = resources.getDrawable(R.drawable.border_checkbox_selector)
            val padding = DeviceUtils.dip2px(context, 5f)
            checkBox.setPadding(padding, 0, padding, 0)
            checkBox.setOnClickListener {
                updateCheckedCategory(index)
                onPickListener?.onPicked(index)
                dismiss()
            }

            flowLayout.addView(checkBox)
            radioGroup?.add(checkBox)
        }
        return view
    }

    override fun initWindowLayoutParams(layoutParams: WindowManager.LayoutParams?) {
        layoutParams?.let {
            it.width = DeviceUtils.getScreenWidth(activity) - DeviceUtils.dip2px(activity, 20f)
            it.height = WindowManager.LayoutParams.WRAP_CONTENT
            it.gravity = Gravity.CENTER
        }
    }

    fun updateCheckedCategory(index: Int) {
        if (selectedIndex != -1 && selectedIndex != index) {
            radioGroup?.get(selectedIndex)?.isChecked = false
        }
        radioGroup?.get(index)?.isChecked = true
        selectedIndex = index
    }

    companion object {
        const val TAG = "CategoryPickerDialog"

        fun createInstance(allCategory: MutableList<CategoryBean>): CategoryPickerDialog {
            var instance = CategoryPickerDialog()
            instance.data = allCategory
            return instance
        }
    }

    fun setOnPickListener(listener: OnPickListener) {
        this.onPickListener = listener
    }

    interface OnPickListener {
        fun onPicked(position: Int)
    }
}