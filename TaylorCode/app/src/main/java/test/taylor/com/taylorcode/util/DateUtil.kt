package test.taylor.com.taylorcode.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun formatDate(millisecond: Long,pattern:String): String? {
        val date = Date(millisecond)
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(date)
    }
}