package com.example.getir_bootcamp_final_project.utils

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

// This class adds a drawable divider bottom of the recycler view item
class DividerItemDecorator(
    private val divider: Drawable,
    private val showDividerForLastItem: Boolean = false
): RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val dividerLeft = parent.paddingLeft
        val dividerRight = parent.width - parent.paddingRight
        val includeLastItem = if (!showDividerForLastItem) 1 else 0

        for (i in 0..(parent.childCount-1-includeLastItem)){
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val dividerTop = child.bottom + params.bottomMargin
            val dividerBottom: Int = dividerTop + divider.intrinsicHeight

            divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            divider.draw(c)
        }
    }
}