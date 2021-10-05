package test.taylor.com.taylorcode.ui.custom_view.overlap_anim

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import taylor.com.animation_dsl.Anim
import taylor.com.animation_dsl.ValueAnim
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.coroutine.countdown2
import test.taylor.com.taylorcode.ui.animation_dsl.valueAnim
import java.util.*

/**
 * a helper class to layout views overlap with each other,
 * and auto scroll as carousel
 */
class OverlapCarouselAnim(val container: ConstraintLayout) {

    /**
     * all view to animate is in this queue
     */
    private val carouselViews = LinkedList<View>()

    /**
     * define how to create view
     */
    var onBindItemView: ((Context, Int, String) -> View?)? = null

    /**
     * how long is the animation
     */
    var duration: Long = 300

    /**
     * how often to refresh
     */
    var interval: Long = 2 * duration

    /**
     * the overlap degree defined in dp
     */
    var overlapDp: Int = 0

    /**
     * the width and height of views
     */
    var dimensionDp: Int = 50

    /**
     * a [ValueAnim] generate value from 0% to 100%
     */
    private val anim by lazy {
        valueAnim {
            values = floatArrayOf(0f, 1f)
            action = { value -> updateValue(value, overlapDp) }
            duration = duration
            onEnd = { _, anim ->
                moveEndToStart()
                resetAnim(anim)
            }
        }
    }

    /**
     * start carousel animation according to the [urls] which will be turned into image showing in [ImageView]
     */
    fun start(urls: List<String>) {
        require(urls.isNotEmpty() && urls.size >= 3) { "at least, carousel item is 3" }
        container.layout_width = (urls.size - 1) * dimensionDp - (urls.size - 2) * (dimensionDp - overlapDp)
        urls.forEachIndexed { index, url ->
            container.apply {
                onBindItemView?.invoke(context, index, url)?.apply {
                    layout_id = "carousel$index"
                    layout_width = dimensionDp
                    layout_height = dimensionDp
                    if (index == 0) {
                        alpha = 0f
                        scaleX = 0f
                        scaleY = 0f
                        start_toStartViewOf = container
                        top_toTopViewOf = container
                        bottom_toBottomViewOf = container
                    } else {
                        start_toStartViewOf = carouselViews.lastOrNull()
                        top_toTopViewOf = carouselViews.lastOrNull()
                        bottom_toBottomViewOf = carouselViews.lastOrNull()
                        if (index != 1) margin_start = overlapDp
                    }
                }?.also {
                    addView(it)
                    carouselViews.add(it)
                }
            }
        }

        countdown2(Long.MAX_VALUE, interval, Dispatchers.Main) {
            anim.start()
        }.launchIn(MainScope())
    }

    private fun resetAnim(anim: Anim) {
        (anim as ValueAnim).apply {
            values = floatArrayOf(0f, 1f)
            action = { value -> updateValue(value, overlapDp) }
        }
    }

    private fun moveEndToStart() {
        (1..carouselViews.size - 2).forEach { index ->
            carouselViews[index].also { it.lastTranslationX = it.translationX }
        }
        val lastView = carouselViews.pollLast()
        carouselViews.addFirst(lastView)
        container.removeView(lastView)
        container.addView(
            lastView?.apply {
                start_toStartOf = parent_id
                margin_start = 0
                translationX = 0f
                lastTranslationX = 0f
            }, 0
        )
    }

    private fun updateValue(value: Any, overlap: Int) {
        val degree = value as Float
        val size = carouselViews.size
        carouselViews.forEachIndexed { index, view ->
            when (index) {
                0 -> view.apply {
                    alpha = degree
                    scaleX = degree
                    scaleY = degree
                }
                size - 1 -> view.apply {
                    alpha = 1 - degree
                    scaleX = 1 - degree
                    scaleY = 1 - degree
                }
                else -> view.translationX = view.lastTranslationX + overlap.dp * degree
            }
        }
    }
}

/**
 * keep last [View.translationX] in View's tag,
 * if doing translation animation several time in one view, this attribute will come into help.
 * every translation animation should be started with the last translation value, or view will always be animated from the scratch.
 */
var View.lastTranslationX: Float
    get() {
        return getTag("lastTransX".hashCode()) as? Float ?: 0f
    }
    set(value) {
        setTag("lastTransX".hashCode(), value)
    }
