package com.cdh.wandroid.ui.category

import androidx.lifecycle.*
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
    private val _allCategory = MutableLiveData<MutableList<CategoryBean>>()

    val allCategory: LiveData<MutableList<CategoryBean>> = _allCategory

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

    fun loadAllCategory() = viewModelScope.launch(Dispatchers.IO) {
        _showLoading.postValue(true)
        var result = _categoryRepository.getCategoryTree()
        _showLoading.postValue(false)
        if (result.isOk()) {
            _loadSucceed.postValue(true)
            _allCategory.postValue(result.getResponse()?.data)
        } else {
            _loadSucceed.postValue(false)
        }
    }
}