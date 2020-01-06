package com.cdh.wandroid.ui.category

import androidx.lifecycle.*
import com.cdh.wandroid.model.ArticlesRepository
import com.cdh.wandroid.model.bean.ArticleBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by chidehang on 2020-01-06
 */
class CategoryContentViewModel(val categoryId: Int) : ViewModel() {

    private val _refreshable = MutableLiveData<Boolean>()
    private val _articles = MutableLiveData<Pair<Boolean, MutableList<ArticleBean>?>>()

    private val _repository = ArticlesRepository()

    @Volatile
    private var pageNo = 0

    fun observeRefreshable(owner: LifecycleOwner, block: (Boolean) -> Unit) {
        _refreshable.observe(owner, Observer { enable ->
            block.invoke(enable)
        })
    }

    fun observeArticles(owner: LifecycleOwner, block: (Pair<Boolean, MutableList<ArticleBean>?>) -> Unit) {
        _articles.observe(owner, Observer { data ->
            block.invoke(data)
        })
    }

    fun loadAriticlesByCategory() = viewModelScope.launch(Dispatchers.IO) {
        pageNo = 0
        _refreshable.postValue(true)
        val result = _repository.getCategoryArticles(pageNo, categoryId)
        _refreshable.postValue(false)
        if (result.isOk() && result.getResponse()?.data?.datas != null) {
            val pair = Pair(true, result.getResponse()?.data?.datas)
            _articles.postValue(pair)
        }
    }

    fun loadMoreArticles() = viewModelScope.launch(Dispatchers.IO) {
        val oldPage = pageNo

        val result = _repository.getCategoryArticles(++pageNo, categoryId)
        if (result.isOk()) {
            val pair = Pair(false, result.getResponse()?.data?.datas)
            _articles.postValue(pair)
        } else {
            pageNo = oldPage
        }
    }
}

class CategoryContentViewModelFactory(val categoryId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Int::class.java).newInstance(categoryId)
    }
}