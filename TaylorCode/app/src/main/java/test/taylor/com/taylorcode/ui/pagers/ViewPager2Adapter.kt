package test.taylor.com.taylorcode.ui.pagers

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter @JvmOverloads constructor(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments: SparseArray<Fragment> = SparseArray()

    init {
        fragments.put(0, VpFragment1.newInstance())
        fragments.put(1, VpFragment2.newInstance())
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return fragments.get(position)
    }
}