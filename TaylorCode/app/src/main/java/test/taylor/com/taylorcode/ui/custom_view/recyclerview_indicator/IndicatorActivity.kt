package test.taylor.com.taylorcode.ui.custom_view.recyclerview_indicator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter

class IndicatorActivity : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var indicator: Indicator

    private val indicatorAdapter = VarietyAdapter().apply {
        addProxy(IndicatorAdapterProxy())
        dataList = listOf(
            IndicatorBean(1),
            IndicatorBean(2),
            IndicatorBean(3),
            IndicatorBean(4),
            IndicatorBean(5),
            IndicatorBean(6),
            IndicatorBean(7),
            IndicatorBean(8),
            IndicatorBean(9),
            IndicatorBean(10)
        )
    }

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            rv = RecyclerView {
                layout_id = "rv"
                layout_width = match_parent
                layout_height = 100
                layoutManager = LinearLayoutManager(this@IndicatorActivity).apply {
                    orientation = horizontal
                }
                adapter = indicatorAdapter

            }

            indicator = Indicator {
                layout_width = 80
                layout_height = 30
                top_toBottomOf = "rv"
                center_horizontal = true
                rx = 20f
                ry = 20f
                indicatorWidth = 20f
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val scrollRange = recyclerView.computeHorizontalScrollRange()
                val offset = recyclerView.computeHorizontalScrollOffset()
                val extend = recyclerView.computeHorizontalScrollExtent()
                val progress = offset.toFloat() / (scrollRange- extend)
                Log.v("ttaylor", "tag=adhf, IndicatorActivity.onScrolled()  progress = ${progress}")
                indicator.progress = progress
            }
        })
        indicatorAdapter.notifyDataSetChanged()


    }
}