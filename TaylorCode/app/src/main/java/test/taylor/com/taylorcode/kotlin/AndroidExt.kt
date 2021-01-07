package test.taylor.com.taylorcode.kotlin

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager


fun Context?.startActivitySafe(intent: Intent) {
    this ?: return
    this.packageManager?.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) ?: return
    startActivity(intent)
}