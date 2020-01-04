package com.cdh.wandroid.ui.adapter.base

import android.content.Context
import android.graphics.Bitmap
import android.text.Spanned
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cdh.wandroid.util.ImageLoader

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val mContext: Context
    private val mConvertView: View
    private val mViews: SparseArray<View?>
    /**
     * 多布局类型时，区分不同类型，默认为0
     */
    var viewType = 0

    /**
     * 通过控件ID获取对应控件
     */
    fun <T : View?> getView(viewId: Int): T? {
        var view = mViews[viewId]
        if (view == null) {
            view = mConvertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T?
    }

    fun setText(viewId: Int, text: String?): ViewHolder {
        val tv = getView<TextView>(viewId)!!
        tv.text = text
        return this
    }

    fun setText(viewId: Int, spanned: Spanned?): ViewHolder {
        val tv = getView<TextView>(viewId)!!
        tv.text = spanned
        return this
    }

    fun setImageResource(
        viewId: Int,
        drawableId: Int
    ): ViewHolder {
        val iv = getView<ImageView>(viewId)!!
        iv.setImageResource(drawableId)
        return this
    }

    fun setImageBitmap(viewId: Int, bm: Bitmap?): ViewHolder {
        val iv = getView<ImageView>(viewId)!!
        iv.setImageBitmap(bm)
        return this
    }

    fun setImageUrl(viewId: Int, url: String): ViewHolder {
        val iv = getView<ImageView>(viewId)!!
        ImageLoader.display(iv, url)
        return this
    }

    init {
        mContext = view.context
        mConvertView = view
        mViews = SparseArray()
    }
}