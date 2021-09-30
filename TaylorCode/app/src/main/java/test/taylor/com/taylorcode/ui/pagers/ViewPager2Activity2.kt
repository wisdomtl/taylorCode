package test.taylor.com.taylorcode.ui.pagers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.viewpager2_activity.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.R

/**
 * case: ViewPager2 with fragment
 */
class ViewPager2Activity2 : AppCompatActivity() {

    private val tabLiveData = MutableLiveData<List<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewpager2_activity)

        tabLiveData.observe(this@ViewPager2Activity2) { list ->
            vp2.adapter = ViewPager2Adapter(supportFragmentManager, lifecycle, list.size)
        }

        // load data
        lifecycleScope.launch {
            delay(500)
            tabLiveData.value = listOf(
                "a",
                "b",
                "c"
            )
        }
    }


}