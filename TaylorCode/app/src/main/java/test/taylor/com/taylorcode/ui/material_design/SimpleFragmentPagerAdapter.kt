package test.taylor.com.taylorcode.ui.material_design

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by xf on 2020/5/19
 * email: yanglinfeng@bilibili.com
 * since: 1.0.0
 */
open class SimpleFragmentPagerAdapter(fm: FragmentManager,behavior:Int) : FragmentPagerAdapter(fm,behavior) {
    var mTitles: Array<String>? = null
    var mCount: Int = 0
    lateinit var createFragment: (position: Int) -> Fragment

    override fun getItem(position: Int) = createFragment.invoke(position)

    override fun getCount() = mCount

    override fun getPageTitle(position: Int) = mTitles?.get(position)
}