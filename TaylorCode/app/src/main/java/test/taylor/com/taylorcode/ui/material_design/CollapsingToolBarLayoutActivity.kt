package test.taylor.com.taylorcode.ui.material_design

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.util.Printer
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.collasping_layout.*
import test.taylor.com.taylorcode.R

class CollapsingToolBarLayoutActivity:AppCompatActivity() {

    private val fragments = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collasping_layout)
        fragments.add(CCCFragment(1))
        fragments.add(CCCFragment(2))
        fragments.add(CCCFragment(3))
        fragments.add(CCCFragment(4))
        fragments.add(CCCFragment(5))
        fragments.add(CCCFragment(6))
        fragments.add(CCCFragment(7))
        vppp.adapter = SimpleFragmentPagerAdapter(supportFragmentManager).apply {
            mTitles = arrayOf("ddd")
            mCount = 7
            createFragment = {position -> fragments[position] }
        }
    }
}