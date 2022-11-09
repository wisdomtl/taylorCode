package test.taylor.com.taylorcode.activitystack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.architecture.flow.lifecycle.NavigationFragmentActivity
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.startActivity

class NewActivity : AppCompatActivity(), Param {

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                text = "save"
                fontFamily = R.font.pingfang
                gravity = gravity_center
                center_horizontal = true
                onClick = {
                    startActivity<NavigationFragmentActivity>()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

    }

    override val paramMap: Map<String, Any>
        get() = mapOf(
            "type2" to 222,
            "tabName2" to 333
        )
}