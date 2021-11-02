package test.taylor.com.taylorcode.data_persistence

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TestBean2Converter {
    @TypeConverter
    fun beanToString(bean: TestBean2): String {
        return Gson().toJson(bean)
    }

    @TypeConverter
    fun stringToBean(str: String): TestBean2 {
        val type = object : TypeToken<TestBean2>() {}.type
        val bean = Gson().fromJson<TestBean2>(str, type)
        return bean
    }
}