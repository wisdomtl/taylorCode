package test.taylor.com.taylorcode.cover_by_fragment

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.activitystack.Param
import test.taylor.com.taylorcode.architecture.flow.lifecycle.SubFragment2
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.extension.decorView
import test.taylor.com.taylorcode.kotlin.extension.onVisibilityChange

class CoveredByFragmentActivity:AppCompatActivity(),Param {

    private lateinit var tv:TextView
    private lateinit var viewGroup1:ViewGroup
    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

             TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                text = "show fragment"
                fontFamily = R.font.pingfang
                gravity = gravity_center
                top_toTopOf = parent_id
                center_horizontal = true
                onClick = {
                    showFragment()
                }

            }

            TextView {
                layout_id = "tvChange3"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                text = "show add view"
                fontFamily = R.font.pingfang
                gravity = gravity_center
                top_toBottomOf = "tvChange"
                center_horizontal = true
                margin_top =  30
                onClick = {
                    decorView?.addView(tv1)
                }
            }

            tv = TextView {
                layout_id = "tvChange2"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                text = "save"
                fontFamily = R.font.pingfang
                gravity = gravity_center
                center_horizontal = true
                bottom_toBottomOf = parent_id
                margin_bottom = 20
                tag = "tvvvvv"
            }

            viewGroup1 = FrameLayout {
                layout_id = "container"
                layout_width = match_parent
                layout_height = 300
                bottom_toBottomOf = parent_id
            }
        }
    }

    private fun showFragment() {
        supportFragmentManager.beginTransaction()
            .add("container".toLayoutId(), fragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        tv.onVisibilityChange(listOf(viewGroup1)) { view, isVisible ->
            Log.w("ttaylor", "[test2022] CoveredByFragmentActivity.onCreate[view, isVisible($isVisible)]: ")
        }
    }

    private val tv1  by lazy {
        TextView {
            layout_id = "tvChange33"
            layout_width = match_parent
            layout_height = match_parent
            textSize = 50f
            text = "save"
            fontFamily = R.font.pingfang
            gravity = gravity_center
            background_color = "#00f00f"
        }
    }

    private val fragment by lazy {
        SubFragment2()
    }

    override fun onBackPressed() {
        supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commit()
        decorView?.removeView(tv1)
    }

    override val paramMap: Map<String, Any>
        get() = mapOf(
            "type" to 1
        )
}