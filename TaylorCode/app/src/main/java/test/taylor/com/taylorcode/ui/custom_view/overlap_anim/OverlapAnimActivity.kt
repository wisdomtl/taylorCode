package test.taylor.com.taylorcode.ui.custom_view.overlap_anim

import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.RESTART
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import taylor.com.animation_dsl.ValueAnim
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.coroutine.countdown2
import test.taylor.com.taylorcode.ui.StrokeImageView
import test.taylor.com.taylorcode.ui.animation_dsl.animSet
import test.taylor.com.taylorcode.ui.performance.load
import java.util.*

class OverlapAnimActivity : AppCompatActivity() {

    private val ITEM_DIMENSION = 50

    private val OVERLAP_GAP = 35
    private val ANIM_DURATION = 1000L

    private var carouselViews = LinkedList<View>()

    private lateinit var tv: TextView

    private lateinit var container: ConstraintLayout

    val contentView by lazy {
        ConstraintLayout {

            container = ConstraintLayout {
                layout_id = "root"
                layout_width = 250
                layout_height = ITEM_DIMENSION
                background_color = "#e8e8e8"
                top_toTopOf = parent_id
                start_toStartOf = parent_id

                StrokeImageView(context).apply {
                    layout_id = "ivOverlapHead"
                    layout_width = ITEM_DIMENSION
                    layout_height = ITEM_DIMENSION
                    scaleType = scale_center_crop
                    start_toStartOf = parent_id
                    align_vertical_to = parent_id
                    roundedAsCircle = true
                    tag = 0
                    load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fd.lanrentuku.com%2Fdown%2Fpng%2F0904%2Four_earth%2Four_earth_04.png&refer=http%3A%2F%2Fd.lanrentuku.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635996869&t=022629dfaced5d85de630eb12b9296a9")
                    alpha = 0f
                    scaleX = 0f
                    scaleY = 0f
                }.also {
                    addView(it)
                    carouselViews.add(it)
                }
            }

            tv = TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                textColor = "#000000"
                text = "save"
                gravity = gravity_center
                center_horizontal = true
                center_vertical = true
                onClick = {
                    animSet {
                        anim {
                            values = floatArrayOf(0f, 200f)
                            action = {
                                val translationX = it as Float
                                tv.translationX = translationX
                            }
                        }
                    }.start()
                }
            }

            TextView {
                layout_id = "oneShot"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                textColor = "#ff00ff"
                text = "one shot"
                gravity = gravity_center
                onClick = {
                    repeatAnim.start()
                }
                top_toBottomOf = "tvChange"
                margin_top = 20
                center_horizontal = true
            }
        }
    }

    /**
     * case: repeat anim with accumulated value
     */
    private val repeatAnim by lazy {
        animSet {
            anim {
                values = floatArrayOf(0f, 100f)
                action = {
                    val degree = it as Float
                    tv.translationX = degree
                }
                repeatCount = INFINITE
                onRepeat = { _, anim ->
                    tv.lastTranslationX = tv.translationX
                    (anim as ValueAnim).apply {
                        action = {
                            val degree = it as Float
                            tv.translationX = tv.lastTranslationX + degree
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        lifecycleScope.launchWhenCreated {
            showOverlapAnimView(
                listOf(
                    "https://xiaored.oss-cn-hangzhou.aliyuncs.com/backend/party/hall/01111111111_1628221952063_0_w76h76.png",
                    "https://xiaoredpics.neoclub.cn/backend/appearance/defaultAvatar2.png",
                    "http://xiaoredpic.neoclub.cn/miaohong/user/custom/avatar/image/test/15209233/e845424c-4778-48a3-b699-6bc5bdc30f52.jpg",
                    "http://xiaoredpic.neoclub.cn/miaohong/user/custom/avatar/image/test/15233023/120f44fb-849c-4112-b8db-259331f2fc17.jpg",
                    "http://xiaoredpic.neoclub.cn/backend/appearance/default/avatar/male001.png"
                )
            )
        }

    }

    private fun showOverlapAnimView(urls: List<String>) {
        urls.forEachIndexed { index, url ->
            container.apply {
                layout_width = urls.size * ITEM_DIMENSION - (urls.size - 1) * (ITEM_DIMENSION - OVERLAP_GAP)
                StrokeImageView(context).apply {
                    layout_id = "ivOverlap$index"
                    layout_width = ITEM_DIMENSION
                    layout_height = ITEM_DIMENSION
                    scaleType = scale_center_crop
                    start_toStartViewOf = carouselViews.last
                    top_toTopViewOf = carouselViews.last
                    bottom_toBottomViewOf = carouselViews.last
                    roundedAsCircle = true
                    tag = index + 1
                    if (index != 0) margin_start = OVERLAP_GAP
                    load(url)
                }.also {
                    addView(it)
                    carouselViews.add(it)
                }
            }
        }

        startCarousel()
//        testAnimAndRelativePositionRelationship()
    }

    /**
     * case: the relative position wont change when doing animation
     */
    private fun testAnimAndRelativePositionRelationship() {
        val firstView = carouselViews[1]
        var initWidth: Int = 0
        var initHeight: Int = 0
        animSet {
            anim {
                values = floatArrayOf(1f, 0f)
                action = {
                    firstView.updateLayoutParams {
                        width = (initWidth * (it as Float)).toInt()
                        height = (initHeight * (it as Float)).toInt()
                    }
                }
                duration = 1000
                onStart = { _, _ ->
                    initWidth = firstView.width
                    initHeight = firstView.height
                }
            }
            delay = 1000
        }.start()
    }

    private fun startCarousel() {
        countdown2(Long.MAX_VALUE, ANIM_DURATION * 2, Dispatchers.Main) {
        oneRound()
        }.launchIn(MainScope())
    }

    private val anim by lazy {
        animSet {
            anim {
                values = floatArrayOf(0f, 1f)
                action = { value ->
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
                            else -> view.translationX = view.lastTranslationX + OVERLAP_GAP.dp * degree
                        }
                    }
                }
                duration = ANIM_DURATION
                repeatCount = INFINITE
                repeatMode = RESTART
                delay = 1000

                onRepeat = { _, anim ->
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
            }
        }
    }

    private fun oneRound() {
        animSet {
            anim {
                values = floatArrayOf(0f, 1f)
                action = { value ->
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
                            else -> view.translationX = view.lastTranslationX + OVERLAP_GAP.dp * degree
                        }
                    }
                }
                duration = ANIM_DURATION
//                repeatCount = INFINITE
//                repeatMode = RESTART
//                delay = 1000

                onEnd = { _, anim ->
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
            }
        }.start()

//        (anim.getAnim(0) as ValueAnim).apply {
//            action = { value ->
//                val degree = value as Float
//                val size = carouselViews.size
//                carouselViews.forEachIndexed { index, view ->
//                    when (index) {
//                        0 -> view.apply {
//                            alpha = degree
//                            scaleX = degree
//                            scaleY = degree
//                        }
//                        size - 1 -> view.apply {
//                            alpha = 1 - degree
//                            scaleX = 1 - degree
//                            scaleY = 1 - degree
//                        }
//                        else -> view.translationX = view.lastTranslationX + OVERLAP_GAP.dp * degree
//                    }
//                }
//            }
//
//            onEnd = { _, _ ->
//                (1..carouselViews.size - 2).forEach { index ->
//                    carouselViews[index].also { it.lastTranslationX = it.translationX }
//                }
//                val lastView = carouselViews.pollLast()
//                carouselViews.addFirst(lastView)
//                container.removeView(lastView)
//                container.addView(
//                    lastView?.apply {
//                        start_toStartOf = parent_id
//                        margin_start = 0
//                        translationX = 0f
//                        lastTranslationX = 0f
//                    }, 0
//                )
//            }
//        }
    }
}

var View.lastTranslationX: Float
    get() {
        return getTag("lastTransX".hashCode()) as? Float ?: 0f
    }
    set(value) {
        setTag("lastTransX".hashCode(), value)
    }