package com.cdh.wandroid.ui

import androidx.lifecycle.*
import com.cdh.wandroid.model.BannerRepository
import com.cdh.wandroid.model.HomeArticlesRepository
import com.cdh.wandroid.model.bean.ArticleBean
import com.cdh.wandroid.model.bean.BannerBean
import com.cdh.wandroid.util.T
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by chidehang on 2020-01-01
 */
class HomeViewModel : ViewModel() {

    private val _refreshable = MutableLiveData<Boolean>()
    private val _banners = MutableLiveData<List<BannerBean>>()
    private val _articles = MutableLiveData<Pair<Boolean, MutableList<ArticleBean>?>>()

    private val _bannerRepository = BannerRepository()
    private val _articlesRepository = HomeArticlesRepository()

    @Volatile
    private var pageNo = 1

    fun observeRefreshable(owner: LifecycleOwner, block: (Boolean) -> Unit) {
        _refreshable.observe(owner, Observer { enable ->
            block.invoke(enable)
        })
    }

    fun observeBanners(owner: LifecycleOwner, block: (List<BannerBean>?) -> Unit) {
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
        loadBannerList()
        loadArticleList()
    }

    private fun loadBannerList() = viewModelScope.launch(Dispatchers.IO) {
        val result = _bannerRepository.getBannerData()
        if (result.isOk()) {
            _banners.postValue(result.getResponse()?.data)
        } else {
            _banners.postValue(null)
        }
    }

    private fun loadArticleList() = viewModelScope.launch(Dispatchers.IO) {
        pageNo = 0
        val result = _articlesRepository.getArticleData(pageNo)
        _refreshable.postValue(false)
        var pair = Pair(true, result.getResponse()?.data?.datas)
        _articles.postValue(pair)
    }

    fun loadMoreArticles() = viewModelScope.launch(Dispatchers.IO) {
        val oldPageNo = pageNo
        val result = _articlesRepository.getArticleData(pageNo++)
        if (result.isOk()) {
            var pair = Pair(false, result.getResponse()?.data?.datas)
            _articles.postValue(pair)
        } else {
            pageNo = oldPageNo
        }
    }

    fun onBannerItemClick(position: Int) {
        var banner = _banners.value?.get(position)

    }
}