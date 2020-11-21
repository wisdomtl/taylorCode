package test.taylor.com.taylorcode.ui.night_mode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*

class TaylorDialogFragment : BaseDialogFragment() {

    companion object {
        fun show(activity: AppCompatActivity) {
            val dialog = TaylorDialogFragment()
            dialog.show(activity.supportFragmentManager, "taylor")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = ConstraintLayout {
        layout_width = 400
        layout_height = 500

        TextView {
            layout_id = "tvtitle"
            layout_width = wrap_content
            layout_height = wrap_content
            center_horizontal = true
            center_vertical = true
            text = "title"
            textSize = 20f
            onClick = onTitleClick
        }

        TextView {
            layout_width = wrap_content
            layout_height = wrap_content
            text = "sub-title"
            textSize = 15f
            top_toBottomOf = "tvtitle"
        }
    }

    private val onTitleClick = { _: View ->
        context?.let {
            Toast.makeText(it, "onclick", Toast.LENGTH_LONG).show()
        }
        Unit
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.apply {
            attributes?.apply {
                width = WindowManager.LayoutParams.WRAP_CONTENT
                height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            setWindowAnimations(R.style.BottomInAndOut)
        }
    }
}