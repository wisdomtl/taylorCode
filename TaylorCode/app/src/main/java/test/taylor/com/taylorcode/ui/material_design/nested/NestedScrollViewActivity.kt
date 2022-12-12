package test.taylor.com.taylorcode.ui.material_design.nested

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.activitystack.getParam
import test.taylor.com.taylorcode.kotlin.NestedScrollView
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.extension.onVisibilityChange

class NestedScrollViewActivity : AppCompatActivity() {

    private lateinit var container: LinearLayout

    private val contentView by lazy {
        NestedScrollView {
            layout_width = match_parent
            layout_height = match_parent

            container = LinearLayout {
                layout_width = match_parent
                layout_height = match_parent
                orientation = vertical
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        initView()
        Log.d("ttaylor", "[getparam]NestedScrollViewActivity.onCreate[savedInstanceState]: inner-fragment-type=${getParam<Int>("inner-fragment-type")}, abc2=${getParam<String>("abc1")} type2=${getParam<Int>("type2")}")
    }

    private fun initView() {
        (1..60).forEach {
            TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 40f
                textColor = "#00ff00"
                gravity = gravity_center
                layout_gravity = gravity_center
                text = "item$it"
            }.also {
                container.addView(it)
                it.onVisibilityChange { view, isVisible ->
                    Log.d("ttaylor", "NestedScrollViewActivity.initView[view(${(view as TextView).text}), isVisible(${isVisible})]: ")
                }
            }
        }
    }
}