package com.cdh.wandroid.ui.mine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdh.wandroid.model.FavoriteRepository
import com.cdh.wandroid.model.bean.ArticleBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by chidehang on 2020-01-09
 */
class MyFavoriteViewModel : ViewModel() {

    private val favoriteRepository = FavoriteRepository

    private val _toastTips = MutableLiveData<String>()
    val toastTips: LiveData<String> = _toastTips

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> = _showProgress

    private val _showError = MutableLiveData<Boolean>(false)
    val showError: LiveData<Boolean> = _showError

    private val _refreshable = MutableLiveData<Boolean>()
    val refreshable: LiveData<Boolean> = _refreshable

    private val _articles = MutableLiveData<Pair<Boolean, MutableList<ArticleBean>>>()
    val articles: LiveData<Pair<Boolean, MutableList<ArticleBean>>> = _articles

    private val _uncollectItem = MutableLiveData<ArticleBean>()
    val uncollectItem: LiveData<ArticleBean> = _uncollectItem

    private var pageNo = 0

    fun loadFirstData() = viewModelScope.launch(Dispatchers.IO) {
        pageNo = 0
        _refreshable.postValue(true)
        var result = favoriteRepository.getMyFavoriteArticles(pageNo)
        _refreshable.postValue(false)

        if (result.isOk()
            && result.getResponse()!!.data.datas != null
            && result.getResponse()!!.data.datas.isNotEmpty()) {
            _showError.postValue(false)
            var pair = Pair(true, result.getResponse()!!.data.datas)
            _articles.postValue(pair)
            return@launch
        }
        if (!result.isOk()) {
            _toastTips.postValue(result.getError().msg)
        }
        _showError.postValue(true)
    }

    fun loadMoreData() = viewModelScope.launch(Dispatchers.IO) {
        var oldPage = pageNo
        var result = favoriteRepository.getMyFavoriteArticles(++pageNo)
        if (result.isOk()) {
            if (result.getResponse()!!.data.datas != null && result.getResponse()!!.data.datas.isNotEmpty()) {
                var pair = Pair(false, result.getResponse()!!.data.datas)
                _articles.postValue(pair)
            }
        } else {
            pageNo = oldPage
            _toastTips.postValue(result.getError().msg)
        }
    }

    fun uncollect(article: ArticleBean) = viewModelScope.launch(Dispatchers.IO) {
        _showProgress.postValue(true)
        var result = favoriteRepository.uncollect(article.id, article.originId, true)
        _showProgress.postValue(false)
        if (result.isOk()) {
            _uncollectItem.postValue(article)
        } else {
            _toastTips.postValue("收藏失败")
        }
    }
}