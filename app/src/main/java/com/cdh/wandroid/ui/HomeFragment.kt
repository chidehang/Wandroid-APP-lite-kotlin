package com.cdh.wandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.cdh.wandroid.R
import com.cdh.wandroid.databinding.FragmentHomeBinding
import com.cdh.wandroid.model.bean.ArticleBean
import com.cdh.wandroid.model.bean.BannerBean
import com.cdh.wandroid.ui.adapter.ArticleListAdapter
import com.cdh.wandroid.ui.adapter.BannerImageHolderView
import com.cdh.wandroid.ui.widget.refresh.OnLoadMoreListener
import com.cdh.wandroid.ui.widget.refresh.setRefreshListener

/**
 * Created by chidehang on 2020-01-01
 */
class HomeFragment : Fragment() {

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

    override fun onStart() {
        super.onStart()
        mBinding.cbHomeBanner.startTurning(2000)
    }

    override fun onStop() {
        super.onStop()
        mBinding.cbHomeBanner.stopTurning()
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

        mViewModel.observeBanners(activity!!) { banners ->
            if (banners == null || banners.isEmpty()) {
                mBinding.cbHomeBanner.visibility = View.GONE
            } else {
                mBinding.cbHomeBanner.visibility = View.VISIBLE
                setupBannerView(banners)
            }
        }

        mViewModel.observeArticles(activity!!) { data ->
            setupArticleList(data.first, data.second)
        }

        mViewModel.initHomeData()
    }

    private fun setupBannerView(banners: List<BannerBean>) {
        mBinding.cbHomeBanner.setPages(CBViewHolderCreator {
            BannerImageHolderView()
        }, banners)
            .setPageIndicator(intArrayOf(R.mipmap.indicator_dot_normal, R.mipmap.indicator_dot_checked))
            .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
            .setOnItemClickListener { position ->
                mViewModel.onBannerItemClick(position)
            }
    }

    private fun setupArticleList(firstPage: Boolean, articles: MutableList<ArticleBean>?) {
        if (firstPage) {
            mBinding.rvHomeArticles.adapter = ArticleListAdapter(activity!!, articles)
        } else {
            (mBinding.rvHomeArticles.adapter as ArticleListAdapter).appendData(articles)
        }
    }
}