package test.taylor.com.taylorcode.ui.custom_view.overlap_anim

import android.animation.ValueAnimator.INFINITE
import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnPreDraw
import androidx.core.view.updateLayoutParams
import taylor.com.animation_dsl.ValueAnim
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.StrokeImageView
import test.taylor.com.taylorcode.ui.animation_dsl.animSet
import test.taylor.com.taylorcode.ui.performance.load
import java.util.*

class OverlapAnimActivity : AppCompatActivity() {

    private val ITEM_DIMENSION = 50

    private val OVERLAP_GAP = 10
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
            "https://xiaored.oss-cn-hangzhou.aliyuncs.com/backend/party/01111111111_1625363250143_0_w1000h1000.png",
            "http://xiaoredpic.neoclub.cn/backend/appearance/default/avatar/male001.png"
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

//                OverlapCarouselAnim(this).apply {
//                    duration = ANIM_DURATION
//                    overlapDp = OVERLAP_GAP
//                    dimensionDp = ITEM_DIMENSION
//                    interval = 2000
////                    onCreateItemView = { context ->
////                        StrokeImageView(context).apply {
////                            scaleType = scale_center_crop
////                            roundedAsCircle = true
////                        }
////                    }
////                    onBindItemView = { view, index, url ->
////                        Log.v("ttaylor","index=$index,url=${url}")
////                        url?.let { (view as? ImageView)?.load(url) }
////                    }
//                }.start(urls, urls.size > 4, 4)
            }

            tv = MyTextView(context).apply {
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
                                tv.left = translationX.toInt()
                                tv.alpha = translationX/200
                            }
                        }
                    }.start()
                }
            }.also { addView(it) }

            MyTextView(context).apply {
                layout_id = "oneShot"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                textColor = "#ff00ff"
                text = "one shot"
                gravity = gravity_center
                onClick = {
//                    repeatAnim.start()
                    val anim = AnimationUtils.loadAnimation(context, R.anim.slide_left_in)
                    it.startAnimation(anim)
                }
                top_toBottomOf = "tvChange"
                margin_top = 20
                center_horizontal = true
            }.also { addView(it) }
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

class MyTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr){
    override fun invalidate() {
        super.invalidate()
        Log.v("ttaylor","[value animation] invalidate()")
    }

    override fun invalidateOutline() {
        super.invalidateOutline()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        Log.v("ttaylor","[value animation] draw()")
    }

    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, r, b)
        Log.v("ttaylor","[value animation] layout() l=$l,t=$t,r=$r,b=$b")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.v("ttaylor","[value animation] onLayout() l=$left,t=$top,r=$right,b=$bottom")
    }



}