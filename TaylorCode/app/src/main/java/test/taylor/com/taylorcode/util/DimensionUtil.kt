package test.taylor.com.taylorcode.util

import android.content.res.Resources
import android.util.TypedValue

fun Float.dp(): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    ).toInt()

