package test.taylor.com.taylorcode.ui.pagers

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter @JvmOverloads constructor(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    var count: Int
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments: SparseArray<Fragment> = SparseArray()

    override fun getItemCount(): Int {
        return count
    }

    override fun createFragment(position: Int): Fragment {
        var fragment = fragments.get(position)
        if (fragment == null) {
            fragment = ViewPagerFragment()
            fragments.put(position, fragment)
        }
        return fragment
    }
}