package com.cdh.wandroid.ui.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.cdh.wandroid.R
import com.cdh.wandroid.databinding.ActivitySettingBinding
import com.cdh.wandroid.ui.BaseActivity
import com.cdh.wandroid.ui.widget.webview.WebLauncher
import com.cdh.wandroid.util.T

class SettingActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivitySettingBinding

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(SettingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel

        initView()
    }

    private fun initView() {
        mBinding.titleBarSetting.setTitle(getString(R.string.mine_setting_label))

        mBinding.llSettingAbout.setOnClickListener(this)
        mBinding.llSettingGitLink.setOnClickListener(this)
        mBinding.btnLogout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.ll_setting_about-> {
                val url = "https://me.csdn.net/dehang0"
                WebLauncher.launchWeb(this, url)
            }
            R.id.ll_setting_git_link -> {
                val url = "https://github.com/chidehang/Wandroid-APP-lite-kotlin"
                WebLauncher.launchWeb(this, url)
            }
            R.id.btn_logout -> {
                mViewModel.logout()
                T.showShort("退出登录成功")
            }
        }
    }
}
