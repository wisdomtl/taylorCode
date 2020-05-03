package test.taylor.com.taylorcode.kotlin.collection

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
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
    ImageView(context).apply(init).also { addView(it) }
}

fun ViewGroup.Button(init: Button.() -> Unit) {
    Button(context).apply(init).also { addView(it) }
}

fun ViewGroup.View(init: View.() -> Unit) {
    View(context).apply(init).also { addView(it) }
}

fun ViewGroup.RelativeLayout(init: RelativeLayout.() -> Unit) {
    RelativeLayout(context).apply(init).also { addView(it) }
}

fun ConstraintLayout.GuideLine(init: Guideline.() -> Unit) {
    Guideline(context).apply(init).also { addView(it) }
}

fun ConstraintLayout.Flow(init: Flow.() -> Unit) {
    Flow(context).apply(init).also { addView(it) }
}

fun Context.ConstraintLayout(init: ConstraintLayout.() -> Unit): ConstraintLayout =
    ConstraintLayout(this).apply(init)

fun Context.LinearLayout(init: LinearLayout.() -> Unit): LinearLayout =
    LinearLayout(this).apply(init)

fun Context.FrameLayout(init: FrameLayout.() -> Unit) =
    FrameLayout(this).apply(init)


var View.padding_top: Int
    get() {
        return 0
    }
    set(value) {
        setPadding(paddingLeft, value.dp(), paddingRight, paddingBottom)
    }

var View.padding_bottom: Int
    get() {
        return 0
    }
    set(value) {
        setPadding(paddingLeft, paddingTop, paddingRight, value.dp())
    }

var View.padding_left: Int
    get() {
        return 0
    }
    set(value) {
        setPadding(value.dp(), paddingTop, paddingRight, paddingBottom)
    }

var View.padding_right: Int
    get() {
        return 0
    }
    set(value) {
        setPadding(paddingLeft, paddingTop, value.dp(), paddingBottom)
    }

var View.layout_width: Int
    get() {
        return 0
    }
    set(value) {
        val w = if (value > 0) value.dp() else value
        val h = layoutParams?.height ?: 0
        Log.v("ttaylor", "tag=asdf, width.()  w=${w},h=${h}")
        layoutParams = ViewGroup.LayoutParams(w, h)
    }

var View.layout_height: Int
    get() {
        return 0
    }
    set(value) {

        val w = layoutParams?.width ?: 0
        val h = if (value > 0) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            resources.displayMetrics
        ).toInt() else value
        Log.v("ttaylor", "tag=asdf, height.()  w=${w},h=${h}")
        layoutParams = ViewGroup.LayoutParams(w, h)
    }


var ImageView.src: Int
    get() {
        return -1
    }
    set(value) {
        setImageResource(value)
    }


var TextView.textStyle: Int
    get() {
        return -1
    }
    set(value) = setTypeface(typeface, value)

var View.alignParentStart: Boolean
    get() {
        return false
    }
    set(value) {
        if (!value) return
        layoutParams = RelativeLayout.LayoutParams(layoutParams.width, layoutParams.height).apply {
            (layoutParams as? RelativeLayout.LayoutParams)?.rules?.forEachIndexed { index, i ->
                addRule(index,i)
            }
            addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
        }
    }

var View.alignParentEnd: Boolean
    get() {
        return false
    }
    set(value) {
        if (!value) return
        layoutParams = RelativeLayout.LayoutParams(layoutParams.width, layoutParams.height).apply {
            (layoutParams as? RelativeLayout.LayoutParams)?.rules?.forEachIndexed { index, i ->
                addRule(index,i)
            }
            addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
        }
    }

var View.centerVertical: Boolean
    get() {
        return false
    }
    set(value) {
        if (!value) return
        layoutParams = RelativeLayout.LayoutParams(layoutParams.width, layoutParams.height).apply {
            (layoutParams as? RelativeLayout.LayoutParams)?.rules?.forEachIndexed { index, i ->
                addRule(index,i)
            }
            addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        }
    }

var View.centerInParent: Boolean
    get() {
        return false
    }
    set(value) {
        if (!value) return
        layoutParams = RelativeLayout.LayoutParams(layoutParams.width, layoutParams.height).apply {
            (layoutParams as? RelativeLayout.LayoutParams)?.rules?.forEachIndexed { index, i ->
                addRule(index,i)
            }
            addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        }
    }

var View.backgroundColor:Int
    get() {
        return  -1
    }
    set(value) {
        setBackgroundColor(value)
    }

val match_parent = ViewGroup.LayoutParams.MATCH_PARENT
val wrap_content = ViewGroup.LayoutParams.WRAP_CONTENT


val horizontal = LinearLayout.HORIZONTAL
val vertical = LinearLayout.VERTICAL

val bold = Typeface.BOLD
val normal = Typeface.NORMAL
val italic = Typeface.ITALIC
val bold_italic = Typeface.BOLD_ITALIC

fun Int.dp(): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()