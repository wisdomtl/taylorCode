package test.taylor.com.taylorcode.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*

class ConstraintLayoutFlowActivity : AppCompatActivity() {

    private lateinit var container: ConstraintLayout

    private lateinit var flow: Flow

    private val referenceIds = mutableListOf<String>()

    private var index = 0

    private val contentView by lazy {
        container = ConstraintLayout {
            layout_width = match_parent
            layout_height = 500

            flow = Flow {
                layout_width = 0
                layout_height = wrap_content
                start_toStartOf = parent_id
                end_toEndOf = parent_id
                margin_top = 20
                margin_start = 20
                margin_end = 20
                flow_horizontalGap = 5
                flow_verticalGap = 5
                flow_wrapMode = wrap_mode_chain
                flow_horizontalStyle = packed
                flow_horizontalBias = 0f
            }

            TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                textColor = "#ffffff"
                text = "add"
                gravity = gravity_center
                bottom_toBottomOf = parent_id
                end_toEndOf = parent_id
                padding = 10
                shape = shape {
                    solid_color = "#ff00ff"
                }
                onClick = {
                    addTag("dddd${index++}")
                }
            }

            TextView {
                layout_id = "tvAdd"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                textColor = "#ffffff"
                text = "add the same"
                gravity = gravity_center
                bottom_toTopOf = "tvChange"
                end_toEndOf = parent_id
                margin_bottom = 10
                padding = 10
                shape = shape {
                    solid_color = "#ff00ff"
                }
                onClick = {
                    addTag("moshou")
                }
            }

            TextView {
                layout_id = "tvDelete"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                textColor = "#ffffff"
                text = "delete"
                gravity = gravity_center
                bottom_toTopOf = "tvAdd"
                end_toEndOf = parent_id
                margin_bottom = 10
                shape = shape {
                    solid_color = "#ff00ff"
                }
                onClick = {
                    delete()
                }
            }
        }

        container
    }

    private fun delete() {
        container.removeAllViews()
    }

    private fun addTag(s: String) {
        if(referenceIds.contains(s)) {
            referenceIds.remove(s)
            referenceIds.add(0,s)
            flow.reference_ids = referenceIds
            val child = container.findViewById<View>(s.toLayoutId())
            container.removeView(child)
            container.addView(child)
        }else {
            referenceIds.add(s)
            newTag(s).also {
                container.addView(it)
                flow.reference_ids = referenceIds
            }
        }
    }

    private val tags by lazy {
        listOf("天魔", "moshou", "kdfjlklk", "eioroewweio", "dkfjdklfjdsjf", "dklfdslkfjls", "dkfsdlkfjksd")
    }

    private fun newTag(tag: String) = TextView {
        layout_id = tag
        layout_width = wrap_content
        layout_height = wrap_content
        textSize = 18f
        textColor = "#ffffff"
        text = tag
        gravity = gravity_center
        padding_horizontal = 7
        padding_vertical = 4
        shape = shape {
            corner_radius = 20
            solid_color = "#999999"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        tags.forEachIndexed { index, tag ->
            newTag(tag).also {
                referenceIds.add(tag)
                container.addView(it)
                flow.reference_ids = referenceIds
            }
        }
    }
}