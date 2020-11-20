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

class LiveCommentView2
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
        horizontalGap = 50
    }

    private var laneMap = ArrayMap<Int, Lane>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }

    override fun addView(child: View?) {
        child ?: return
        val w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        val h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        measureChild(child, w, h)
        super.addView(child)
        val originLeft = measuredWidth
        val top = getRandomTop(child.measuredHeight)
        child.layout(originLeft, top, originLeft + child.measuredWidth, top + child.measuredHeight)
        child.animate()
            ?.setDuration(3000)
            ?.setInterpolator(LinearInterpolator())
            ?.setUpdateListener {
                val value = it.animatedFraction
                val left = (measuredWidth - value * (measuredWidth + child.measuredWidth)).toInt()
                child.layout(left, top, left + child.measuredWidth, top + child.measuredHeight)
            }
        laneMap[top]?.add(child) ?: run {
            Lane(measuredWidth).also {
                it.add(child)
                laneMap[top] = it
                it.fire()
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

    inner class Lane(var laneWidth: Int) : CoroutineScope by CoroutineScope(SupervisorJob() + Dispatchers.Default) {

        var viewQueue = LinkedList<View>()

        private var onLayoutChangeListener = LayoutListener()

        private var blockShow = true

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

        fun fire() {
            launch {
                while (true) {
                    val currentView = viewQueue.poll()
                    currentView?.apply {
                        addOnLayoutChangeListener(onLayoutChangeListener.apply { width = currentView.measuredWidth  })
                        postOnAnimation {
                            animate().start()
                        }
                        blockShow = true
                    }
                    while (blockShow) {
                    }
                    currentView?.removeOnLayoutChangeListener(onLayoutChangeListener)
                }
            }
        }

        fun add(view: View) {
            viewQueue.addLast(view)
        }
    }


}