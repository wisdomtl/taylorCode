package test.taylor.com.taylorcode.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentResultListener
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*

class FragmentActivity : AppCompatActivity() {


    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                textColor = "#ffffff"
                text = "Activity"
                gravity = gravity_center
                top_toTopOf = parent_id
                center_horizontal = true
            }

            FrameLayout {
                layout_id = "container"
                layout_width = match_parent
                layout_height = 0
                top_toTopOf = "tvChange"
                bottom_toBottomOf = parent_id
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        supportFragmentManager.beginTransaction().add("container".toLayoutId(), Fragment1()).commit()

        supportFragmentManager.setFragmentResultListener("abc",this) { requestKey, result -> Log.v("ttaylor","activity.onCreate() result=${result["111"]}") }
        supportFragmentManager.setFragmentResultListener("efg",this) { requestKey, result -> Log.v("ttaylor","activity.onCreate() result=${result["112"]}") }
    }
}