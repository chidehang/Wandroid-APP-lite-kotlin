package com.cdh.wandroid.ui.adapter

import android.content.Context
import android.text.Html
import android.text.Spannable
import android.text.Spanned
import android.text.TextUtils
import com.cdh.wandroid.R
import com.cdh.wandroid.model.bean.ArticleBean
import com.cdh.wandroid.ui.adapter.base.BaseRecyclerAdapter
import com.cdh.wandroid.ui.adapter.base.ViewHolder
import com.cdh.wandroid.ui.widget.webview.WebLauncher

/**
 * Created by chidehang on 2020-01-06
 */
class CategoryArticlesAdapter(context: Context, data: MutableList<ArticleBean>?) :
    BaseRecyclerAdapter<ArticleBean>(context, data) {

    init {
        putItemLayoutId(iViewBinder = ArticleViewBinder())
    }

    inner class ArticleViewBinder : IViewBinder<ArticleBean> {

        override fun getLayoutId(): Int {
            return R.layout.item_article_summary
        }

        override fun onBind(holder: ViewHolder, item: ArticleBean, position: Int) {
            holder.setText(R.id.tv_article_item_title, Html.fromHtml(item.title))
            holder.setText(R.id.tv_article_item_classify, item.superChapterName + "/" + item.chapterName)
            holder.setText(R.id.tv_article_item_date, item.niceShareDate)
            if (TextUtils.isEmpty(item.author)) {
                holder.setText(R.id.tv_article_item_author_label, R.string.article_share_label)
                holder.setText(R.id.tv_article_item_author, item.shareUser)
            } else {
                holder.setText(R.id.tv_article_item_author_label, R.string.article_author_label)
                holder.setText(R.id.tv_article_item_author, item.author)
            }
        }
    }
}