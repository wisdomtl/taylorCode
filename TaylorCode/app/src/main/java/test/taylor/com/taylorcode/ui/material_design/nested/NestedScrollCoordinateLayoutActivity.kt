package test.taylor.com.taylorcode.ui.material_design.nested

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.nested_scroll_coordinate_layout.*
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class NestedScrollCoordinateLayoutActivity : Activity() {

    private val texts by lazy {
        listOf(
            "111",
            "222",
            "223",
            "224",
            "225",
            "226",
            "227",
            "228",
            "229",
            "239",
            "249",
            "259",
            "269",
        )
    }
    private val varietyAdapter = VarietyAdapter2().apply {
        addProxy(TextProxy())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nested_scroll_coordinate_layout)
        rv.apply {
            adapter = varietyAdapter
            layoutManager = LinearLayoutManager(this@NestedScrollCoordinateLayoutActivity)
        }

        varietyAdapter.dataList = texts
    }
}

class TextProxy : VarietyAdapter2.Proxy<String, TextViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            TextView {
                layout_id = "tvChange"
                layout_width = match_parent
                layout_height = 70
                textSize = 30f
                textColor = "#000000"
                gravity = gravity_center
            }
        }
        return TextViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TextViewHolder, data: String, index: Int, action: ((Any?) -> Unit)?) {
       holder.tv?.text = data
    }
}

class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv = itemView.find<TextView>("tvChange")
}