package com.cdh.wandroid.ui.adapter

import android.content.Context
import com.cdh.wandroid.R
import com.cdh.wandroid.model.bean.SearchKeyBean
import com.cdh.wandroid.ui.adapter.base.BaseRecyclerAdapter
import com.cdh.wandroid.ui.adapter.base.ViewHolder

/**
 * Created by chidehang on 2020-01-09
 */
class HistoryKeysAdapter(context: Context, data: MutableList<SearchKeyBean>)
    : BaseRecyclerAdapter<SearchKeyBean>(context, data) {

    init {
        putItemLayoutId(iViewBinder = TextViewBinder())
    }

    inner class TextViewBinder : IViewBinder<SearchKeyBean> {

        override fun getLayoutId(): Int {
            return R.layout.item_history_key
        }

        override fun onBind(holder: ViewHolder, item: SearchKeyBean, position: Int) {
            holder.setText(R.id.tv_history_key, item.name)
        }
    }
}