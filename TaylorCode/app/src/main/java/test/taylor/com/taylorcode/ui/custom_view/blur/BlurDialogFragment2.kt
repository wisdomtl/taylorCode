package test.taylor.com.taylorcode.ui.custom_view.blur

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import test.taylor.com.taylorcode.kotlin.extension.decorView
import test.taylor.com.taylorcode.kotlin.layout_height
import test.taylor.com.taylorcode.kotlin.layout_width
import test.taylor.com.taylorcode.kotlin.match_parent

class BlurDialogFragment2 : DialogFragment() {

    private lateinit var contentView: BlurringView

    companion object {
        fun show(fragmentManager: FragmentManager) {
            BlurDialogFragment2().show(fragmentManager, "blur")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return context?.let {
            contentView = BlurringView(it).apply {
                layout_width = match_parent
                layout_height = match_parent
            }
            contentView
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fullScreenMode()

        activity?.decorView?.let { contentView.setBlurredView(it) }
    }

}