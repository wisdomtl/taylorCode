package test.taylor.com.taylorcode.ui.viewpager3.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.ui.viewpager3.ViewPager2

class Viewpager3Activity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewpager3)
            findViewById<ViewPager2>(R.id.vp3)?.adapter = ViewPager2FragmentStateAdapter(supportFragmentManager, lifecycle, 5)
    }


}