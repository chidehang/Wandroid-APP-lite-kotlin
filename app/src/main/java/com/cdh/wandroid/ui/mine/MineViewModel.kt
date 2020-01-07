package com.cdh.wandroid.ui.mine

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cdh.wandroid.R
import com.cdh.wandroid.model.AccountRepository

/**
 * Created by chidehang on 2020-01-07
 */
class MineViewModel(application: Application) : AndroidViewModel(application) {

    private val _username = MutableLiveData<String>(application.resources.getString(R.string.mine_login_first_tip))

    var username:LiveData<String> = _username

    private val accountRepository = AccountRepository

    fun isLoggedIn(): Boolean {
        return accountRepository.isLoggedIn()
    }

    fun updateLoginStatus() {
        var accountBean = accountRepository.getAccountInfo()
        var name = accountBean?.username
            ?: getApplication<Application>().resources.getString(R.string.mine_login_first_tip)
        _username.postValue(name)
    }
}