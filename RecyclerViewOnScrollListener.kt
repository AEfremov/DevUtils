package com.ltech.moneyto.core.components

import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
*   val scrollListener = object : RecyclerViewOnScrollListener(layoutManager) {
*    override fun onLoadMore() {
*        //
*    }
*   }
*   view.addOnScrollListener(scrollListener)
*/
abstract class RecyclerViewOnScrollListener(private val mLinearLayoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    private var loading = true

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            loading = true
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView!!.childCount
        val totalItemCount = mLinearLayoutManager.itemCount
        val firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition()

        if (loading && visibleItemCount + firstVisibleItem == totalItemCount) {
            loading = false

            onLoadMore()
        }
    }

    abstract fun onLoadMore()
}