package com.cdh.wandroid.ui.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cdh.wandroid.R
import com.cdh.wandroid.databinding.ActivityLoginBinding
import com.cdh.wandroid.ui.BaseActivity
import com.cdh.wandroid.ui.widget.webview.WebLauncher
import com.cdh.wandroid.util.T
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityLoginBinding

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        initView()
        initData()
    }

    private fun initView() {
        mBinding.titleBarLogin.setTitle(getString(R.string.login_title))

        mBinding.tvLoginNewRegister.setOnClickListener(this)
        mBinding.tvLoginRetrievePassword.setOnClickListener(this)
        mBinding.btnLogin.setOnClickListener(this)
    }

    private fun initData() {
        mViewModel.showProgress.observe(this, Observer { show ->
            if (show) {
                showProgress("登录中...")
            } else {
                dismissProgress()
            }
        })

        mViewModel.loginResult.observe(this, Observer { pair ->
            if (pair.first) {
                T.showShort("登录成功")
                finish()
            } else {
                T.showShort(pair.second)
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_login_new_register -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            R.id.tv_login_retrieve_password -> {
                var url = "https://github.com/hongyangAndroid/wanandroid/issues"
                WebLauncher.launchWeb(this, url)
            }
            R.id.btn_login -> {
                mBinding.inputLoginPassword.error = ""
                mBinding.inputLoginUsername.error = ""

                if (mBinding.inputLoginUsername.editText!!.text.isEmpty()) {
                    showInputError(mBinding.inputLoginUsername, getString(R.string.login_username_illegal))
                    return
                }
                if (mBinding.inputLoginPassword.editText!!.text.isEmpty()) {
                    showInputError(mBinding.inputLoginPassword, getString(R.string.login_password_illegal))
                    return
                }

                mViewModel.login(mBinding.inputLoginUsername.editText!!.text.toString(),
                    mBinding.inputLoginPassword.editText!!.text.toString())
            }
        }
    }

    private fun showInputError(inputLayout: TextInputLayout, tips: String) {
        inputLayout.error = tips
        inputLayout.editText?.isFocusableInTouchMode = true
        inputLayout.editText?.requestFocus()
    }
}
