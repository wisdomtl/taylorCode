package test.taylor.com.taylorcode.ui.custom_view.bullet_screen

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator

class LiveCommentView2 @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(
    context,
    attrs,
    defStyleAttr
) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
//        var width = MeasureSpec.getSize(widthMeasureSpec)
//        var height = MeasureSpec.getSize(heightMeasureSpec)
//        //countDownLatch.countDown();
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }

    override fun addView(child: View?) {
        val w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        val h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        child?.measure(w, h)
        super.addView(child)
        val left = measuredWidth
        val top = 200
        child?.layout(left, top, left + child.measuredWidth, top + child.measuredHeight)
        val animator = child?.animate()
        animator?.setDuration(3000)
            ?.setInterpolator(LinearInterpolator())
            ?.setUpdateListener {
                val value = it.animatedFraction
                val left = (measuredWidth - value * (measuredWidth + child.measuredWidth)).toInt()
                child.layout(left, top, left + child.measuredWidth, top + child.measuredHeight)
            }
            ?.start()

    }
}