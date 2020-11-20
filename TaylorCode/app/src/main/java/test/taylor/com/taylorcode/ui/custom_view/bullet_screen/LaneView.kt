package test.taylor.com.taylorcode.ui.custom_view.bullet_screen

import android.content.Context
import android.util.ArrayMap
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.ui.custom_view.bullet_screen.LiveComment.dp
import java.util.*

/**
 * [LaneView] is used to show live comments all over the screen
 */
class LaneView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {
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

    /**
     * [Lane] is sorted by it's top coordinate in [LaneView]
     */
    private var laneMap = ArrayMap<Int, Lane>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }

    override fun addView(child: View?) {
        child ?: return
        /**
         * measure child view
         */
        val w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        val h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        measureChild(child, w, h)
        /**
         * add child view
         */
        super.addView(child)
        val left = measuredWidth
        val top = getRandomTop(child.measuredHeight)
        /**
         * layout child view just behind the most right of [LaneView]
         */
        child.layout(left, top, left + child.measuredWidth, top + child.measuredHeight)
        /**
         * put child view into [Lane]
         */
        laneMap[top]?.add(child) ?: run {
            Lane(measuredWidth).also {
                it.add(child)
                laneMap[top] = it
            }
        }
    }

    /**
     * get the lane top by random
     */
    private fun getRandomTop(commentHeight: Int): Int {
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

    /**
     * a  [Lane] is used to show live comments in sequence without overlapping
     */
    inner class Lane(var laneWidth: Int) : CoroutineScope by CoroutineScope(SupervisorJob() + Dispatchers.Default) {
        private var viewQueue = LinkedList<View>()
        private var onLayoutChangeListener = LayoutListener()
        private var blockShow = true

        /**
         * launch a Coroutine to pop child view infinitely
         */
        init {
            launch {
                while (true) {
                    val currentView = viewQueue.poll()
                    currentView?.apply {
                        addOnLayoutChangeListener(onLayoutChangeListener.apply { width = currentView.measuredWidth })
                        postOnAnimation {
                            animate()
                                .setDuration(3000)
                                .setInterpolator(LinearInterpolator())
                                .setUpdateListener {
                                    val value = it.animatedFraction
                                    val left = (laneWidth - value * (laneWidth + measuredWidth)).toInt()
                                    layout(left, top, left + measuredWidth, top + measuredHeight)
                                }
                                .start()
                        }
                        blockShow = true
                    }
                    while (blockShow) {
                    }
                    currentView?.removeOnLayoutChangeListener(onLayoutChangeListener)
                }
            }
        }

        inner class LayoutListener : OnLayoutChangeListener {
            var width: Int = 0
            override fun onLayoutChange(
                v: View?,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                if (laneWidth - left > width + horizontalGap) {
                    blockShow = false
                }
            }

        }

        fun add(view: View) {
            viewQueue.addLast(view)
        }
    }
}