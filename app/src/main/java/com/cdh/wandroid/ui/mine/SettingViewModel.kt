package com.cdh.wandroid.ui.mine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdh.wandroid.BuildConfig
import com.cdh.wandroid.model.AccountRepository
import kotlinx.coroutines.launch

/**
 * Created by chidehang on 2020-01-08
 */
class SettingViewModel : ViewModel() {

    private val accountRepository = AccountRepository

    private val _isLoggedIn = MutableLiveData<Boolean>(accountRepository.isLoggedIn())
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    private val _appVersion = MutableLiveData<String>(BuildConfig.VERSION_NAME)
    val appVersion: LiveData<String> = _appVersion

    fun logout() = viewModelScope.launch {
        accountRepository.logout()
        _isLoggedIn.postValue(false)
    }
}