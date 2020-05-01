package test.taylor.com.taylorcode.kotlin.collection

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline

fun ViewGroup.TextView(init: TextView.() -> Unit) {
    TextView(context).apply(init).also { addView(it) }
}

fun ViewGroup.ImageView(init: ImageView.() -> Unit) {
    ImageView(context).apply(init)
}

fun ViewGroup.Button(init: Button.() -> Unit) {
    Button(context).apply(init)
}

fun ViewGroup.View(init: View.() -> Unit) {
    View(context).apply(init)
}

fun ConstraintLayout.GuideLine(init: Guideline.() -> Unit) {
    Guideline(context).apply(init)
}

fun ConstraintLayout.Flow(init: Flow.() -> Unit) {
    Flow(context).apply(init)
}

fun Context.ConstraintLayout(init: ConstraintLayout.() -> Unit): ConstraintLayout =
    ConstraintLayout(this).apply(init)

fun Context.LinearLayout(init: LinearLayout.() -> Unit): LinearLayout =
    LinearLayout(this).apply(init)

fun Context.FrameLayout(init: FrameLayout.() -> Unit) =
    FrameLayout(this).apply(init)


//var TextView.txt: String
//    get() {
//        return this.text.toString()
//    }
//    set(value) {
//        text = value
//    }


var View.layout_width: Int
    get() {
        return 0
    }
    set(value) {

        val isValid = { lp: ViewGroup.LayoutParams? ->
            when {
                lp == null -> false
                lp.width == 0 -> false
                lp.height == 0 -> false
                else -> true
            }
        }

        val w = if (!isValid(layoutParams)) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            resources.displayMetrics
        ).toInt() else layoutParams.width
        val h = layoutParams?.height ?: 0
        Log.v("ttaylor", "tag=asdf, width.()  w=${w},h=${h}")
        layoutParams = ViewGroup.LayoutParams(w, h)
    }

var View.layout_height: Int
    get() {
        return 0
    }
    set(value) {
        val isValid = { lp: ViewGroup.LayoutParams? ->
            when {
                lp == null -> false
                lp.width == 0 -> false
                lp.height == 0 -> false
                else -> true
            }
        }
        val w = layoutParams?.width ?: 0
        val h = if (!isValid(layoutParams)) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            resources.displayMetrics
        ).toInt() else layoutParams.height
        Log.v("ttaylor", "tag=asdf, height.()  w=${w},h=${h}")
        layoutParams = ViewGroup.LayoutParams(w, h)
    }

