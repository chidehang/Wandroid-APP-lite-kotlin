package com.cdh.wandroid.ui.category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cdh.wandroid.R
import com.cdh.wandroid.databinding.FragmentCategoryContentBinding
import com.cdh.wandroid.model.bean.ArticleBean
import com.cdh.wandroid.ui.adapter.CategoryArticlesAdapter
import com.cdh.wandroid.ui.adapter.base.BaseRecyclerAdapter
import com.cdh.wandroid.ui.widget.refresh.OnLoadMoreListener
import com.cdh.wandroid.ui.widget.refresh.setRefreshListener
import com.cdh.wandroid.ui.widget.webview.WebLauncher

/**
 * Created by chidehang on 2020-01-05
 */
class CategoryContentFragment: Fragment() {

    private lateinit var mBinding: FragmentCategoryContentBinding

    private lateinit var mViewModel: CategoryContentViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val cid = arguments?.get(EXTRA_CID) as Int
        mViewModel = ViewModelProviders
            .of(this, CategoryContentViewModelFactory(cid))
            .get(CategoryContentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.fragment_category_content, null, false)
        initView()
        initData()
        return mBinding.root
    }

    private fun initView() {
        mBinding.swipeRefreshCategoryArticles.setOnRefreshListener {
            mViewModel.loadAriticlesByCategory()
        }

        mBinding.rvCategoryContent.layoutManager = LinearLayoutManager(activity)
        mBinding.rvCategoryContent.setRefreshListener(object: OnLoadMoreListener{
            override fun onLoadMore() {
                mViewModel.loadMoreArticles()
            }
        })
    }

    private fun initData() {
        mViewModel.observeRefreshable(this) { enable ->
            mBinding.swipeRefreshCategoryArticles.isRefreshing = enable
        }

        mViewModel.observeArticles(this) { data ->
            setupRecyclerView(data.first, data.second!!)
        }

        mViewModel.loadAriticlesByCategory()
    }

    private fun setupRecyclerView(isRefresh: Boolean, list: MutableList<ArticleBean>) {
        if (isRefresh) {
            var adapter = CategoryArticlesAdapter(activity!!, list)
            adapter.setOnRecyclerItemClickListener(object: BaseRecyclerAdapter.OnRecyclerItemClickListener<ArticleBean>{
                override fun onRecyclerItemClicked(
                    adapter: BaseRecyclerAdapter<ArticleBean>,
                    view: View,
                    position: Int
                ) {
                    var url = adapter.data?.get(position)?.link
                    WebLauncher.launchWeb(activity, url)
                }
            })
            mBinding.rvCategoryContent.adapter = adapter
        } else {
            (mBinding.rvCategoryContent.adapter as CategoryArticlesAdapter).appendData(list)
        }
    }

    companion object {
        const val EXTRA_CID = "extra_cid"

        fun createInstance(cid: Int): CategoryContentFragment {
            var instance = CategoryContentFragment()
            var args = Bundle()
            args.putInt(EXTRA_CID, cid)
            instance.arguments = args
            return instance
        }
    }
}