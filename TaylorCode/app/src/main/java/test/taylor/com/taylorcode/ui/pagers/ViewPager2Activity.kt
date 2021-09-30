package test.taylor.com.taylorcode.ui.pagers

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.viewpager2_activity.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.TextView
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.recyclerview.variety.Diff
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

/**
 * ViewPager2 with views
 */
class ViewPager2Activity : AppCompatActivity() {

    private val imgUrls = mutableListOf(
        "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3030050658,3694586235&fm=26&gp=0.jpg",
        "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2571315283,182922750&fm=26&gp=0.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591790054139&di=627d2e1d16d93f1f2fdcac074a623d39&imgtype=0&src=http%3A%2F%2Fpngimg.com%2Fuploads%2Fdonald_trump%2Fdonald_trump_PNG56.png"
    )

    private val viewPagerFlow = MutableStateFlow<List<BaseBean>?>(null)

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewpager2_activity)
        /**
         * case: ViewPager2 with views
         */
        val viewPagerAdapter = VarietyAdapter2().apply {
            addProxy(ViewPagerProxy())
            addProxy(ViewPagerEmptyProxy())
        }
        vp2.adapter = viewPagerAdapter
        /**
         * case: ViewPager2 with Tab
         */
        TabLayoutMediator(tablayout, vp2) { tab, position ->
            tab.text = position.toString()
            tab.badge
        }.attach()
        vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.v("ttaylor", "onPageSelected() position = $position")
                lifecycleScope.launch { fetch(position) }
            }
        })
//        bannerViewPager()
//
//        fl.dispatchEvent = { ev ->
//            if (ev?.action == MotionEvent.ACTION_MOVE) {
//                handler.removeCallbacksAndMessages(null)
//            } else if (ev?.action == MotionEvent.ACTION_UP) {
//                handler.postDelayed(showNext, 1000)
//            }
//        }

        //load data
        lifecycleScope.launch {
            delay(500)
            viewPagerFlow.value = listOf(
                // it must be 0 ,1,2,3 which is according to the real index in adapter,or DiffUtil will consider it as remove and insert, then ViewPager will scroll automatically
                EmptyString(0, "null"),
                EmptyString(1, "null"),
                EmptyString(2, "null"),
                EmptyString(3, "null"),
            )
        }

        // observe data
        lifecycleScope.launch {
            viewPagerFlow.collect { list ->
                list ?: return@collect
                val oldData = viewPagerAdapter.dataList.toMutableList()
                if (oldData.isEmpty()) {
                    viewPagerAdapter.dataList = list
                } else {
                    list.forEach { item -> oldData.set(item.id, item) }
                    viewPagerAdapter.dataList = oldData
                }
            }
        }
    }

    private suspend fun fetch(id: Int) {
        delay(1500)
        viewPagerFlow.value = listOf(EmptyString(id, "wisdomtl${id}"))
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.v("ttaylor", "tag=asdf, ViewPager2Activity.onTouchEvent()  event=${event?.action}")
        return super.onTouchEvent(event)
    }


    /**
     * case3: make ViewPager auto-scroll like banner
     */
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

class ViewPagerProxy : VarietyAdapter2.Proxy<DataText, ViewPagerViewHolder2>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            TextView {
                layout_id = "tvChange"
                layout_width = match_parent
                layout_height = match_parent
                textSize = 50f
                textColor = "#888888"
                fontFamily = R.font.pingfang
                gravity = gravity_center
            }
        }
        return ViewPagerViewHolder2(itemView)
    }

    override fun onBindViewHolder(
        holder: ViewPagerViewHolder2,
        data: DataText,
        index: Int,
        action: ((Any?) -> Unit)?
    ) {
        holder.tv?.text = data.str
    }

}

class DataText(idd: Int, var str: String) : BaseBean(idd)

class ViewPagerViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv = itemView.find<TextView>("tvChange")
}


class ViewPagerEmptyProxy : VarietyAdapter2.Proxy<EmptyString, ViewPagerEmptyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            TextView {
                layout_id = "tvChange"
                layout_width = match_parent
                layout_height = match_parent
                textSize = 40f
                textColor = "#00ff00"
                fontFamily = R.font.pingfang
                gravity = gravity_center
            }
        }
        return ViewPagerEmptyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ViewPagerEmptyViewHolder,
        data: EmptyString,
        index: Int,
        action: ((Any?) -> Unit)?
    ) {
        holder.tvChange?.text = data.str
    }
}

class EmptyString(var idd: Int, var str: String) : BaseBean(idd), Diff {
    override fun diff(other: Any?): Any? {
        return null
    }

    override fun sameAs(other: Any?): Boolean {
        return when {
            other !is EmptyString -> false
            other.idd == this.idd -> true
            else -> false
        }
    }

    override fun contentSameAs(other: Any?): Boolean {
        return when {
            other !is EmptyString -> false
            other.str == this.str -> true
            else -> false
        }
    }
}

class ViewPagerEmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvChange = itemView.find<TextView>("tvChange")
}

open class BaseBean(var id: Int)