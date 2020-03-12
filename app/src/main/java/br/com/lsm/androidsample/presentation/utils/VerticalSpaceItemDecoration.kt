package br.com.lsm.androidsample.presentation.utils

import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class VerticalSpaceItemDecoration(private val verticalSpaceInDp: Int) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val resources = view.resources
        val spaceInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            verticalSpaceInDp.toFloat(),
            resources.displayMetrics
        )
        outRect.bottom = spaceInPixels.toInt()
    }

}