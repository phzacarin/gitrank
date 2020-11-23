package com.zacarin.gitrank.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val VISIBLE_ITEMS_THRESHOLD = 1

/**
 * Listener for [RecyclerView] scrolling with "infinite scrolling" behavior.
 */
abstract class InfiniteScrollListener(
    private val layoutManager: LinearLayoutManager,
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()

        if (lastVisibleItemPosition == totalItemCount.minus(VISIBLE_ITEMS_THRESHOLD)) {
            onLoadMore()
        }
    }

    /**
     * Callback for when more items should be loaded.
     */
    abstract fun onLoadMore()
}
