package test.taylor.com.taylorcode.util

import android.content.res.Resources

fun Float.dp(): Int = (Resources.getSystem().displayMetrics.density * this + 0.5).toInt()

