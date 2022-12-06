package test.taylor.com.taylorcode.ui.custom_view.time_picker

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class TimeProxy : VarietyAdapter2.ItemBuilder<Time, TimeViewHolder>() {
    override fun onBindViewHolder(holder: TimeViewHolder, data: Time, index: Int, action: ((Any?) -> Unit)?) {
        holder.tvHour?.text = data.hour
        holder.tvMinute?.text = data.minute
    }

    @SuppressLint("WrongConstant")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var tvHour: TextView? = null
        var tvMinute: TextView? = null
        val itemView = parent.context.run {
            ConstraintLayout {
                layout_width = match_parent
                layout_height = 25
                padding_horizontal = 17

                tvHour = TextView {
                    layout_id = "tvHour"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 15f
                    textColor = "#3F4658"
                    gravity = gravity_center
                    center_vertical = true
                    start_toStartOf = parent_id
                }

                tvMinute = TextView {
                    layout_id = "tvMinute"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 15f
                    textColor = "#3F4658"
                    gravity = gravity_center
                    center_vertical = true
                    end_toEndOf = parent_id
                }
            }
        }
        return TimeViewHolder(itemView, tvHour, tvMinute)
    }
}

data class Time(
    var hour: String,
    var minute: String
)

class TimeViewHolder(itemView: View, val tvHour: TextView?, val tvMinute: TextView?) : RecyclerView.ViewHolder(itemView) {
    fun getTime(): String = "${tvHour?.text}:${tvMinute?.text}"
}