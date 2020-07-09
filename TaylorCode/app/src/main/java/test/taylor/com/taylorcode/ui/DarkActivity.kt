package test.taylor.com.taylorcode.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.startActivity
import android.util.Log

class DarkActivity : AppCompatActivity() {

    private val rootView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent
            TextView {
                layout_id = "tvTitle"
                layout_width = wrap_content
                layout_height = wrap_content
                text = "dark activity"
                center_horizontal = true
                center_vertical = true
                textSize = 20f
                onClick = dimActivity
            }

            Button {
                layout_width = wrap_content
                layout_height = wrap_content
                text = "another activity"
                textAllCaps = false
                top_toBottomOf = "tvTitle"
                center_horizontal = true
                onClick = startAnotherActivity
            }
        }
    }

    private val startAnotherActivity = { _: View ->
        startActivity<ActionBarActivity>()
        Unit
    }

    private val dimActivity  = {_:View->
        dimScreen()
        Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        dimScreen()
        window.attributes = window.attributes.apply {
//            width = 1000
//            height = 300
//            alpha = 0.2f
            screenBrightness = 0.04F
        }

        Log.v("ttaylor","tag=, DarkActivity.onCreate()  brightness=${window.attributes.screenBrightness}")

//        setContentView(rootView)
//        dimScreen()
    }


    private fun dimScreen() {
        Log.v("ttaylor","tag=, DarkActivity.dimScreen()  ")
        val layoutParams = this.getWindow().getAttributes()
        layoutParams.dimAmount = 0.1f
        this.getWindow().setAttributes(layoutParams)
    }
}