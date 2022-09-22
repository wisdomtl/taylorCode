package test.taylor.com.taylorcode.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*

class Fragment1:Fragment() {

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                textColor = "#ff00ff"
                text = "Fragment 1"
                gravity = gravity_center
                center_horizontal = true
                center_vertical = true
                onClick = {
                    DialogFragment1().show(childFragmentManager,"ddd")
                }
            }

            TextView {
                layout_id = "tvChange2"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                textColor = "#ff00ff"
                text = "send message to activity"
                top_toBottomOf = "tvChange"
                margin_top = 20
                gravity = gravity_center

                onClick = {
                    parentFragmentManager.setFragmentResult("efg", bundleOf("112" to 112))
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return contentView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * case: register a listener for key "abc"
         */
        childFragmentManager.setFragmentResultListener("abc",this) { requestKey, result ->
            when(requestKey){
                "abc" ->{
                    Log.v("ttaylor","Fragment.onCreate() result=${result["111"]}")
                }
            }
        }
    }
}