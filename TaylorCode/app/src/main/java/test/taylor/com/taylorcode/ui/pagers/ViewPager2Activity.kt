package test.taylor.com.taylorcode.ui.pagers

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.viewpager2_activity.*
import test.taylor.com.taylorcode.R

class ViewPager2Activity : AppCompatActivity() {

    private val imgUrls = mutableListOf(
        "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3030050658,3694586235&fm=26&gp=0.jpg"
        ,
        "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2571315283,182922750&fm=26&gp=0.jpg"
        ,
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591790054139&di=627d2e1d16d93f1f2fdcac074a623d39&imgtype=0&src=http%3A%2F%2Fpngimg.com%2Fuploads%2Fdonald_trump%2Fdonald_trump_PNG56.png"
    )

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewpager2_activity)
        /**
         * case: ViewPager2 with fragments and TabLayout
         */
//        vp2.adapter = ViewPager2Adapter(supportFragmentManager, lifecycle)
//        TabLayoutMediator(tablayout, vp2) { tab, position ->
//            tab.text = position.toString()
//            tab.badge
//        }.attach()

        /**
         * case: ViewPager2 with views
         */
        vp2.adapter = ViewPageViewAdapter().apply { data = imgUrls }
        bannerViewPager()

        fl.dispatchEvent = {ev->
            if (ev?.action == MotionEvent.ACTION_MOVE) {
                handler.removeCallbacksAndMessages(null)
            } else if (ev?.action == MotionEvent.ACTION_UP) {
                handler.postDelayed(showNext, 1000)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.v("ttaylor","tag=asdf, ViewPager2Activity.onTouchEvent()  event=${event?.action}")
        return super.onTouchEvent(event)
    }


    val showNext = object : Runnable {
        override fun run() {
            vp2.currentItem = (++vp2.currentItem)
            handler.postDelayed(this, 1000)
        }
    }
    private fun bannerViewPager() {
        if (imgUrls.size <= 1) return
        handler.postDelayed(showNext, 1000)
    }
}