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
        fragments.add(CCCFragment())
        vppp.adapter = SimpleFragmentPagerAdapter(supportFragmentManager).apply {
            mTitles = arrayOf("ddd")
            mCount = 1
            createFragment = {position ->
               fragments[position]
            }
        }


    }
}