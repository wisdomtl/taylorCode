package test.taylor.com.taylorcode.ui.custom_view.blur

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import test.taylor.com.taylorcode.kotlin.layout_height
import test.taylor.com.taylorcode.kotlin.layout_width
import test.taylor.com.taylorcode.kotlin.match_parent

class BlurDialogFragment:DialogFragment() {

    companion object{
        fun show(fragmentManager: FragmentManager){
            BlurDialogFragment().show(fragmentManager,"blur")
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  context?.let {
            BlurViewGroup(it).apply {
                layout_width = match_parent
                layout_height = match_parent
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.apply {
            attributes?.apply {
                width = WindowManager.LayoutParams.MATCH_PARENT
                height= WindowManager.LayoutParams.MATCH_PARENT
            }
        }
    }
}