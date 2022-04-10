package test.taylor.com.taylorcode.architecture

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*

class LiveDataActivity : AppCompatActivity() {

    private val liveDataViewModel by lazy {
        ViewModelProvider(this@LiveDataActivity)[LiveDataViewModel::class.java]
    }

    private lateinit var tv: TextView

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            tv = TextView {
                layout_id = "tv"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 12f
                text = "save"
                gravity = gravity_center
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
//        liveDataViewModel.stringLiveData.observe(this@LiveDataActivity) {
//            tv.text = it
//        }

        liveDataViewModel.getData()

    }

    override fun onStart() {
        super.onStart()
        liveDataViewModel.livedata.observe(this@LiveDataActivity) {
            Log.v("ttaylor", "onCreate() normal livedata update")
        }
    }
}