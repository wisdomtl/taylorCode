package test.taylor.com.taylorcode.ui.custom_view.bullet_screen

import android.animation.Animator
import android.content.Context
import android.content.res.Resources
import android.util.ArrayMap
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.view.animation.LinearInterpolator
import androidx.core.util.Pools
import test.taylor.com.taylorcode.ui.custom_view.bullet_screen.LaneView.Mode.AsyncMode
import test.taylor.com.taylorcode.ui.custom_view.bullet_screen.LaneView.Mode.SyncMode
import java.util.*

/**
 * [LaneView] is used to show live comments all over the screen
 */
class LaneView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewGroup(context, attrs, defStyleAttr) {
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
     * how one live comment should sync with others
     */
    var mode: Mode = AsyncMode

    /**
     * how long a live comment will show in screen
     */
    var duration = 4000L

    /**
     * define how to create live comment view
     */
    lateinit var createView: () -> View

    /**
     * define how to bind data with live comment view
     */
    lateinit var bindView: (Any, View) -> Unit

    /**
     * a pool which holds several live comments view to be reuse
     */
    private lateinit var pool: Pools.SimplePool<View>

    /**
     * the capacity of [pool]
     */
    var poolCapacity: Int = 20
        set(value) {
            field = value
            pool = Pools.SimplePool(value)
        }

    /**
     * [Lane] is sorted by it's top coordinate in [LaneView]
     */
    private var laneMap = ArrayMap<Int, Lane>()

    init {
        verticalGap = 5
        horizontalGap = 5
        poolCapacity = 20
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }

    fun show(datas: List<Any>) {
        datas.forEach { show(it) }
    }

    fun show(data: Any) {
        val child = obtain()

        bindView(data, child)

        /**
         * measure child view
         */
        val w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        val h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        measureChild(child, w, h)
        /**
         * add child view
         */
        addView(child)
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
                it.showNext()
            }
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
        view.detach()
        pool.release(view)
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
     * a [Lane] is used to show live comments in sequence without overlapping
     */
    inner class Lane(var laneWidth: Int) {
        private var viewQueue = LinkedList<View>()
        private var currentView: View? = null

        //a valve to control live comments showing without overlapping
        private var blockShow = false
        private val onLayoutChangeListener =
            OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                Log.e(
                    "ttaylor",
                    "tag=, Lane.()  laneWidth=${laneWidth} ,left=${left}, measureWidth=${v?.measuredWidth} ,horizontalGap=${horizontalGap}"
                )
                if (laneWidth - left > v?.measuredWidth ?: 0 + horizontalGap) {
                    blockShow = false
                    Log.w("ttaylor", "onLayoutChange() blockShow=${blockShow}")
                    showNext()
                }
            }

        fun showNext() {
            Log.w("ttaylor", "showNext1() blockShow=${blockShow}")
            if (blockShow) return
            currentView?.removeOnLayoutChangeListener(onLayoutChangeListener)
            currentView = viewQueue.poll()
            currentView?.let { view ->
                view.addOnLayoutChangeListener(onLayoutChangeListener)
                val duration = when (mode) {
                    is SyncMode -> {
                        val distance = laneWidth + view.measuredWidth
                        val speed = laneWidth.toFloat() / duration
                        (distance / speed).toLong()
                    }
                    is AsyncMode -> {
                        duration
                    }
                }
                view.animate()
                    .setDuration(duration)
                    .setInterpolator(LinearInterpolator())
                    .setUpdateListener {
                        val value = it.animatedFraction
                        val left = (laneWidth - value * (laneWidth + view.measuredWidth)).toInt()
                        view.layout(left, view.top, left + view.measuredWidth, view.top + view.measuredHeight)
                    }
                    .addListener { onEnd = { recycle(view) } }
                    .start()
                blockShow = true
                Log.w("ttaylor", "showNext2() blockShow=${blockShow}")
            }
        }

        fun add(view: View) {
            viewQueue.addLast(view)
            showNext()
        }
    }

    //<editor-fold desc="helper function">
    private fun View?.detach() = this?.parent?.let { it as? ViewGroup }?.also { it.removeView(this) }

    val Int.dp: Int
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()

    fun ViewPropertyAnimator.addListener(action: AnimatorListenerBuilder.() -> Unit): ViewPropertyAnimator {
        AnimatorListenerBuilder().apply(action).let { builder ->
            setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                    animation?.let { builder.onRepeat?.invoke(animation) }
                }

                override fun onAnimationEnd(animation: Animator?) {
                    animation?.let { builder.onEnd?.invoke(animation) }
                }

                override fun onAnimationCancel(animation: Animator?) {
                    animation?.let { builder.onCancel?.invoke(animation) }
                }

                override fun onAnimationStart(animation: Animator?) {
                    animation?.let { builder.onStart?.invoke(animation) }
                }

            })
        }
        return this
    }

    class AnimatorListenerBuilder {
        var onRepeat: ((Animator) -> Unit)? = null
        var onEnd: ((Animator) -> Unit)? = null
        var onCancel: ((Animator) -> Unit)? = null
        var onStart: ((Animator) -> Unit)? = null
    }
    //</editor-fold>

    sealed class Mode() {
        object SyncMode : Mode()
        object AsyncMode : Mode()
    }
}
