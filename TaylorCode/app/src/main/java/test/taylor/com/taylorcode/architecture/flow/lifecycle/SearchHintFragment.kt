package test.taylor.com.taylorcode.architecture.flow.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.viewpager2.widget.ViewPager2
import test.taylor.com.taylorcode.activitystack.Param
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.fragment.visibility.IPvTracker
import test.taylor.com.taylorcode.ui.pagers.ViewPager2FragmentStateAdapter

class SearchHintFragment : BaseFragment(), IPvTracker,Param {

    private lateinit var vp2:ViewPager2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return context?.run {
            ConstraintLayout {
                layout_width = match_parent
                layout_height = match_parent
                background_color = "#ff00ff"

                vp2 = ViewPager2 {
                    layout_id = "vpContent"
                    layout_width = match_parent
                    layout_height = match_parent
                }
            }
        }.also {it?.tag = "SearchHintFragment" }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vp2.adapter = ViewPager2FragmentStateAdapter(childFragmentManager, lifecycle, 6)
    }

    override fun getPvEventId(): String {
        return "SearchHintFragment"
    }

    override fun getPvExtra(): Bundle {
        return bundleOf()
    }

    override val paramMap: Map<String, Any>
        get() = mapOf("abc2" to "abc1",
        "type" to 100)
}