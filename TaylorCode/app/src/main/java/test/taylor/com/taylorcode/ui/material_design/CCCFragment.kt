package test.taylor.com.taylorcode.ui.material_design

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.collapsing_layout2.*
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.architecture.flow.lifecycle.BaseFragment
import test.taylor.com.taylorcode.kotlin.extension.onVisibilityChange
import test.taylor.com.taylorcode.ui.fragment.visibility.IPvTracker
import kotlin.math.log

class CCCFragment(private val tag:Int) : BaseFragment(),IPvTracker {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.collapsing_layout2, null).also { it.tag = "cccFragment(${tag})" }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvViewPagerFragment.text = tag.toString()
        tvViewPagerFragment.onVisibilityChange { view, b ->
            Log.d("ttaylor", "CCCFragment.onViewCreated[view(${(view as TextView).text}), visibility=${b}]: ")
        }
//        dddddd.onClick = {
//            Looper.getMainLooper().dump({ str-> Log.v("ttaylor","$str") },"ttaylorMessage")
//
//        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("ttaylor", "CCCFragment(${tag}).onResume[]: ")
    }

    override fun getPvEventId(): String {
        return "CCCFragment${tag}"
    }

    override fun getPvExtra(): Bundle {
        return bundleOf()
    }
}