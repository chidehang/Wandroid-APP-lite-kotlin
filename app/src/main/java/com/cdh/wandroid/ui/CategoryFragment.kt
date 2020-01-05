package com.cdh.wandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.cdh.wandroid.R
import com.cdh.wandroid.databinding.FragmentCategoryBinding
import com.cdh.wandroid.model.bean.CategoryBean
import com.google.android.material.tabs.TabLayout

/**
 * Created by chidehang on 2020-01-01
 */
class CategoryFragment : Fragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentCategoryBinding

    private val mViewModel by lazy {
        ViewModelProviders.of(activity!!).get(CategoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.fragment_category, null, false)
        initView()
        initData()
        return mBinding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        TabLayoutMediator(mBinding.tabsCategoryTwoLevel, mBinding.vpCategoryArticles) { tab, position ->
//            tab.text = "tab ${position}"
//        }.attach()
//    }

    private fun initView() {
        mBinding.clCategoryError.setOnClickListener(this)

        mBinding.tabsCategoryOneLevel.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                setupTwoTabLayout((tab.tag as CategoryBean).children)
            }
        })
    }

    private fun initData() {
        mViewModel.observeLoding(this) { enable ->
            if (enable) mBinding.progressCategoryLoading.show() else mBinding.progressCategoryLoading.hide()
        }

        mViewModel.observeLoadError(this) { succeed ->
            if (succeed) {
                mBinding.llCategoryContainer.visibility = View.VISIBLE
                mBinding.clCategoryError.visibility = View.GONE
            } else {
                mBinding.llCategoryContainer.visibility = View.GONE
                mBinding.clCategoryError.visibility = View.VISIBLE
            }
        }

        mViewModel.observeAllCategory(this) { data ->
            if (data.isNotEmpty()) {
                setupOneTabLayout(data)
            }
        }

        mViewModel.loadAllCategory()
    }

    private fun setupOneTabLayout(data: MutableList<CategoryBean>) {
        mBinding.tabsCategoryOneLevel.removeAllTabs()
        data.forEach {
            mBinding.tabsCategoryOneLevel.addTab(
                mBinding.tabsCategoryOneLevel.newTab()
                    .setText(it.name)
                    .setTag(it)
            )
        }

        setupTwoTabLayout(data[0].children)
    }

    private fun setupTwoTabLayout(data: MutableList<CategoryBean>) {
        mBinding.tabsCategoryTwoLevel.removeAllTabs()
        data.forEach {
            mBinding.tabsCategoryTwoLevel.addTab(
                mBinding.tabsCategoryTwoLevel.newTab()
                    .setText(it.name)
                    .setTag(it)
            )
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.cl_category_error -> {
                mViewModel.loadAllCategory()
            }
        }
    }
}