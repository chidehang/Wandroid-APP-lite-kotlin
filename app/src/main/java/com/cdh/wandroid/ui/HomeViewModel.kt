package com.cdh.wandroid.ui

import androidx.lifecycle.*
import com.cdh.wandroid.model.BannerRepository
import com.cdh.wandroid.model.HomeArticlesRepository
import com.cdh.wandroid.model.bean.ArticleBean
import com.cdh.wandroid.model.bean.BannerBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by chidehang on 2020-01-01
 */
class HomeViewModel : ViewModel() {

    private val _refreshable = MutableLiveData<Boolean>()
    private val _banners = MutableLiveData<Pair<Boolean, MutableList<BannerBean>?>>()
    private val _articles = MutableLiveData<Pair<Boolean, MutableList<ArticleBean>?>>()

    private val _bannerRepository = BannerRepository()
    private val _articlesRepository = HomeArticlesRepository()

    @Volatile
    private var pageNo = 1

    @Volatile
    private var refreshData = true

    fun observeRefreshable(owner: LifecycleOwner, block: (Boolean) -> Unit) {
        _refreshable.observe(owner, Observer { enable ->
            block.invoke(enable)
        })
    }

    fun observeBanners(owner: LifecycleOwner, block: (Pair<Boolean, MutableList<BannerBean>?>) -> Unit) {
        _banners.observe(owner, Observer { banners ->
            block.invoke(banners)
        })
    }

    fun observeArticles(owner: LifecycleOwner, block: (Pair<Boolean, MutableList<ArticleBean>?>) -> Unit) {
        _articles.observe(owner, Observer { articles ->
            block.invoke(articles)
        })
    }

    fun initHomeData() {
        refreshData = true
        loadBannerList()
        loadArticleList()
    }

    private fun loadBannerList() = viewModelScope.launch(Dispatchers.IO) {
        val result = _bannerRepository.getBannerData()
        var pair = Pair(isRefreshData(), result.getResponse()?.data)
        _banners.postValue(pair)
    }

    private fun loadArticleList() = viewModelScope.launch(Dispatchers.IO) {
        pageNo = 0
        val result = _articlesRepository.getArticleData(pageNo)
        _refreshable.postValue(false)
        var pair = Pair(isRefreshData(), result.getResponse()?.data?.datas)
        _articles.postValue(pair)
    }

    fun loadMoreArticles() = viewModelScope.launch(Dispatchers.IO) {
        val oldPageNo = pageNo
        val result = _articlesRepository.getArticleData(pageNo++)
        if (result.isOk()) {
            var pair = Pair(isRefreshData(), result.getResponse()?.data?.datas)
            _articles.postValue(pair)
        } else {
            pageNo = oldPageNo
        }
    }

    fun onBannerItemClick(position: Int) {
        var banner = _banners.value?.second?.get(position)

    }

    private fun isRefreshData(): Boolean {
        var b = this.refreshData
        this.refreshData = false
        return b
    }
}