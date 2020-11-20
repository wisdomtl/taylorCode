package test.taylor.com.taylorcode.ui.custom_view.bullet_screen

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.ui.custom_view.bullet_screen.LiveComment.dp

class LiveCommentView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    /**
     * whether live comments overlap with each other
     */
    var overlap = 0f

    /**
     * the gap between different lane
     */
    var verticalGap: Int = 5
        set(value) {
            field = value.dp
        }

    /**
     * the gap between different live comments in one lane
     */
    var horizontalGap: Int = 5
        set(value) {
            field = value.dp
        }

    init {
        verticalGap = 5
        horizontalGap = 5
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        (0 until childCount).map { getChildAt(it) }.forEach loop@{ child ->
            val left = 500
            val top = getRandomLane(child.measuredHeight)
            child.layout(left, top, left + child.measuredWidth, top + child.measuredHeight)
        }
    }

    /**
     * get the lane top by random
     */
    private fun getRandomLane(commentHeight: Int): Int {
        return if (overlap == 0f) {
            val lanesHeight = measuredHeight - paddingTop - paddingBottom
            val lanesCapacity = (lanesHeight + verticalGap) / (commentHeight + verticalGap)
            val extraPadding = lanesHeight - commentHeight * lanesCapacity - verticalGap * (lanesCapacity - 1)
            val firstLaneTop = paddingTop + extraPadding / 2
            val randomOffset = (0 until lanesCapacity).random() * (commentHeight + verticalGap)
            firstLaneTop + randomOffset
        } else {
            0
        }
    }
}