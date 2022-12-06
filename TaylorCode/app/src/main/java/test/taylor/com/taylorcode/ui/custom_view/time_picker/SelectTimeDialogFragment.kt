package test.taylor.com.taylorcode.ui.custom_view.time_picker

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*

import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2
import java.util.*
import kotlin.math.ceil

class SelectTimeDialogFragment : DialogFragment() {
    private lateinit var rvStart: RecyclerView
    private lateinit var rvEnd: RecyclerView
    private var startSnap = LinearSnapHelper()
    private var endSnap = LinearSnapHelper()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent
            padding_top = 17
            shape = shape {
                corner_radii = intArrayOf(12, 12, 12, 12, 0, 0, 0, 0)
                solid_color = "#ffffff"
            }

            TextView {
                layout_id = "tvCancel"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 15f
                textColor = "#747E8B"
                text = "取消"
                gravity = gravity_center
                start_toStartOf = parent_id
                top_toTopOf = parent_id
                margin_start = 18
                onClick = {
                    dismiss()
                }
            }

            TextView {
                layout_id = "tvConfirm"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 15f
                textColor = "#FF5183"
                text = "确定"
                gravity = gravity_center
                end_toEndOf = parent_id
                top_toTopOf = parent_id
                margin_end = 18
                onClick = {
                    confirm()
                    dismiss()
                }
            }

            View {
                layout_id = "vDivider"
                layout_width = match_parent
                layout_height = 1
                background_color = "#EEEEEE"
                top_toBottomOf = "tvCancel"
                margin_top = 16
            }

            TextView {
                layout_id = "tvStart"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 15f
                textColor = "#747E8B"
                text = "开始时间"
                gravity = gravity_center
                top_toBottomOf = "vDivider"
                start_toStartOf = parent_id
                end_toStartOf = "tvEnd"
                margin_top = 15
            }

            TextView {
                layout_id = "tvEnd"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 15f
                textColor = "#747E8B"
                text = "结束时间"
                gravity = gravity_center
                top_toBottomOf = "vDivider"
                start_toEndOf = "tvStart"
                end_toEndOf = parent_id
                align_vertical_to = "tvStart"
            }

            rvStart = RecyclerView {
                layout_id = "rvStart"
                layout_width = 109
                layout_height = 175
                top_toBottomOf = "tvStart"
                align_horizontal_to = "tvStart"
                bottom_toBottomOf = parent_id
                layoutManager = LinearLayoutManager(context)
                adapter = timesAdapter
                startSnap.attachToRecyclerView(this)
            }

            View {
                layout_id = "vDividerStartTop"
                layout_width = 109
                layout_height = 0.5
                align_horizontal_to = "rvStart"
                top_toTopOf = "rvStart"
                bottom_toTopOf = "vDividerStartBottom"
                vertical_chain_style = packed
                margin_bottom = 12.5
                background_color = "#D3D6DE"
            }

            View {
                layout_id = "vDividerStartBottom"
                layout_width = 109
                layout_height = 0.5
                align_horizontal_to = "rvStart"
                top_toBottomOf = "vDividerStartTop"
                bottom_toBottomOf = "rvStart"
                vertical_chain_style = packed
                margin_top = 12.5
                background_color = "#D3D6DE"
            }

            rvEnd = RecyclerView {
                layout_id = "rvEnd"
                layout_width = 109
                layout_height = 175
                top_toBottomOf = "tvEnd"
                align_horizontal_to = "tvEnd"
                bottom_toBottomOf = parent_id
                layoutManager = LinearLayoutManager(context)
                adapter = timesAdapter
                endSnap.attachToRecyclerView(this)
            }

            View {
                layout_id = "vDividerEndTop"
                layout_width = 109
                layout_height = 0.5
                align_horizontal_to = "rvEnd"
                top_toTopOf = "rvEnd"
                bottom_toTopOf = "vDividerEndBottom"
                vertical_chain_style = packed
                margin_bottom = 12.5
                background_color = "#D3D6DE"
            }

            View {
                layout_id = "vDividerEndBottom"
                layout_width = 109
                layout_height = 0.5
                align_horizontal_to = "rvEnd"
                top_toBottomOf = "vDividerEndTop"
                bottom_toBottomOf = "rvEnd"
                vertical_chain_style = packed
                margin_top = 12.5
                background_color = "#D3D6DE"
            }

            View {
                layout_id = "vMask"
                layout_width = 0
                layout_height = 75
                start_toStartOf = "rvStart"
                end_toEndOf = "rvEnd"
                top_toTopOf = "rvStart"
                shape = shape {
                    gradient_colors = listOf("#ffffffff", "#66ffffff")
                    orientation = gradient_top_bottom
                }
            }

            View {
                layout_id = "vMask"
                layout_width = 0
                layout_height = 75
                bottom_toBottomOf = "rvStart"
                start_toStartOf = "rvStart"
                end_toEndOf = "rvEnd"
                shape = shape {
                    gradient_colors = listOf("#ffffffff", "#66ffffff")
                    orientation = gradient_bottom_top
                }
            }
        }
    }

    private fun confirm() {
        val startTime = rvStart.getChildAt(3)?.let {
            (rvStart.getChildViewHolder(it) as? TimeViewHolder)?.getTime()
        }
        val endTime = rvEnd.getChildAt(3)?.let {
            (rvEnd.getChildViewHolder(it) as? TimeViewHolder)?.getTime()
        }
        if (isValidTime(startTime, endTime)) {

        }
    }

    private fun isValidTime(startTime: String?, endTime: String?): Boolean {
        if (startTime == null || endTime == null) return false
        val end = endTime.split(":").let {
            val hour = it.first().removePrefix("0").toInt()
            val minute = it.last().toInt()
            hour * 60 + minute
        }
        val start = startTime.split(":").let {
            val hour = it.first().removePrefix("0").toInt()
            val minute = it.last().toInt()
            hour * 60 + minute
        }
        return end - start >= 30
    }

    private val timesAdapter = VarietyAdapter2().apply {
        addItemBuilder(TimeProxy())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.SlideDialog)
        timesAdapter.dataList = generateAllTime()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showCurrentTime()
    }

    private fun showCurrentTime() {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val minute = Calendar.getInstance().get(Calendar.MINUTE)
        val index = hour * 4 + ceil(minute / 15f).toInt()
        rvStart.scrollToPosition(index)
        rvEnd.scrollToPosition(index + 8)
    }

    private fun generateAllTime(): List<Time> = mutableListOf<Time>().apply {
        add(Time("", ""))
        add(Time("", ""))
        add(Time("", ""))
        (0..9).forEach {
            add(Time("0$it", "00"))
            add(Time("0$it", "15"))
            add(Time("0$it", "30"))
            add(Time("0$it", "45"))
        }
        (10..23).forEach {
            add(Time("$it", "00"))
            add(Time("$it", "15"))
            add(Time("$it", "30"))
            add(Time("$it", "45"))
        }
        add(Time("", ""))
        add(Time("", ""))
        add(Time("", ""))
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.decorView?.shape = shape {
            corner_radius = 15
            solid_color = "#ffffff"
        }
        dialog?.window?.attributes?.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = 279.dp
            gravity = Gravity.BOTTOM
        }

    }
}