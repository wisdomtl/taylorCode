package test.taylor.com.taylorcode.ui.recyclerview.grid_layout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.extension.onItemVisibilityChange
import test.taylor.com.taylorcode.startActivity
import test.taylor.com.taylorcode.ui.recyclerview.StaggerLayoutManagerActivity
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class GridLayoutActivity : AppCompatActivity() {

    private val gridAdapter by lazy {
        VarietyAdapter2().apply {
            addProxy(GridProxy())
        }
    }

    private lateinit var rv:RecyclerView

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            rv = RecyclerView {
                layout_id ="rv"
                layout_width = match_parent
                layout_height = 500
                top_toTopOf = parent_id
                layoutManager = GridLayoutManager(this@GridLayoutActivity, 2, GridLayoutManager.VERTICAL, false).apply {
                    /**
                     * GridLayoutManager case: SpanSizeLookup
                     */
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return if (position == 0) {
                                return 2;
                            } else {
                                return 1;
                            }
                        }
                    }
                }
                adapter = gridAdapter
            }

            TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                textColor = "#ffffff"
                text = "jump"
                fontFamily = R.font.pingfang
                gravity = gravity_center
                top_toBottomOf = "rv"
                onClick = {
                    this@ConstraintLayout.context.startActivity<StaggerLayoutManagerActivity>()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        gridAdapter.dataList = (0 .. 200).map { GridBean("$it") }

        rv.onItemVisibilityChange(0.5f){ itemView: View, adapterIndex: Int, isVisible: Boolean ->
            Log.d("ttaylor", "GridLayoutActivity.onCreate[itemView, adapterIndex($adapterIndex), isVisible($isVisible)]: ")
        }
    }
}


data class GridBean(val str: String)

class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv = itemView.find<TextView>("tv")
}

class GridProxy : VarietyAdapter2.Proxy<GridBean, GridViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            ConstraintLayout {
                layout_width = 200
                layout_height = 200
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
        Log.d("ttaylor", "GridProxy.onBindViewHolder[holder, data, index, action]: parent=${holder.itemView.parent}")
        holder.tv?.text = data.str
    }
}