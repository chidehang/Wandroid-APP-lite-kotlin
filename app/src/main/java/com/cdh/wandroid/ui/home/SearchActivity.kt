package com.cdh.wandroid.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cdh.wandroid.ArgumentConstants
import com.cdh.wandroid.R
import com.cdh.wandroid.databinding.ActivitySearchBinding
import com.cdh.wandroid.model.bean.SearchKeyBean
import com.cdh.wandroid.ui.BaseActivity
import com.cdh.wandroid.ui.adapter.HistoryKeysAdapter
import com.cdh.wandroid.ui.adapter.base.BaseRecyclerAdapter
import com.cdh.wandroid.util.DeviceUtils

class SearchActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivitySearchBinding

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        initView()
        initData()
    }

    private fun initView() {
        mBinding.titleBarSearch.setTitle(getString(R.string.search_title))

        mBinding.rvSearchHistoryKeys.layoutManager = LinearLayoutManager(this)

        mBinding.ivSearchGo.setOnClickListener(this)
        mBinding.tvSearchClearKeys.setOnClickListener(this)
    }

    private fun initData() {
        mViewModel.hotKeys.observe(this, Observer { list ->
            setupHotKeys(list)
        })

        mViewModel.historyKeys.observe(this, Observer { list ->
            setupHistoryKeys(list)
        })

        mViewModel.loadHotKeys()
    }

    override fun onStart() {
        super.onStart()
        mViewModel.loadHistoryKey()
    }

    private fun setupHotKeys(list: MutableList<SearchKeyBean>) {
        mBinding.flowSearchHotKeys.removeAllViews()
        for (item in list) {
            var textView = TextView(this)
            textView.text = item.name
            textView.setBackgroundResource(R.drawable.home_search_bg)
            val p = DeviceUtils.dip2px(this, 5f)
            textView.setPadding(p, p, p, p)
            textView.setOnClickListener {
                gotoSearch(item.name)
            }
            mBinding.flowSearchHotKeys.addView(textView)
        }
    }

    private fun setupHistoryKeys(list: MutableList<SearchKeyBean>) {
        var adapter = HistoryKeysAdapter(this, list)
        adapter.setOnRecyclerItemClickListener(object: BaseRecyclerAdapter.OnRecyclerItemClickListener<SearchKeyBean> {
            override fun onRecyclerItemClicked(
                adapter: BaseRecyclerAdapter<SearchKeyBean>,
                view: View,
                position: Int
            ) {
                gotoSearch(adapter.data!![position].name)
            }
        })
        mBinding.rvSearchHistoryKeys.adapter = adapter
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.iv_search_go -> {
                if (mBinding.edSearchInput.text == null || mBinding.edSearchInput.text!!.isEmpty()) {
                    mBinding.edSearchInput.error = getString(R.string.search_input_hint)
                    return
                }

                var key = mBinding.edSearchInput.text.toString()
                gotoSearch(key)
                mViewModel.saveSearchKey(key)
            }
            R.id.tv_search_clear_keys -> {
                mViewModel.clearHistoryKey()
            }
        }
    }

    private fun gotoSearch(key: String) {
        var intent = Intent(this, SearchResultActivity::class.java)
        intent.putExtra(ArgumentConstants.EXTRA_SEARCH_KEY, key)
        startActivity(intent)
    }
}
