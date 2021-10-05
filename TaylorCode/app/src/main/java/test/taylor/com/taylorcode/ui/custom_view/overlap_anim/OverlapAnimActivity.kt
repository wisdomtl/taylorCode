package test.taylor.com.taylorcode.ui.custom_view.overlap_anim

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.coroutine.countdown2
import test.taylor.com.taylorcode.ui.StrokeImageView
import test.taylor.com.taylorcode.ui.animation_dsl.animSet
import test.taylor.com.taylorcode.ui.performance.load
import java.util.*

class OverlapAnimActivity : AppCompatActivity() {

    private val ITEM_DIMENSION = 50

    private val OVERLAP_GAP = 35
    private val ANIM_DURATION = 2000L

    private var carouselViews = LinkedList<View>()

    private lateinit var tv: TextView

    val contentView by lazy {
        ConstraintLayout {

            ConstraintLayout {
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
            contentView.apply {
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
                onStart = {
                    initWidth = firstView.width
                    initHeight = firstView.height
                }
            }
            delay = 1000
        }.start()
    }

    private fun startCarousel() {
        countdown2(Long.MAX_VALUE, 2000, Dispatchers.Main) {
            var startTranslationX = 0
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
                }

                onEnd = {
                    (1..carouselViews.size - 2).forEach { index ->
                        carouselViews[index].also { it.lastTranslationX = it.translationX }
                    }
                    val lastView = carouselViews.pollLast()
                    carouselViews.addFirst(lastView)
                    lastView.apply {
                        start_toStartOf = parent_id
                    }
                }
                delay = 1000
            }.start()
        }.launchIn(MainScope())
    }
}

var View.lastTranslationX: Float
    get() {
        return getTag("lastTransX".hashCode()) as? Float ?: 0f
    }
    set(value) {
        setTag("lastTransX".hashCode(), value)
    }