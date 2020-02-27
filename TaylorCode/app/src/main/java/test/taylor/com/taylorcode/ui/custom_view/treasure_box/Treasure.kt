package test.taylor.com.taylorcode.ui.custom_view.treasure_box

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.util.print

class Treasure @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var ids = mutableListOf<Int>()
    private var offsetXs = mutableListOf<Float>()
    private var offsetYs = mutableListOf<Float>()
    private var radiuses = mutableListOf<Float>()
    private var bgPaint: Paint = Paint()

    private val DEFAULT_RADIUS = 10F

    var radius: Float = 20f

    init {
        readAttrs(attrs)
        initPaint()
    }

    private fun initPaint() {
        bgPaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = Color.parseColor("#ff0000")
        }
    }

    fun updateLayout(treasureBox: TreasureBox) {

    }

    fun drawTreasure(viewGroup: ViewGroup, canvas: Canvas?) {
        ids.forEachIndexed { index, id ->
            viewGroup.findViewById<View>(id)?.let { v ->
                radiuses.getOrElse(index) { DEFAULT_RADIUS }.dp2px().let { radius ->
                    val cx = v.right - radius + offsetXs.getOrElse(index) { 0F }.dp2px()
                    val cy = v.top + radius + offsetYs.getOrElse(index) { 0F }.dp2px()
                    canvas?.drawCircle(cx, cy, radius, bgPaint)
                }
            }
        }
    }

    private fun readAttrs(attributeSet: AttributeSet?) {
        attributeSet?.let { attrs ->
            context.obtainStyledAttributes(attrs, R.styleable.Treasure)?.let {
                divideIds(it.getString(R.styleable.Treasure_reference_ids))
                divideRadiuses(it.getString(R.styleable.Treasure_reference_radius))
                dividerOffsets(it.getString(R.styleable.Treasure_reference_offsetX), it.getString(R.styleable.Treasure_reference_offsetY))
                it.recycle()
            }
        }
    }

    private fun dividerOffsets(offsetXString: String?, offsetYString: String?) {
        offsetXString?.split(",")?.forEach { offset -> offsetXs.add(offset.trim().toFloat()) }
        offsetYString?.split(",")?.forEach { offset -> offsetYs.add(offset.trim().toFloat()) }
    }

    private fun divideRadiuses(radiusString: String?) {
        radiusString?.split(",")?.forEach { radius -> radiuses.add(radius.trim().toFloat()) }
    }

    private fun divideIds(idString: String?) {
        idString?.split(",")?.forEach { id ->
            ids.add(resources.getIdentifier(id.trim(), "id", context.packageName))
        }
        ids.toCollection(mutableListOf()).print("ids") { it.toString() }
    }

    private fun Float.dp2px(): Float {
        val scale = Resources.getSystem().displayMetrics.density
        return this * scale + 0.5f
    }
}