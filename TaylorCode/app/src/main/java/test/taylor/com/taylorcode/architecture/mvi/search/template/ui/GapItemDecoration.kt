package com.bilibili.studio.search.template.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GapItemDecoration(private val leftGap: Int, private val rightGap: Int, private val bottomGap: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(leftGap, 0, rightGap, bottomGap)
    }
}
