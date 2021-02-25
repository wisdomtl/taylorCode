package test.taylor.com.taylorcode.ui

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.constraintlayou_activity2.*
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.line_feed_layout.LineFeedLayout
import test.taylor.com.taylorcode.util.DimensionUtil

class ConstraintLayoutActivity2 : AppCompatActivity() {
    val id1 = 1
    val id2 = 2
    val id3 = 3
    val id4 = 4
    val id5 = 5
    val tags = listOf(
        "dkfj",
        "dfsf",
        "dsfds",
        "dfsf",
        "dsfds",
        "dfsf",
        "dsfds",
        "dfsf",
        "dsfds",
        "dfsf",
        "dsfds",
        "dfsf",
        "dsfds",
        "dfsf",
        "dsfds",
        "dfsf",
        "dsfds"
    )

    val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constraintlayou_activity2)
//        build()
        buildChain()
        tvShadow.apply {
            background_drawable_state_list = listOf(
                intArrayOf(state_enable) to shape {
                    corner_radius = 10
                    solid_color = "#0000ff"
                },
                intArrayOf(state_disable) to shape {
                    corner_radius = 10
                    solid_color = "#ffff00"
                }
            )

        }
        tvNormal?.onClick = {
            tvShadow.isEnabled = !tvShadow.isEnabled
        }
    }

    private fun build() {
        TextView(this).apply {
            id = id1
            text = "abd"
            textSize = DimensionUtil.dp2px(20.0).toFloat()
            setBackgroundColor(Color.parseColor("#00ff00"))
            setTextColor(Color.parseColor("#404852"))
            layoutParams =
                ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT).apply {
                    topToTop = ConstraintSet.PARENT_ID
                    startToStart = ConstraintSet.PARENT_ID
                }
        }.also { croot2?.addView(it) }

        TextView(this).apply {
            id = id2
            text = "ab222k"
            textSize = DimensionUtil.dp2px(20.0).toFloat()
            setBackgroundColor(Color.parseColor("#ffff00"))
            setTextColor(Color.parseColor("#404852"))
            layoutParams =
                ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT).apply {
                    topToBottom = id1
                    startToStart = ConstraintSet.PARENT_ID
                }
        }.also { croot2?.addView(it) }

        LineFeedLayout(this).apply {
            layoutParams =
                ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT).apply {
                    topToBottom = id2
                    startToStart = ConstraintSet.PARENT_ID
                }
            setBackgroundColor(Color.parseColor("#38ff00"))
            tags?.forEach { tag ->
                TextView(this@ConstraintLayoutActivity2).apply {
                    text = tag
                    textSize = 20f
                    setTextColor(Color.parseColor("#448976"))
                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                        rightMargin = DimensionUtil.dp2px(20.0)
                        topMargin = DimensionUtil.dp2px(10.0)
                    }
                }.also { addView(it) }
            }
        }.also { croot2?.addView(it) }
    }

    private fun buildChain() {
        val views = mutableListOf<View>()
        (0..3).forEachIndexed { index, i ->
            TextView {
                layout_id = "tddv$index"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 16f
                textColor = "#3F4658"
                text = "$index   $index"
                gravity = gravity_center
                fontFamily = R.font.pingfang
                background_color = "#00ffff"
            }.also {
                views.add(it)
                croot2?.addView(it)
            }
        }
        croot2.buildChain(start, views, null, horizontal,0,0,0)
    }
}