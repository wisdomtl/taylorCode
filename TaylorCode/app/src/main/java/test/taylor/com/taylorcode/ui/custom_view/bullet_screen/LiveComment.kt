package test.taylor.com.taylorcode.ui.custom_view.bullet_screen

import android.app.Activity
import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams
import androidx.core.util.Pools
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import test.taylor.com.taylorcode.kotlin.wrap_content

object LiveComment {
    /**
     * define how to create live comment view
     */
    lateinit var createView: () -> View

    /**
     * define how to bind data with live comment view
     */
    lateinit var bindView: (Any, View) -> Unit

    /**
     * define how live comments animates
     */
    lateinit var animateView: (View, Rect, Int, Int, (View) -> Unit) -> Unit

    /**
     * the host [Activity] which live comments settle in
     */
    lateinit var activity: Activity

    private lateinit var anchorRect: Rect


    /**
     * the place which live comments show in
     */
    var anchorView: View? = null
        set(value) {
            field = value
            anchorView?.doOnPreDraw {
                anchorRect = Rect()
                value?.getGlobalVisibleRect(anchorRect)
            }
        }

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

    /**
     * the distance between the first lane to the top of anchor view
     */
    var paddingTop: Int = 10
        set(value) {
            field = value.dp
        }

    /**
     * the distance between the last lane to the bottom of anchor view
     */
    var paddingBottom: Int = 10
        set(value) {
            field = value.dp
        }

    /**
     * a pool which holds several live comments view to be reuse
     */
    private lateinit var pool: Pools.SimplePool<View>

    private val onAnimationEnd = { view: View ->
        recycle(view)
    }

    /**
     * the capacity of [pool]
     */
    var poolCapacity: Int = 20
        set(value) {
            field = value
            pool = Pools.SimplePool(value)
        }

    init {
        poolCapacity = 20
        verticalGap = 0
        horizontalGap = 5
        paddingTop = 0
        paddingBottom = 0
    }

    /**
     * show live comment within [anchorView] which belongs to [activity], and bind [data] with it
     */
    fun show(data: Any) {
        requireNotNull(anchorView) { "anchor view must not be null" }
        obtain().also { commentView ->
            bindView(data, commentView)
            commentView.doOnLayout {
                Rect().also { contentViewRect ->
                    activity.contentView?.getGlobalVisibleRect(contentViewRect)
                    val localAnchorRect = anchorRect.relativeTo(contentViewRect)
                    val lane = randomLane(localAnchorRect, commentView.measuredHeight)
                    commentView.layout(localAnchorRect.right, lane, anchorRect.right + commentView.measuredWidth, lane + commentView.measuredHeight)
                }
                animateView(commentView, anchorRect, commentView.measuredWidth, commentView.measuredHeight, onAnimationEnd)
            }
            activity.contentView?.addView(commentView, LayoutParams(wrap_content, wrap_content))
        }
    }

    /**
     * get the lane top by random
     */
    private fun randomLane(anchorRect: Rect, liveCommentHeight: Int): Int {
        return if (overlap == 0f) {
            val lanesHeight = (anchorRect.bottom - paddingBottom) - (anchorRect.top + paddingTop)
            val lanesCapacity = (lanesHeight + verticalGap) / (liveCommentHeight + verticalGap)
            val extraPadding = lanesHeight - liveCommentHeight * lanesCapacity - verticalGap * (lanesCapacity - 1)
            val firstLaneTop = anchorRect.top + paddingTop + extraPadding / 2
            val randomOffset = (0 until lanesCapacity).random() * (liveCommentHeight + verticalGap)
            firstLaneTop +randomOffset
        } else {
            0
        }
    }

    /**
     * obtain a live comment view whether in [pool] or creating a new one
     */
    private fun obtain(): View = pool.acquire() ?: createView()

    /**
     * recycle a live comment view after it gets across the screen
     */
    private fun recycle(view: View) {
        view.detch()
        view.translationX = 0f
        pool.release(view)
    }

    /**
     * find content view of an [Activity]
     */
    private val Activity.contentView: FrameLayout?
        get() = takeIf { !isFinishing && !isDestroyed }?.window?.decorView?.findViewById(android.R.id.content)

    val Int.dp: Int
        get() {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                this.toFloat(),
                Resources.getSystem().displayMetrics
            ).toInt()
        }

    private fun Rect.relativeTo(otherRect: Rect): Rect {
        val relativeLeft = left - otherRect.left
        val relativeTop = top - otherRect.top
        val relativeRight = relativeLeft + right - left
        val relativeBottom = relativeTop + bottom - top
        return Rect(relativeLeft, relativeTop, relativeRight, relativeBottom)
    }

    private fun View?.detch() = this?.parent?.let { it as? ViewGroup }?.also { it.removeView(this) }
}