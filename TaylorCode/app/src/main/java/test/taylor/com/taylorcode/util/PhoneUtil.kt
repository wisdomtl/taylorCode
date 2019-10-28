package test.taylor.com.taylorcode.util

object PhoneUtil {
    fun getBrand() = android.os.Build.BRAND

    fun getSystemModel() = android.os.Build.MODEL

    fun getSystemVersion() = android.os.Build.VERSION.RELEASE
}