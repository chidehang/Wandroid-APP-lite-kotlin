package com.cdh.wandroid.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdh.wandroid.model.AccountRepository
import com.cdh.wandroid.model.bean.AccountBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by chidehang on 2020-01-07
 */
class LoginViewModel : ViewModel() {

    private val _showProgress = MutableLiveData<Boolean>()
    var showProgress: LiveData<Boolean> = _showProgress

    private val _loginResult = MutableLiveData<Pair<Boolean, String>>()
    var loginResult: LiveData<Pair<Boolean, String>> = _loginResult

    private val _accountRepository = AccountRepository

    fun login(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        _showProgress.postValue(true)
        val result = _accountRepository.login(username, password)
        _showProgress.postValue(false)
        var pair = Pair(result.isOk(), if (result.isOk()) "" else result.getError().msg)
        _loginResult.postValue(pair)
    }
}