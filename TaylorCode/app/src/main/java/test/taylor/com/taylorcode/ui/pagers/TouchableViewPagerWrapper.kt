package test.taylor.com.taylorcode.ui.pagers

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

class TouchableViewPagerWrapper @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {
    var dispatchEvent: ((MotionEvent?) -> Unit)? = null

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        dispatchEvent?.invoke(ev)
        return super.dispatchTouchEvent(ev)
    }
}