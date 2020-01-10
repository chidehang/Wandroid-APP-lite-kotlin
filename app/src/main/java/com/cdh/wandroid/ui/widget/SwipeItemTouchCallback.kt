package com.cdh.wandroid.ui.widget

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.cdh.wandroid.R
import kotlin.math.abs

/**
 * Created by chidehang on 2020-01-09
 */
class SwipeItemTouchCallback : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState != ItemTouchHelper.ACTION_STATE_SWIPE) {
            return
        }

        var menu = viewHolder.itemView.findViewById<View>(R.id.swipe_item_menu)
        if (menu == null) {
            return
        }

        val menuWidth = menu.measuredWidth
        var offsetX = abs(dX).toInt()
        if (offsetX > menuWidth) {
            offsetX = menuWidth
        }
        viewHolder.itemView.scrollTo(-offsetX, 0)
    }
}
