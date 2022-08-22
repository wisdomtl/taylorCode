package test.taylor.com.taylorcode.ui.flow

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*

class FlowLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    var horizontalGap = 5
    var verticalGap = 5
    var wrapMode = wrap_mode_chain
    var horizontalStyle = packed
    var horizontalBias = 0f

    var onCreateTagView = { tag: String ->
        TextView(autoAdd = false) {
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
    }

    private val referenceIds = mutableListOf<String>()

    private var flow = Flow {
        layout_width = 0
        layout_height = wrap_content
        align_horizontal_to = parent_id
        flow_horizontalGap = horizontalGap
        flow_verticalGap = verticalGap
        flow_wrapMode = wrapMode
        flow_horizontalStyle = horizontalStyle
        flow_horizontalBias = horizontalBias
    }

    fun addTag(tag: String) {
        if (referenceIds.contains(tag)) {
            referenceIds.remove(tag)
            referenceIds.add(0, tag)
            flow.reference_ids = referenceIds
            val child = findViewById<View>(tag.toLayoutId())
            removeView(child)
            addView(child)
        } else {
            referenceIds.add(tag)
            onCreateTagView(tag).also {
                addView(it)
                flow.reference_ids = referenceIds
            }
        }
    }

    fun removeAllTags() {
        removeAllViews()
        referenceIds.clear()
    }
}