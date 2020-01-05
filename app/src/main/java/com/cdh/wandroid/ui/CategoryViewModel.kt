package com.cdh.wandroid.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdh.wandroid.model.CategoryRepository
import com.cdh.wandroid.model.bean.CategoryBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by chidehang on 2020-01-05
 */
class CategoryViewModel : ViewModel() {

    private val _showLoading = MutableLiveData<Boolean>()
    private val _loadSucceed = MutableLiveData<Boolean>()
    private val _allCatagory = MutableLiveData<MutableList<CategoryBean>>()

    private val _categoryRepository = CategoryRepository()

    fun observeLoding(owner: LifecycleOwner, block: (Boolean) -> Unit) {
        _showLoading.observe(owner, Observer { enable ->
            block.invoke(enable)
        })
    }

    fun observeLoadError(owner: LifecycleOwner, block: (Boolean) -> Unit) {
        _loadSucceed.observe(owner, Observer { succeed ->
            block.invoke(succeed)
        })
    }

    fun observeAllCategory(owner: LifecycleOwner, block: (MutableList<CategoryBean>) -> Unit) {
        _allCatagory.observe(owner, Observer { data ->
            block.invoke(data)
        })
    }

    fun loadAllCategory() = viewModelScope.launch(Dispatchers.IO) {
        _showLoading.postValue(true)
        var result = _categoryRepository.getCategoryTree()
        _showLoading.postValue(false)
        if (result.isOk()) {
            _loadSucceed.postValue(true)
            _allCatagory.postValue(result.getResponse()?.data)
        } else {
            _loadSucceed.postValue(false)
        }
    }
}