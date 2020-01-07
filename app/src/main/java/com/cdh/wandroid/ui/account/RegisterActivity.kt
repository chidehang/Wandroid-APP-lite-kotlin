package com.cdh.wandroid.ui.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cdh.wandroid.R
import com.cdh.wandroid.databinding.ActivityRegisterBinding
import com.cdh.wandroid.ui.BaseActivity
import com.cdh.wandroid.util.T
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : BaseActivity() {

    private lateinit var mBinding: ActivityRegisterBinding

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(RegisterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        initView()
        initData()
    }

    private fun initView() {
        mBinding.titleBarRegister.setTitle(getString(R.string.register_title))

        mBinding.btnRegister.setOnClickListener {
            mBinding.inputRegisterUsername.error = ""
            mBinding.inputRegisterPassword.error = ""
            mBinding.inputRegisterRepassword.error = ""

            if (mBinding.inputRegisterUsername.editText!!.text.isEmpty()) {
                showInputError(mBinding.inputRegisterUsername, getString(R.string.login_username_illegal))
                return@setOnClickListener
            }
            if (mBinding.inputRegisterPassword.editText!!.text.isEmpty()) {
                showInputError(mBinding.inputRegisterPassword, getString(R.string.login_password_illegal))
                return@setOnClickListener
            }
            if (mBinding.inputRegisterRepassword.editText!!.text.isEmpty()) {
                showInputError(mBinding.inputRegisterRepassword, getString(R.string.login_password_illegal))
                return@setOnClickListener
            }
            var password = mBinding.inputRegisterPassword.editText!!.text.toString()
            var repassword = mBinding.inputRegisterRepassword.editText!!.text.toString()
            if (password != repassword) {
                showInputError(mBinding.inputRegisterRepassword, getString(R.string.register_repassword_illegal))
                return@setOnClickListener
            }

            mViewModel.register(mBinding.inputRegisterUsername.editText!!.text.toString(), password, repassword)
        }
    }

    private fun initData() {
        mViewModel.showProgress.observe(this, Observer { show ->
            if (show) {
                showProgress("注册中...")
            } else {
                dismissProgress()
            }
        })

        mViewModel.registerResult.observe(this, Observer { pair ->
            if (pair.first) {
                T.showShort("注册成功")
                finish()
            } else {
                T.showShort(pair.second)
            }
        })
    }

    private fun showInputError(inputLayout: TextInputLayout, tips: String) {
        inputLayout.error = tips
        inputLayout.editText?.isFocusableInTouchMode = true
        inputLayout.editText?.requestFocus()
    }
}
