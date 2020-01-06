package com.cdh.wandroid.ui.adapter.base

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class BaseRecyclerAdapter<T>(
    protected var mContext: Context,
    protected var mData: MutableList<T>?
) : RecyclerView.Adapter<ViewHolder>() {

    protected var mInflater: LayoutInflater
    protected var mViewBinders: SparseArray<IViewBinder<T>>
    protected var onItemClickListener: OnRecyclerItemClickListener<T>? = null

    /**
     * 添加item布局id及对应布局处理器
     */
    protected fun putItemLayoutId(
        viewType: Int = VIEW_TYPE_DEFAULT,
        iViewBinder: IViewBinder<T>
    ) {
        mViewBinders.put(viewType, iViewBinder)
    }

    override fun getItemCount(): Int {
        return if (mData == null) 0 else mData!!.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val binder = mViewBinders[getItemViewType(position)]
        binder?.onBind(holder, mData!![position], position)
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener {
                onItemClickListener!!.onRecyclerItemClicked(
                    this@BaseRecyclerAdapter,
                    holder.itemView,
                    position
                )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = mInflater.inflate(mViewBinders[viewType].getLayoutId(), parent, false)
        val holder =
            ViewHolder(view)
        holder.viewType = viewType
        return holder
    }

    /**
     * 获取item视图类型
     * 子类可以重写该方法返回多视图类型
     */
    override fun getItemViewType(position: Int): Int {
        return if (mData == null) {
            VIEW_TYPE_DEFAULT
        } else {
            getItemViewType(position, mData!![position])
        }
    }

    /**
     * 获取item视图类型
     * 子类可以重写该方法返回多视图类型
     */
    open fun getItemViewType(position: Int, item: T): Int {
        return VIEW_TYPE_DEFAULT
    }

    val data: List<T>?
        get() = mData

    fun setData(data: MutableList<T>?) {
        mData = data
    }

    /**
     * 追加数据
     */
    fun appendData(data: MutableList<T>?) {
        if (data == null || data.size <= 0) {
            return
        }
        if (mData == null) {
            mData = data
        } else {
            mData!!.addAll(data)
        }
        notifyDataSetChanged()
    }

    val count: Int
        get() = if (mData == null) 0 else mData!!.size

    fun remove(item: T) {
        mData?.let {
            val position = it.indexOf(item)
            it.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun insert(position: Int, item: T) {
        mData?.let {
            it.add(position, item)
            notifyItemInserted(position)
        }
    }

    fun setOnRecyclerItemClickListener(listener: OnRecyclerItemClickListener<T>?) {
        onItemClickListener = listener
    }

    interface OnRecyclerItemClickListener<T> {
        fun onRecyclerItemClicked(
            adapter: BaseRecyclerAdapter<T>,
            view: View,
            position: Int
        )
    }

    interface IViewBinder<T> {
        fun getLayoutId(): Int

        fun onBind(
            holder: ViewHolder,
            item: T,
            position: Int
        )
    }

    fun clearData() {
        mData?.clear()
    }

    fun release() {
        setOnRecyclerItemClickListener(null)
        if (mData != null) {
            mData!!.clear()
            mData = null
        }
        mViewBinders.clear()
    }

    companion object {
        const val VIEW_TYPE_DEFAULT = 0
    }

    init {
        mInflater = LayoutInflater.from(mContext)
        mViewBinders = SparseArray()
    }
}