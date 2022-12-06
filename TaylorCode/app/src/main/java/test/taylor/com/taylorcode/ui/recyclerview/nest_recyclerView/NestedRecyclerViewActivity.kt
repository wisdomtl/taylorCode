package test.taylor.com.taylorcode.ui.recyclerview.nest_recyclerView

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.extension.onItemVisibilityChange
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class NestedRecyclerViewActivity : AppCompatActivity() {
    private val verticalAdapter by lazy {
        VarietyAdapter2().apply {
            addItemBuilder(VerticalItemBuilder())
        }
    }

    private lateinit var rv: RecyclerView

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            rv = RecyclerView {
                layout_width = match_parent
                layout_height = match_parent
                layoutManager = LinearLayoutManager(this@NestedRecyclerViewActivity, LinearLayoutManager.VERTICAL, false)
                adapter = verticalAdapter
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        val data = (0..50).map {outerIndex->
            VerticalTexts().apply {
                strs = (0..30).map {innerIndex-> "${outerIndex}.${innerIndex}" }
            }
        }

        verticalAdapter.dataList = data

        rv.onItemVisibilityChange { itemView, outerIndex, isVisible ->
            Log.w("ttaylor", "NestedRecyclerViewActivity.onCreate[itemView, outerIndex($outerIndex), isVisible($isVisible)]: ")
            (itemView as? RecyclerView)?.takeIf { isVisible }?.apply {
                onItemVisibilityChange { itemView, innerIndex, isInnerVisible ->
                    Log.d("ttaylor", "NestedRecyclerViewActivity.onCreate[itemView, innerIndex(${outerIndex}.${innerIndex}), isVisible($isInnerVisible)]: ")
                }
                scrollBy(1,0)
            }

        }
    }
}

class VerticalTexts {
    lateinit var strs: List<String>
}

class VerticalViewHolder(itemView: View) : ViewHolder(itemView) {
    val adapter = VarietyAdapter2().apply {
        addItemBuilder(HorizontalItemBuilder())
    }

    val rv = itemView.find<RecyclerView>("rv11")
}

class VerticalItemBuilder : VarietyAdapter2.ItemBuilder<VerticalTexts, VerticalViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = parent.context.run {
            RecyclerView {
                layout_id = "rv11"
                layout_width = match_parent
                layout_height = 200
                layoutManager = LinearLayoutManager(this@run, LinearLayoutManager.HORIZONTAL, false)
            }
        }
        return VerticalViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VerticalViewHolder, data: VerticalTexts, index: Int, action: ((Any?) -> Unit)?) {
        holder.adapter.dataList = data.strs
        holder.rv?.adapter = holder.adapter
    }
}


class HorizontalViewHolder(itemView: View) : ViewHolder(itemView) {
    val tv = itemView.find<TextView>("tv")
}

class HorizontalItemBuilder : VarietyAdapter2.ItemBuilder<String, HorizontalViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = parent.context.run {
            TextView {
                layout_id = "tv"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                textColor = "#ffffff"
                gravity = gravity_center
                padding_horizontal = 10
                padding_vertical = 20
                shape = shape {
                    corner_radius = 20
                    solid_color = "#ff00ff"
                }
            }
        }
        return HorizontalViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, data: String, index: Int, action: ((Any?) -> Unit)?) {
        holder.tv?.text = data
    }

}