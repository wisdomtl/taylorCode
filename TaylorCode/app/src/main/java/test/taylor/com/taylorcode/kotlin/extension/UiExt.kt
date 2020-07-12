package test.taylor.com.taylorcode.kotlin.extension

import android.app.Activity
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment
import test.taylor.com.taylorcode.kotlin.*

fun Activity.contentView(): FrameLayout? =
    takeIf { !isFinishing && !isDestroyed }?.window?.decorView?.findViewById(android.R.id.content)

val Activity.decorView: FrameLayout?
    get() = (takeIf { !isFinishing && !isDestroyed }?.window?.decorView) as? FrameLayout

/**
 * obtain the root view of [DialogFragment]
 */
val DialogFragment.decorView: ViewGroup?
    get() {
        return view?.parent as? ViewGroup
    }

/**
 * obtain the root view of [AppCompatDialog]
 */
val AppCompatDialog.decorView: FrameLayout?
    get() {
        return takeIf { isShowing }?.window?.decorView as FrameLayout
    }

/**
 * toggle night mode in [AppCompatActivity]
 * the algorithm is exactly the same as [DialogFragment.nightMode]
 */
fun Activity.nightMode(lightOff: Boolean, color: String = "#c8000000") {
    val handler = Handler(Looper.getMainLooper())
    val id = "darkMask"
    if (lightOff) {
        handler.postAtFrontOfQueue {
            val maskView = View {
                layout_id = id
                layout_width = match_parent
                layout_height = match_parent
                background_color = color
            }
            decorView?.apply {
                val view = findViewById<View>(id.toLayoutId())
                if (view == null) {
                    addView(maskView)
                }
            }
        }
    } else {
        decorView?.apply {
            find<View>(id)?.let { removeView(it) }
        }
    }
}

/**
 * toggle night mode in [DialogFragment]
 * the algorithm is exactly the same as [AppCompatActivity.nightMode]
 */
fun DialogFragment.nightMode(lightOff: Boolean, color: String = "#c8000000") {
    val handler = Handler(Looper.getMainLooper())
    val id = "darkMask"
    if (lightOff) {
        handler.postAtFrontOfQueue {
            val maskView = View {
                layout_id = id
                layout_width = match_parent
                layout_height = match_parent
                background_color = color
            }
            decorView?.apply {
                val view = findViewById<View>(id.toLayoutId())
                if (view == null) {
                    addView(maskView)
                }
            }
        }
    } else {
        decorView?.apply {
            find<View>(id)?.let { removeView(it) }
        }
    }
}

/**
 * toggle night mode in [AppCompatDialog]
 * the algorithm is exactly the same as [AppCompatActivity.nightMode]
 */
fun AppCompatDialog.nightMode(lightOff: Boolean, color: String = "#c8000000") {
    val handler = Handler(Looper.getMainLooper())
    val id = "darkMask"
    if (lightOff) {
        handler.postAtFrontOfQueue {
            val maskView = View(context).apply {
                setId(id.toLayoutId())
                layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                setBackgroundColor(Color.parseColor(color))
            }
            decorView?.apply {
                val view = findViewById<View>(id.toLayoutId())
                if (view == null) {
                    addView(maskView)
                }
            }
        }
    } else {
        decorView?.apply {
            find<View>(id)?.let { removeView(it) }
        }
    }
}
