package test.taylor.com.taylorcode.ui.performance.recyclerview_item_anim

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.anim_activity.*
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class RecyclerViewItemAnimActivity : AppCompatActivity() {


    private val myAdapter by lazy {
        VarietyAdapter2().apply {
            addProxy(TextProxy2())
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
                layoutManager = LinearLayoutManager(this@RecyclerViewItemAnimActivity)
                adapter = myAdapter
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        val texts = mutableListOf<String>()
        (1..200).forEach {
            texts.add(it.toString())
        }
        myAdapter.dataList = texts

        myAdapter.onViewRecycled = { holder ->
            val count = rv.recycledViewPool.getRecycledViewCount(holder.itemViewType)
            Log.v("ttaylor", "onViewRecycled() type=${holder.itemViewType} , count=${count}")
        }
        myAdapter.onFailedToRecycleView = { holder ->
            Log.v("ttaylor", "onFailedToRecycleView() type=${holder.itemViewType}")
            (holder as? TextViewHolder2)?.tv?.animate()?.cancel() // cancel animation before force recycling
            true //return true force recycle
        }

    }
}


class TextProxy2 : VarietyAdapter2.Proxy<String, TextViewHolder2>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            ConstraintLayout {
                layout_width = match_parent
                layout_height = 70

                TextView {
                    layout_id = "tvChange"
                    layout_width = wrap_content
                    layout_height = 70
                    textSize = 30f
                    textColor = "#000000"
                    gravity = gravity_center
                }

            }
        }
        return TextViewHolder2(itemView)
    }

    override fun onBindViewHolder(holder: TextViewHolder2, data: String, index: Int, action: ((Any?) -> Unit)?) {
        holder.tv?.text = data

        holder.tv?.let { tv ->
//            ViewCompat.animate(tv).translationX(900f).setDuration(10000).start()// this will set transient state to true
            // ObjectAnimator wont set transient state
            animSet {
                animObject {
                    target = tv
                    translationX = floatArrayOf(0f, 900f)
                }
                duration = 30000L
                interpolator = AccelerateDecelerateInterpolator()
            }.start()
        }

    }
}

class TextViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv = itemView.find<TextView>("tvChange")
}