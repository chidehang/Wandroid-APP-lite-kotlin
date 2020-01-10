package com.cdh.wandroid.ui.adapter

import android.app.Activity
import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.cdh.wandroid.R
import com.cdh.wandroid.model.bean.ArticleBean
import com.cdh.wandroid.ui.adapter.base.BaseRecyclerAdapter
import com.cdh.wandroid.ui.adapter.base.ViewHolder
import com.cdh.wandroid.ui.widget.webview.From
import com.cdh.wandroid.ui.widget.webview.WebLauncher
import com.cdh.wandroid.ui.widget.webview.WebParams

/**
 * Created by chidehang on 2020-01-09
 */
class FavoriteArticlesAdapter(context: Context, data: MutableList<ArticleBean>?) :
    BaseRecyclerAdapter<ArticleBean>(context, data) {

    private var onFavoriteListItemClick: OnFavoriteListItemClick? = null

    init {
        putItemLayoutId(iViewBinder = ArticleViewBinder())
    }

    inner class ArticleViewBinder : IViewBinder<ArticleBean> {

        override fun getLayoutId(): Int {
            return R.layout.item_favorite_article
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

            holder.getView<ConstraintLayout>(R.id.slide_foreground_content)?.setOnClickListener {
                onFavoriteListItemClick?.onForegroundClicked(holder, item, position)
            }

            holder.getView<TextView>(R.id.slide_background_content)?.setOnClickListener {
                onFavoriteListItemClick?.onBackgroundClicked(holder, item, position)
            }
        }
    }

    fun setOnFavoriteListItemClick(listener: OnFavoriteListItemClick) {
        this.onFavoriteListItemClick = listener
    }
}

interface OnFavoriteListItemClick {
    fun onForegroundClicked(holder: ViewHolder, item: ArticleBean, position: Int)
    fun onBackgroundClicked(holder: ViewHolder, item: ArticleBean, position: Int)
}