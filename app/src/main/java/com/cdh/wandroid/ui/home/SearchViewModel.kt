package com.cdh.wandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdh.wandroid.model.SearchRepository
import com.cdh.wandroid.model.bean.SearchKeyBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by chidehang on 2020-01-08
 */
class SearchViewModel : ViewModel() {

    private val searchRepository = SearchRepository()

    private val _hotKeys = MutableLiveData<MutableList<SearchKeyBean>>()
    val hotKeys: LiveData<MutableList<SearchKeyBean>> = _hotKeys

    private val _historyKeys = MutableLiveData<MutableList<SearchKeyBean>>()
    val historyKeys: LiveData<MutableList<SearchKeyBean>> = _historyKeys

    fun loadHotKeys() = viewModelScope.launch(Dispatchers.IO) {
        var result = searchRepository.getHotKeys()
        if (result.isOk() && result.getResponse() != null && result.getResponse()!!.data.isNotEmpty()) {
            _hotKeys.postValue(result.getResponse()!!.data)
        }
    }

    fun saveSearchKey(key: String) = viewModelScope.launch(Dispatchers.IO) {
        var bean = SearchKeyBean(name = key)
        searchRepository.saveSearchKey(bean)
    }

    fun clearHistoryKey() {
        _historyKeys.postValue(mutableListOf())
        viewModelScope.launch(Dispatchers.IO) {
            searchRepository.deleteAll()
        }
    }

    fun loadHistoryKey() = viewModelScope.launch(Dispatchers.IO) {
        var list = searchRepository.getAllSearchKey()
        _historyKeys.postValue(list)
    }
}