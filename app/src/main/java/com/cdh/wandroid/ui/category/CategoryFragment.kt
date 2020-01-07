package com.cdh.wandroid.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.widget.PopupWindowCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cdh.wandroid.R
import com.cdh.wandroid.databinding.FragmentCategoryBinding
import com.cdh.wandroid.model.bean.CategoryBean
import com.cdh.wandroid.ui.adapter.MainNavPagerAdapter
import com.cdh.wandroid.ui.widget.CategoryPickerDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Created by chidehang on 2020-01-01
 */
class CategoryFragment : Fragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentCategoryBinding

    private var mCategoryPicker: CategoryPickerDialog?= null

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

    override fun onPause() {
        super.onPause()
        mCategoryPicker?.dismiss()
    }

    private fun initView() {
        mBinding.includeCategoryError.clLoadingError.setOnClickListener(this)

        mBinding.tabsCategoryOneLevel.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                setupTwoTabLayout((tab.tag as CategoryBean).children)
            }
        })

        mBinding.ivCategoryMore.setOnClickListener(this)
    }

    private fun initData() {
        mViewModel.observeLoding(this) { enable ->
            if (enable) mBinding.progressCategoryLoading.show() else mBinding.progressCategoryLoading.hide()
        }

        mViewModel.loadSucceed.observe(this, Observer { succeed ->
            if (succeed) {
                mBinding.llCategoryContainer.visibility = View.VISIBLE
                mBinding.includeCategoryError.clLoadingError.visibility = View.GONE
            } else {
                mBinding.llCategoryContainer.visibility = View.GONE
                mBinding.includeCategoryError.clLoadingError.visibility = View.VISIBLE
            }
        })

        mViewModel.allCategory.observe(this, Observer { data ->
            setupOneTabLayout(data)
        })

        mViewModel.loadAllCategory()
    }

    private fun setupOneTabLayout(data: MutableList<CategoryBean>) {
        if (data.isEmpty()) {
            return
        }

        mBinding.tabsCategoryOneLevel.removeAllTabs()
        data.forEach {
            mBinding.tabsCategoryOneLevel.addTab(
                mBinding.tabsCategoryOneLevel.newTab()
                    .setText(it.name)
                    .setTag(it)
            )
        }
    }

    private fun setupTwoTabLayout(data: MutableList<CategoryBean>) {
        if (data.isEmpty()) {
            return
        }

        val fragments = MutableList<Fragment>(data.size) { index ->
            CategoryContentFragment.createInstance(data[index].id)
        }
        mBinding.vpCategoryArticles.adapter = MainNavPagerAdapter(activity!!, fragments)
        TabLayoutMediator(mBinding.tabsCategoryTwoLevel, mBinding.vpCategoryArticles) { tab, position ->
            tab.text = data[position].name
        }.attach()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.include_category_error -> {
                mViewModel.loadAllCategory()
            }
            R.id.iv_category_more -> {
                mCategoryPicker?.updateCheckedCategory(mBinding.tabsCategoryOneLevel.selectedTabPosition)
                showCategoryPickerDialog()
            }
        }
    }

    private fun showCategoryPickerDialog() {
        if (mViewModel.allCategory.value == null || mViewModel.allCategory.value!!.isEmpty()) {
            return
        }

        if (mCategoryPicker == null) {
            mCategoryPicker = CategoryPickerDialog.createInstance(mViewModel.allCategory.value!!)
            mCategoryPicker?.setOnPickListener(object: CategoryPickerDialog.OnPickListener{
                override fun onPicked(position: Int) {
                    mBinding.tabsCategoryOneLevel.getTabAt(position)?.select()
                }
            })
        }
        mCategoryPicker?.show(childFragmentManager, CategoryPickerDialog.TAG)
    }
}