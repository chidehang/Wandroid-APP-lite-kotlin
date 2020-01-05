package com.cdh.wandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cdh.wandroid.R
import com.cdh.wandroid.databinding.FragmentHomeBinding
import com.cdh.wandroid.model.bean.ArticleBean
import com.cdh.wandroid.model.bean.BannerBean
import com.cdh.wandroid.ui.adapter.HomeListAdapter
import com.cdh.wandroid.ui.widget.refresh.OnLoadMoreListener
import com.cdh.wandroid.ui.widget.refresh.setRefreshListener
import com.cdh.wandroid.ui.widget.webview.WebLauncher
import com.cdh.wandroid.util.T

/**
 * Created by chidehang on 2020-01-01
 */
class HomeFragment : Fragment(), HomeListAdapter.OnHomeModuleClickListener {

    private lateinit var mBinding: FragmentHomeBinding

    private lateinit var mViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(activity!!).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.fragment_home, null, false)
        initView()
        initData()
        return mBinding.root
    }

    private fun initView() {
        mBinding.swipeRefreshHome.setOnRefreshListener {
            mViewModel.initHomeData()
        }

        mBinding.rvHomeArticles.layoutManager = LinearLayoutManager(activity)
        mBinding.rvHomeArticles.setRefreshListener(object: OnLoadMoreListener {
            override fun onLoadMore() {
                mViewModel.loadMoreArticles()
            }
        })
    }

    private fun initData() {
        mViewModel.observeRefreshable(activity!!) { enable ->
            mBinding.swipeRefreshHome.isRefreshing = enable
        }

        mViewModel.observeBanners(activity!!) { data ->
            if (data.second != null && data.second!!.isNotEmpty()) {
                setupBannerView(data.first, data.second!!)
            }
        }

        mViewModel.observeArticles(activity!!) { data ->
            setupArticleList(data.first, data.second)
        }

        mViewModel.initHomeData()
    }

    private fun setupBannerView(isRefresh: Boolean, banners: MutableList<BannerBean>) {
        var bean = ArticleBean(viewType = 1)
        bean.headerBanners = banners
        if (mBinding.rvHomeArticles.adapter == null || isRefresh) {
            var data = mutableListOf(bean)
            mBinding.rvHomeArticles.adapter = createHomeListAdapter(data)
        } else {
            (mBinding.rvHomeArticles.adapter as HomeListAdapter).insert(0, bean)
        }
    }

    private fun setupArticleList(isRefresh: Boolean, articles: MutableList<ArticleBean>?) {
        if (mBinding.rvHomeArticles.adapter == null || isRefresh) {
            mBinding.rvHomeArticles.adapter = createHomeListAdapter(articles)
        } else {
            (mBinding.rvHomeArticles.adapter as HomeListAdapter).appendData(articles)
        }
    }

    private fun createHomeListAdapter(data: MutableList<ArticleBean>?): HomeListAdapter {
        var adapter = HomeListAdapter(activity!!, data)
        adapter.setOnHomeModuleClickListener(this)
        return adapter
    }

    override fun onSearchClick() {
        T.showShort("onSearchClick")
    }

    override fun onBannerClick(item: BannerBean, indexOfBanners: Int) {
        val url = item.url
        WebLauncher.launchWeb(activity, url)
    }

    override fun onArticleClick(item: ArticleBean, indexOfArticles: Int) {
        val url = item.link
        WebLauncher.launchWeb(activity, url)
    }
}