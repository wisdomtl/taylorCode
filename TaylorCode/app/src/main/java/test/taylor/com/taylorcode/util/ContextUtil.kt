package test.taylor.com.taylorcode.util

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes

fun <T: View> Activity.view(@IdRes id:Int):T = findViewById(id)