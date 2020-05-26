package test.taylor.com.taylorcode.ui.state_cross_activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import test.taylor.com.taylorcode.kotlin.*

class LiveDataActivity : AppCompatActivity() {

    private val rootView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            TextView {
                layout_width = wrap_content
                layout_height = wrap_content
                text = "change value of livedata1"
                onClick = changeLiveData1
                top_toTopOf = parent_id
                textSize = 20f
                center_horizontal = true
            }
        }
    }

    private var liveData1 = MutableLiveData<String>()
    private var liveData2 = MutableLiveData<String>()

    private val changeLiveData1: (View) -> Unit = { _ ->
        liveData1.value = "abc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)

        /**
         *  Transformations.map
         */
        val mapLiveData = Transformations.map(liveData1) { it + "aaaa" }
        mapLiveData.observe(this, Observer {
            Log.v("ttaylor", "tag=, LiveDataActivity.onCreate()  map live data=${it}")
        })

        liveData1.observe(this, Observer {
            Log.v("ttaylor", "tag=, LiveDataActivity.onCreate()  origin live data=${it}")
        })
    }
}