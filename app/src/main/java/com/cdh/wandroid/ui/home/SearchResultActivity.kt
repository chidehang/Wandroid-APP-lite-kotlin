package com.cdh.wandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cdh.wandroid.ArgumentConstants
import com.cdh.wandroid.R
import com.cdh.wandroid.databinding.ActivitySearchResultBinding
import com.cdh.wandroid.model.bean.ArticleBean
import com.cdh.wandroid.ui.BaseActivity
import com.cdh.wandroid.ui.adapter.CategoryArticlesAdapter
import com.cdh.wandroid.ui.adapter.base.BaseRecyclerAdapter
import com.cdh.wandroid.ui.widget.refresh.OnLoadMoreListener
import com.cdh.wandroid.ui.widget.refresh.setRefreshListener
import com.cdh.wandroid.ui.widget.webview.From
import com.cdh.wandroid.ui.widget.webview.WebLauncher
import com.cdh.wandroid.ui.widget.webview.WebParams

class SearchResultActivity : BaseActivity() {

    private lateinit var mBinding: ActivitySearchResultBinding

    private val searchKey by lazy {
        intent.getStringExtra(ArgumentConstants.EXTRA_SEARCH_KEY)
    }

    private val mViewModel by lazy {
        ViewModelProviders.of(this, SearchResultViewModelFactory(searchKey))
            .get(SearchResultViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_result)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel

        initView()
        initData()
    }

    private fun initView() {
        mBinding.titleBarSearchResult.setTitle(searchKey)

        mBinding.rvSearchResult.layoutManager = LinearLayoutManager(this)
        mBinding.rvSearchResult.setRefreshListener(object: OnLoadMoreListener {
            override fun onLoadMore() {
                mViewModel.searchMore()
            }
        })

        mBinding.swipeRefreshSearch.setOnRefreshListener {
            mViewModel.search()
        }
    }

    private fun initData() {
        mViewModel.refreshable.observe(this, Observer { enable ->
            mBinding.swipeRefreshSearch.isRefreshing = enable
        })

        mViewModel.articles.observe(this, Observer { pair ->
            setupRecyclerView(pair.first, pair.second)
        })

        mViewModel.search()
    }

    private fun setupRecyclerView(isRefresh: Boolean, list: MutableList<ArticleBean>) {
        if (isRefresh) {
            var adapter = CategoryArticlesAdapter(this, list)
            adapter.setOnRecyclerItemClickListener(object: BaseRecyclerAdapter.OnRecyclerItemClickListener<ArticleBean>{
                override fun onRecyclerItemClicked(
                    adapter: BaseRecyclerAdapter<ArticleBean>,
                    view: View,
                    position: Int
                ) {
                    var item = adapter.data!![position]
                    var url = item.link
                    val params = WebParams(
                        selfId = item.id,
                        articleId = item.id,
                        enableCollect = true,
                        collectState = item.collect,
                        from = From.ARTICLE
                    )
                    WebLauncher.launchWeb(this@SearchResultActivity, url, params)
                }
            })
            mBinding.rvSearchResult.adapter = adapter
        } else {
            (mBinding.rvSearchResult.adapter as CategoryArticlesAdapter).appendData(list)
        }
    }
}
