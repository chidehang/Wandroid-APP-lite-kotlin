package com.cdh.wandroid.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.view.View
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.cdh.wandroid.R
import com.cdh.wandroid.model.bean.ArticleBean
import com.cdh.wandroid.model.bean.BannerBean
import com.cdh.wandroid.ui.adapter.base.BaseRecyclerAdapter
import com.cdh.wandroid.ui.adapter.base.ViewHolder
import com.cdh.wandroid.ui.widget.LifecycleBanner

/**
 * Created by chidehang on 2020-01-04
 */
class HomeListAdapter(mContext: Context, mData: MutableList<ArticleBean>?) :
    BaseRecyclerAdapter<ArticleBean>(mContext, mData) {

    private val HEADER_VIEW_TYPE = 1

    private var onClickListener: OnHomeModuleClickListener ?= null

    init {
        putItemLayoutId(iViewBinder = ArticleViewBinder())
        putItemLayoutId(HEADER_VIEW_TYPE, HeaderViewBinder())
    }

    override fun getItemViewType(position: Int, item: ArticleBean): Int {
        return item.viewType
    }

    inner class HeaderViewBinder : IViewBinder<ArticleBean> {

        override fun getLayoutId(): Int {
            return R.layout.layout_home_header
        }

        override fun onBind(holder: ViewHolder, item: ArticleBean, position: Int) {
            var bannerView = holder.getView<LifecycleBanner<BannerBean>>(R.id.cb_home_banner)
            setupBannerView(bannerView!!, item.headerBanners!!)

            holder.getView<View>(R.id.tv_home_search_entry)?.setOnClickListener { _ ->
                onClickListener?.let {
                    it.onSearchClick()
                }
            }
        }

        private fun setupBannerView(view: LifecycleBanner<BannerBean>, banners: MutableList<BannerBean>) {
            view.setPages(CBViewHolderCreator {
                BannerImageHolderView()
            }, banners)
                .setPageIndicator(intArrayOf(R.mipmap.indicator_dot_normal, R.mipmap.indicator_dot_checked))
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener { position ->
                    onClickListener?.let {
                        it.onBannerClick(banners[position], position)
                    }
                }
        }
    }

    inner class ArticleViewBinder : IViewBinder<ArticleBean> {

        override fun getLayoutId(): Int {
            return R.layout.item_article_summary
        }

        override fun onBind(holder: ViewHolder, item: ArticleBean, position: Int) {
            holder.setText(R.id.tv_article_item_title, item.title)
            holder.setText(R.id.tv_article_item_classify, item.superChapterName + "/" + item.chapterName)
            holder.setText(R.id.tv_article_item_date, item.niceShareDate)
            if (TextUtils.isEmpty(item.author)) {
                holder.setText(R.id.tv_article_item_author_label, R.string.article_share_label)
                holder.setText(R.id.tv_article_item_author, item.shareUser)
            } else {
                holder.setText(R.id.tv_article_item_author_label, R.string.article_author_label)
                holder.setText(R.id.tv_article_item_author, item.author)
            }

            onClickListener?.let {
                holder.itemView.setOnClickListener { _ ->
                    it.onArticleClick(item, position-1)
                }
            }
        }
    }

    fun setOnHomeModuleClickListener(listener: OnHomeModuleClickListener) {
        this.onClickListener = listener
    }

    interface OnHomeModuleClickListener {
        fun onSearchClick()
        fun onBannerClick(item: BannerBean, indexOfBanners: Int)
        fun onArticleClick(item: ArticleBean, indexOfArticles: Int)
    }
}