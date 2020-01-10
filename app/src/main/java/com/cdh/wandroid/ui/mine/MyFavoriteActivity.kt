package com.cdh.wandroid.ui.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.cdh.wandroid.R
import com.cdh.wandroid.databinding.ActivityMyFavoriteBinding
import com.cdh.wandroid.model.bean.ArticleBean
import com.cdh.wandroid.ui.adapter.FavoriteArticlesAdapter
import com.cdh.wandroid.ui.adapter.base.BaseRecyclerAdapter
import com.cdh.wandroid.ui.widget.SwipeItemTouchCallback
import com.cdh.wandroid.ui.widget.refresh.OnLoadMoreListener
import com.cdh.wandroid.ui.widget.refresh.setRefreshListener
import com.cdh.wandroid.ui.widget.webview.From
import com.cdh.wandroid.ui.widget.webview.WebLauncher
import com.cdh.wandroid.ui.widget.webview.WebParams
import com.cdh.wandroid.util.T

class MyFavoriteActivity : AppCompatActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(MyFavoriteViewModel::class.java)
    }

    private lateinit var mBinding: ActivityMyFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_favorite)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel

        initView()
        initData()
    }

    private fun initView() {
        mBinding.titleBarFavorite.setTitle(getString(R.string.mine_favorite_label))

        mBinding.rvFavoriteArticles.layoutManager = LinearLayoutManager(this)
        mBinding.rvFavoriteArticles.setRefreshListener(object: OnLoadMoreListener {
            override fun onLoadMore() {
                mViewModel.loadMoreData()
            }
        })

        val touchCallback = SwipeItemTouchCallback()
        val itemTouchHelper = ItemTouchHelper(touchCallback)
        itemTouchHelper.attachToRecyclerView(mBinding.rvFavoriteArticles)

        mBinding.swipeRefreshFavorite.setOnRefreshListener {
            mViewModel.loadFirstData()
        }
    }

    private fun initData() {
        mViewModel.refreshable.observe(this, Observer { enable ->
            mBinding.swipeRefreshFavorite.isRefreshing = enable
        })

        mViewModel.articles.observe(this, Observer { pair ->
            setupRecyclerView(pair.first, pair.second)
        })

        mViewModel.toastTips.observe(this, Observer { tips ->
            T.showShort(tips)
        })
    }

    override fun onStart() {
        super.onStart()
        mViewModel.loadFirstData()
    }

    private fun setupRecyclerView(isRefresh: Boolean, list: MutableList<ArticleBean>) {
        if (isRefresh) {
            var adapter = FavoriteArticlesAdapter(this, list)
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
                        articleId = item.originId,
                        enableCollect = true,
                        collectState = true,
                        from = From.FAVORITE
                    )
                    WebLauncher.launchWeb(this@MyFavoriteActivity, url, params)
                }
            })
            mBinding.rvFavoriteArticles.adapter = adapter
        } else {
            (mBinding.rvFavoriteArticles.adapter as FavoriteArticlesAdapter).appendData(list)
        }
    }
}
