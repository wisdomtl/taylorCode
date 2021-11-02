package test.taylor.com.taylorcode.data_persistence

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TestBean3Converter {
    @TypeConverter
    fun beanToString(bean: List<TestBean2>): String {
        return Gson().toJson(bean)
    }

    @TypeConverter
    fun stringToBean(str: String): List<TestBean2> {
        val type = object : TypeToken<List<TestBean2>>() {}.type
        val bean = Gson().fromJson<List<TestBean2>>(str, type)
        return bean
    }
}