package test.taylor.com.taylorcode.ui.recyclerview

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.extension.onItemVisibilityChange
import test.taylor.com.taylorcode.ui.recyclerview.grid_layout.GridBean
import test.taylor.com.taylorcode.ui.recyclerview.grid_layout.GridViewHolder
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class StaggerLayoutManagerActivity:AppCompatActivity() {

    private val staggerAdapter by lazy {
        VarietyAdapter2().apply {
            addItemBuilder(StaggerProxy())
            addItemBuilder(StaggerProxy2())
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
                layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                adapter = staggerAdapter
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        staggerAdapter.dataList = (0 .. 200).map { if(it.mod(2) == 0)GridBean("$it") else GridBean2("$it") }
        rv.onItemVisibilityChange(percent = 0.5f){ itemView: View, adapterIndex: Int, isVisible: Boolean ->
            Log.d("ttaylor", "StaggerLayoutManagerActivity.onCreate[itemView, adapterIndex($adapterIndex), isVisible($isVisible)]: ")
        }
    }
}

class StaggerProxy : VarietyAdapter2.ItemBuilder<GridBean, GridViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            ConstraintLayout {
                layout_width = match_parent
                layout_height = 100
                shape = shape {
                    solid_color = "#888888"
                    corner_radius = 20
                }

                TextView {
                    layout_id = "tv"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 20f
                    textColor = "#ffffff"
                    gravity = gravity_center
                    center_horizontal = true
                    center_vertical = true
                }
            }
        }
        return GridViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GridViewHolder, data: GridBean, index: Int, action: ((Any?) -> Unit)?) {
        holder.tv?.text = data.str
    }
}

data class GridBean2(val str: String)

class StaggerProxy2 : VarietyAdapter2.ItemBuilder<GridBean2, GridViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            ConstraintLayout {
                layout_width = match_parent
                layout_height = 200
                shape = shape {
                    solid_color = "#787878"
                    corner_radius = 20
                }

                TextView {
                    layout_id = "tv"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 20f
                    textColor = "#ffffff"
                    gravity = gravity_center
                    center_horizontal = true
                    center_vertical = true
                }
            }
        }
        return GridViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GridViewHolder, data: GridBean2, index: Int, action: ((Any?) -> Unit)?) {
        holder.tv?.text = data.str
    }
}