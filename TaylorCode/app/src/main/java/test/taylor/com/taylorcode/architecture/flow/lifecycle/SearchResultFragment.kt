package test.taylor.com.taylorcode.architecture.flow.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.fragment.visibility.IPvTracker

class SearchResultFragment:BaseFragment(),IPvTracker {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return context?.run {
            ConstraintLayout {
                layout_width = match_parent
                layout_height = match_parent
                background_color = "#0000ff"
            }
        }
    }

    override fun getPvEventId(): String {
        return "SearchResultFragment"
    }

    override fun getPvExtra(): Bundle {
        return bundleOf()
    }
}