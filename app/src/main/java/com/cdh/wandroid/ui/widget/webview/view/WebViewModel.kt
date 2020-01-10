package com.cdh.wandroid.ui.widget.webview.view

import android.app.Application
import android.content.Intent
import androidx.lifecycle.*
import com.cdh.wandroid.R
import com.cdh.wandroid.model.AccountRepository
import com.cdh.wandroid.model.FavoriteRepository
import com.cdh.wandroid.ui.account.LoginActivity
import com.cdh.wandroid.ui.widget.webview.From
import com.cdh.wandroid.ui.widget.webview.WebParams
import com.cdh.wandroid.util.T
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable

/**
 * Created by chidehang on 2020-01-09
 */
class WebViewModel(application: Application, val params: WebParams?) : AndroidViewModel(application) {

    private val _enableCollect = MutableLiveData<Boolean>(params?.enableCollect)
    val enableCollect: LiveData<Boolean> = _enableCollect

    private val _collectStatus = MutableLiveData<Boolean>(params?.collectState)
    val collectStatus: LiveData<Boolean> = _collectStatus

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val accountRepository = AccountRepository
    private val favoriteRepository = FavoriteRepository

    fun onCollectClick() {
        if (!accountRepository.isLoggedIn()) {
            val application = getApplication<Application>()
            T.showShort(application.getString(R.string.mine_login_first_tip))
            val intent = Intent(application, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            application.startActivity(intent)
            return
        }

        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            if (_collectStatus.value == true) {
                uncollect()
            } else {
                collect()
            }
            _loading.postValue(false)
        }
    }

    private suspend fun collect() {
        params?.let {
            val result = favoriteRepository.collect(params.selfId)
            if (result.isOk()) {
                _collectStatus.postValue(true)
            } else {
                T.showShort(result.getError().msg)
            }
        }
    }

    private suspend fun uncollect() {
        params?.let {
            var result = favoriteRepository.uncollect(params.selfId, params.articleId, params.from==From.FAVORITE)
            if (result.isOk()) {
                _collectStatus.postValue(false)
            } else {
                T.showShort(result.getError().msg)
            }
        }
    }
}

class WebViewModelFactory(val application: Application, val params: WebParams?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Application::class.java, WebParams::class.java)
            .newInstance(application, params)
    }
}