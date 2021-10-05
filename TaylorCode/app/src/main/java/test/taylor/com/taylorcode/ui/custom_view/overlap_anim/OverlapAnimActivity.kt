package test.taylor.com.taylorcode.ui.custom_view.overlap_anim

import android.animation.ValueAnimator.INFINITE
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import taylor.com.animation_dsl.ValueAnim
import test.taylor.com.taylorcode.kotlin.*
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

    private val urls =
        listOf(
            "https://xiaored.oss-cn-hangzhou.aliyuncs.com/backend/party/hall/01111111111_1628221952063_0_w76h76.png",
            "https://xiaoredpics.neoclub.cn/backend/appearance/defaultAvatar2.png",
            "http://xiaoredpic.neoclub.cn/miaohong/user/custom/avatar/image/test/15209233/e845424c-4778-48a3-b699-6bc5bdc30f52.jpg",
            "http://xiaoredpic.neoclub.cn/miaohong/user/custom/avatar/image/test/15233023/120f44fb-849c-4112-b8db-259331f2fc17.jpg",
            "http://xiaoredpic.neoclub.cn/backend/appearance/default/avatar/male001.png",
            "https://xiaored.oss-cn-hangzhou.aliyuncs.com/backend/party/01111111111_1625363250143_0_w1000h1000.png"
        )

    val contentView by lazy {
        ConstraintLayout {

            container = ConstraintLayout {
                layout_id = "root"
                layout_width = 250
                layout_height = ITEM_DIMENSION
                background_color = "#e8e8e8"
                top_toTopOf = parent_id
                start_toStartOf = parent_id

                OverlapCarouselAnim(this).apply {
                    duration = ANIM_DURATION
                    overlapDp = OVERLAP_GAP
                    dimensionDp = ITEM_DIMENSION
                    interval = 2000
                    onBindItemView = { context,index, url ->
                        StrokeImageView(context).apply {
                            scaleType = scale_center_crop
                            roundedAsCircle = true
                            load(url)
                        }
                    }
                }.start(urls)
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
}