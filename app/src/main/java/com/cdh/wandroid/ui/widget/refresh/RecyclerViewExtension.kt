package com.cdh.wandroid.ui.widget.refresh

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * Created by chidehang on 2020-01-03
 */
fun RecyclerView.setRefreshListener(listener: OnRefreshListener) {
    addOnScrollListener(object: RecyclerView.OnScrollListener() {
        var lastVisiblePosition: Int = 0
        val parentLayout = this@setRefreshListener.parent

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager
            when(layoutManager) {
                is LinearLayoutManager -> {
                    lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                }
                is GridLayoutManager -> {
                    lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                }
                is StaggeredGridLayoutManager -> {
                    val array = IntArray(layoutManager.spanCount)
                    layoutManager.findLastVisibleItemPositions(array)
                    lastVisiblePosition = findMax(array)
                }
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisiblePosition+1 == recyclerView.adapter?.itemCount) {
                if (parentLayout is SwipeRefreshLayout) {
                    if (!parentLayout.isRefreshing) {
                        listener.onLoadMore()
                    }
                } else {
                    listener.onLoadMore()
                }
            }
        }
    })

    val swipeRefreshLayout = this.parent
    if (swipeRefreshLayout is SwipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener {
            listener.onRefresh()
        }
    }
}

fun findMax(array: IntArray): Int {
    var max = array[0]
    for (i in array.indices) {
        val value = array[i]
        if (value > max) {
            max = value
        }
    }
    return max
}