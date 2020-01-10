package com.cdh.wandroid.ui.home

import androidx.lifecycle.*
import com.cdh.wandroid.model.SearchRepository
import com.cdh.wandroid.model.bean.ArticleBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by chidehang on 2020-01-08
 */
class SearchResultViewModel(val searchKey: String) : ViewModel() {

    private val searchRepository = SearchRepository()

    private val _refreshable = MutableLiveData<Boolean>()
    val refreshable: LiveData<Boolean> = _refreshable

    private val _articles = MutableLiveData<Pair<Boolean, MutableList<ArticleBean>>>()
    val articles: LiveData<Pair<Boolean, MutableList<ArticleBean>>> = _articles

    private val _showError = MutableLiveData<Boolean>(false)
    val showError: LiveData<Boolean> = _showError

    private var pageNo = 0

    fun search() = viewModelScope.launch(Dispatchers.IO) {
        pageNo = 0
        _refreshable.postValue(true)
        var result = searchRepository.search(pageNo, searchKey)
        _refreshable.postValue(false)

        if (result.isOk()
            && result.getResponse()!!.data.datas != null
            && result.getResponse()!!.data.datas.isNotEmpty()) {
            _showError.postValue(false)
            var pair = Pair(true, result.getResponse()!!.data.datas)
            _articles.postValue(pair)
            return@launch
        }
        _showError.postValue(true)
    }

    fun searchMore() = viewModelScope.launch(Dispatchers.IO) {
        var oldPage = pageNo
        var result = searchRepository.search(++pageNo, searchKey)
        if (result.isOk()) {
            if (result.getResponse()!!.data.datas != null && result.getResponse()!!.data.datas.isNotEmpty()) {
                var pair = Pair(false, result.getResponse()!!.data.datas)
                _articles.postValue(pair)
            }
        } else {
            pageNo = oldPage
        }
    }
}

class SearchResultViewModelFactory(val searchKey: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(String::class.java).newInstance(searchKey)
    }
}