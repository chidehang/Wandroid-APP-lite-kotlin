package com.cdh.wandroid.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdh.wandroid.model.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by chidehang on 2020-01-07
 */
class RegisterViewModel : ViewModel() {

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> = _showProgress

    private val _registerResult = MutableLiveData<Pair<Boolean, String>>()
    val registerResult: LiveData<Pair<Boolean, String>> = _registerResult

    private val _accountRepository = AccountRepository

    fun register(username: String, password: String, repassword: String) = viewModelScope.launch(Dispatchers.IO) {
        _showProgress.postValue(true)
        var result = _accountRepository.register(username, password, repassword)
        _showProgress.postValue(false)
        var pair = Pair(result.isOk(), if (result.isOk()) "" else result.getError().msg)
        _registerResult.postValue(pair)
    }
}