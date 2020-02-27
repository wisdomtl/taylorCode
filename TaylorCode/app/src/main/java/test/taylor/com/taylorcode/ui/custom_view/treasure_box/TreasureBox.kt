package test.taylor.com.taylorcode.ui.custom_view.treasure_box

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class TreasureBox @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {

    private var treasures = mutableListOf<Treasure>()

    init {
        //this is must, or no drawing will show in ViewGroup
        setWillNotDraw(false)
    }


    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        (child as? Treasure)?.let { treasure ->
            treasures.add(treasure)
        }
    }

    override fun onViewRemoved(child: View?) {
        super.onViewRemoved(child)
        (child as? Treasure)?.let { treasure ->
            treasures.remove(treasure)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        treasures.forEach { treasure -> treasure.updateLayout(this) }
    }

    override fun onDrawForeground(canvas: Canvas?) {
        super.onDrawForeground(canvas)
        treasures.forEach { treasure -> treasure.drawTreasure(this, canvas) }
    }
}