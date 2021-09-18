package test.taylor.com.taylorcode.ui.custom_view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(private val horizontalInterval: Int, private val verticalInterval: Int)
    : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(horizontalInterval / 2, 0, horizontalInterval / 2, verticalInterval)
    }
}
