package com.cdh.wandroid.ui

import androidx.lifecycle.*
import com.cdh.wandroid.model.BannerRepository
import com.cdh.wandroid.model.bean.BannerBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by chidehang on 2020-01-01
 */
class HomeViewModel : ViewModel() {

    val _banners = MutableLiveData<List<BannerBean>>()

    private val _bannerRepository = BannerRepository()

    fun observeBanners(owner: LifecycleOwner, block: (List<BannerBean>?) -> Unit) {
        _banners.observe(owner, Observer { banners ->
            block.invoke(banners)
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

    private fun loadArticleList() {
    }

    fun loadMoreArticles() {
    }

    fun onBannerItemClick(position: Int) {
        var banner = _banners.value?.get(position)

    }
}