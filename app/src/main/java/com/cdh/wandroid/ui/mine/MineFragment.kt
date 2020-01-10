package com.cdh.wandroid.ui.mine

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cdh.wandroid.R
import com.cdh.wandroid.databinding.FragmentMineBinding
import com.cdh.wandroid.ui.account.LoginActivity
import com.cdh.wandroid.util.T
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

/**
 * Created by chidehang on 2020-01-01
 */
class MineFragment : Fragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentMineBinding

    private val mViewModel by lazy {
        ViewModelProviders
            .of(activity!!, ViewModelProvider.AndroidViewModelFactory(activity!!.application))
            .get(MineViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, null, false)
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this

        initView()
        initData()
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        mViewModel.updateUserName()
    }

    private fun initView() {
        OverScrollDecoratorHelper.setUpOverScroll(mBinding.svMineRoot)

        mBinding.clMineHeader.setOnClickListener(this)
        mBinding.llMineFavorite.setOnClickListener(this)
        mBinding.llMineSetting.setOnClickListener(this)
    }

    private fun initData() {

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.cl_mine_header -> {
                if (!mViewModel.isLoggedIn()) {
                    startActivity(Intent(activity, LoginActivity::class.java))
                }
            }
            R.id.ll_mine_favorite -> {
                if (!mViewModel.isLoggedIn()) {
                    startActivity(Intent(activity, LoginActivity::class.java))
                } else {
                    startActivity(Intent(activity, MyFavoriteActivity::class.java))
                }
            }
            R.id.ll_mine_setting -> {
                startActivity(Intent(activity, SettingActivity::class.java))
            }
        }
    }
}