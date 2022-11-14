package test.taylor.com.taylorcode.ui.pagers

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.extension.onVisibilityChange

/**
 * Created by taylor on 2017/11/13.
 */
class ViewPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_pager_activity)
//        val adapter = MyPagerAdapter(prepareViews())
        val adapter = ViewPagerFragmentAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT)
        val vp = findViewById<View>(R.id.vp) as MyViewPager
        vp.adapter = adapter
        vp.setScrollable(true)
        setIndicator(vp)
//        val tv = adapter.findViewById(R.id.tv_pager1) as TextView?
//        tv!!.setOnClickListener { Toast.makeText(this@ViewPagerActivity, "tv1", Toast.LENGTH_SHORT).show() }
    }

    private fun setIndicator(viewPager: ViewPager) {
        val magicIndicator = findViewById<View>(R.id.mi) as MagicIndicator
        val indicator = Indicator(this)
        indicator.circleCount = PAGE_LAYOUT_ID.size
        indicator.setLongColor(Color.DKGRAY)
        indicator.setShortColor(Color.GRAY)
        magicIndicator.navigator = indicator
        ViewPagerHelper.bind(magicIndicator, viewPager)
    }

    private fun prepareViews(): List<View> {
        val views: MutableList<View> = ArrayList()
        for (i in PAGE_LAYOUT_ID.indices) {
//            val page: View = LayoutInflater.from(this).inflate(layoutId, null)
//            var tv: TextView? = null
//            tv = page.findViewById(R.id.tv_pager1)
//            if (tv == null) tv = page.findViewById(R.id.tv_pager2)
//            if (tv == null) tv = page.findViewById(R.id.tv_pager3)
//            if (tv == null) tv = page.findViewById(R.id.tv_pager4)
//            if (tv == null) tv = page.findViewById(R.id.tv_pager5)
//            tv.tag = i
            var tv: TextView? = null
            val page = ConstraintLayout {
                layout_width = match_parent
                layout_height = match_parent

                tv = TextView {
                    layout_id = "tvChange"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 30f
                    textColor = "#00ff00"
                    gravity = gravity_center
                    center_horizontal = true
                    center_vertical = true
                    text = "page${i}"
                    tag = i
                }
            }
            tv?.onVisibilityChange { view: View, aBoolean: Boolean ->
                Log.w("ttaylor", "ViewPagerActivity.onVisibilityChange(show=" + aBoolean + "),tag=" + view.tag + ",view=" + view)
                Unit
            }
            views.add(page)
        }
        return views
    }

    internal  inner class ViewPagerFragmentAdapter(fragmentManager:FragmentManager,behavior:Int) : FragmentPagerAdapter(fragmentManager,behavior) {
        private val fragments: SparseArray<Fragment> = SparseArray()
        override fun getCount(): Int {
            return 5
        }

        override fun getItem(position: Int): Fragment {
            var fragment = fragments.get(position)
            if (fragment == null) {
                fragment = ViewPagerFragment().apply {
                    arguments = bundleOf("index" to position)
                }
                fragments.put(position, fragment)
            }
            return fragment
        }
    }

    /**
     * case1:the basic use of PagerAdapter extension
     */
    internal inner class MyPagerAdapter(private val views: List<View>?) : PagerAdapter() {
        /**
         * case2:find views in ViewPagers
         *
         * @param id
         * @return
         */
        fun findViewById(id: Int): View? {
            for (view in views!!) {
                val findView = view.findViewById<View>(id)
                if (findView != null) {
                    return findView
                }
            }
            return null
        }

        override fun getCount(): Int {
            return views!!.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            if (views != null && views.size > position) {
                container.removeView(views[position])
            }
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            container.addView(views!![position])
            return views[position]
        }
    }

    companion object {
        val PAGE_LAYOUT_ID = intArrayOf(R.layout.pager1, R.layout.page2, R.layout.page3, R.layout.page4, R.layout.page5)
    }
}