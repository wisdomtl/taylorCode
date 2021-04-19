package test.taylor.com.taylorcode.ui.touch_event

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView
import kotlinx.android.synthetic.main.touch_event_activity.*
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.onClick
import test.taylor.com.taylorcode.util.print

class TouchEventActivity : Activity() {

    private var expand = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.touch_event_activity)
        initView()

        tvExpand?.onClick = {
            if (expand) {
                tvExppppadn?.maxLines = 2
                val txt = "哈哈的看法打了飞机了第三款就疯啦的时间父类的解放路快速点击弗兰克斯到家了发跨境电商雷锋精神底滤科菲都是浪费酸辣粉来得及福利费解放路快速点击弗兰克斯到家了发跨"
                tvExppppadn.ellipsisText(txt) { expand,showingText ->
                    Log.v("ttaylor", "showing text=$showingText  ")
                }
            } else {
                tvExppppadn?.maxLines = Int.MAX_VALUE
                val txt = "哈哈的看法打了飞机了第三款就疯啦的时间父类的解放路快速点击弗兰克斯到家了发跨境电商雷锋精神底滤科菲都是浪费酸辣粉来得及福利费解放路快速点击弗兰克斯到家了发跨"
                tvExppppadn.ellipsisText(txt) {expand, showingText ->
                    Log.v("ttaylor", "showing text=$showingText  ")
                }
            }
            expand = !expand
        }
    }

    private fun initView() {
//        v_te.setOnClickListener({})
        v_te.setOnClickListener {
            Log.v("ttaylor3","on view clicked")
        }

        tvg.setOnClickListener {
            Log.v("ttaylor3"," view group is clicked")
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val actionmask = event.action and MotionEvent.ACTION_MASK
        (0 until event.pointerCount).map { event.getPointerId(it) }.print { it.toString() }
            .let {
                Log.v(
                    "ttaylor",
                    "TouchEventActivity.dispatchTouchEvent() event = ${event.action} ,isMutiple=${actionmask == MotionEvent.ACTION_POINTER_DOWN}, index = ${event.actionIndex} ,pointer ids=${it}"
                )
            }
//        Log.v("ttaylor", "TouchEventActivity.dispatchTouchEvent()" + "  return " + b);
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.v("ttaylor", "TouchEventActivity.onTouchEvent()  event = ${event.action}")
//        Log.v("ttaylor", "TouchEventActivity.onTouchEvent()" + "  return " + b);
        return super.onTouchEvent(event)
    }

}


fun TextView.ellipsisText(text: String, onMeasure: (Boolean, String) -> Unit) {
    post {
        paint.textSize = textSize
        val totalWidth = paint.measureText(text)
        if (totalWidth > maxLines * width) {
            val lineCount = layout?.lineCount ?: 2
            val ellipsisIndex = layout.getEllipsisCount(lineCount - 1)
            val showingTextIndex = text.length - ellipsisIndex
            val showingText = text.subSequence(0, showingTextIndex)
            onMeasure(false, showingText.toString())
        } else {
            onMeasure(true, text)
        }
    }
}