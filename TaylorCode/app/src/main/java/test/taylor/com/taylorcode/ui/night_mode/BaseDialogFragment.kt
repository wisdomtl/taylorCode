package test.taylor.com.taylorcode.ui.night_mode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

/**
 * base DialogFragment which is able to show night mode dialog
 */
abstract class BaseDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return context?.let {
            MaskViewGroup(it).apply {
                addView(createView(inflater, container, savedInstanceState))
            }
        }
    }

    abstract fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
}