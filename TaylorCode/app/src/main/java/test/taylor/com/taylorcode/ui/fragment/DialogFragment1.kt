package test.taylor.com.taylorcode.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import test.taylor.com.taylorcode.kotlin.*

class DialogFragment1 : DialogFragment() {

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                textColor = "#FF00FF"
                text = "DialogFragment 1"
                gravity = gravity_center
                center_horizontal = true
                center_vertical = true
                onClick = {
                    /**
                     * case: send a fragment result of  "abc"
                     */
                    parentFragmentManager.setFragmentResult("abc", bundleOf("111" to 333))
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return contentView
    }
}