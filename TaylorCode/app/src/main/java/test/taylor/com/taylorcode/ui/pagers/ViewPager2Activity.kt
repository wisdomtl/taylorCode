package test.taylor.com.taylorcode.ui.pagers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.viewpager2_activity.*
import test.taylor.com.taylorcode.R

class ViewPager2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewpager2_activity)
        vp2.adapter = ViewPager2Adapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(tablayout, vp2) { tab, position ->
            tab.text = position.toString()
            tab.badge
        }.attach()
    }
}