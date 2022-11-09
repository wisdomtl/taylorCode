package test.taylor.com.taylorcode.architecture.flow.lifecycle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.activitystack.getParam
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.fragment.visibility.IPvTracker

class SubFragment1:BaseFragment(),IPvTracker {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return context?.run {
            ConstraintLayout {
                layout_width = match_parent
                layout_height = match_parent
                background_color = "#f0f0f0"

                TextView {
                    layout_id = "tvChange"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 50f
                    textColor = "#ffffff"
                    text = "sub Fragment 1"
                    fontFamily = R.font.pingfang
                    gravity = gravity_center
                    center_vertical = true
                    center_horizontal = true
                }
            }.also { it.setTag("subFragment1") }
        }
    }
    override fun getPvEventId(): String {
        return "SubFragment1"
    }

    override fun getPvExtra(): Bundle {
        return bundleOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ttaylor", "[getParam]SubFragment1.onCreate[savedInstanceState]: type=${activity?.getParam<Int>("type")},tabName=${activity?.getParam<String>("tabName")}, map=${activity?.getParam<Map<String,Int>>("map")},type2=${activity?.getParam<Int>("type2")}")

    }
}