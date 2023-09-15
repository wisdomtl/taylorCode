package test.taylor.com.taylorcode.util

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log


fun isAppInstalled(context: Context, packageName: String?): Boolean {
    return if (packageName.isNullOrEmpty()) {
        false
    } else try {
//        context.packageManager.getApplicationInfo(packageName, 0)
        val info = context.packageManager.getPackageInfo(packageName,PackageManager.GET_ACTIVITIES)
        Log.e("ttaylor22", "info=$info")
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun isPackageInstalled(context: Context, packageName: String?): Boolean {
    val packageManager = context.packageManager
    val intent = packageManager.getLaunchIntentForPackage(packageName!!) ?: return false
    val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    return !list.isEmpty()
}