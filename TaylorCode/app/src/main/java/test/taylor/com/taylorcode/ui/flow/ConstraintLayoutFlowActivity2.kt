package test.taylor.com.taylorcode.ui.flow

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*

class ConstraintLayoutFlowActivity2 : AppCompatActivity() {

    private lateinit var flowLayout: FlowLayout

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            flowLayout = FlowLayout(this@ConstraintLayoutFlowActivity2).apply {
                layout_width = match_parent
                layout_height = wrap_content
                align_horizontal_to = parent_id
                top_toTopOf = parent_id
                horizontalBias = 0f
                horizontalStyle = packed
                wrapMode = wrap_mode_chain
                verticalGap = 10
                horizontalGap = 10
                margin_start = 5
                margin_end = 5
            }.also { addView(it) }

            TextView {
                layout_id = "tvDelete"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                textColor = "#ffffff"
                text = "delete"
                gravity = gravity_center
                center_vertical = true
                center_horizontal = true
                shape = shape {
                    solid_color = "#00ff00"
                    corner_radius = 20
                }
                onClick = {
                    delete()
                }
            }

            TextView {
                layout_id = "tvAdd"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                textColor = "#ffffff"
                text = "add"
                gravity = gravity_center
                top_toBottomOf = "tvDelete"
                center_horizontal = true
                margin_top = 10
                shape = shape {
                    solid_color = "#00ff00"
                    corner_radius = 20
                }
                onClick = {
                    add()
                }
            }
        }

    }

    private fun add() {
        flowLayout.addTag("dkfjdkfjkdfj")
    }

    private fun delete() {
        flowLayout.removeAllTags()
    }


    private val tags by lazy {
        listOf("天魔", "moshou", "kdfjlklk", "eioroewweio", "dkfjdklfjdsjf", "dklfdslkfjls", "dkfsdlkfjksd")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        tags.forEach { tag ->
            flowLayout.addTag(tag)
        }
    }
}