package test.taylor.com.taylorcode.kotlin

import android.app.Activity
import android.widget.FrameLayout

fun Activity.contentView(): FrameLayout? =
    takeIf { !isFinishing && !isDestroyed }?.window?.decorView?.findViewById(android.R.id.content)