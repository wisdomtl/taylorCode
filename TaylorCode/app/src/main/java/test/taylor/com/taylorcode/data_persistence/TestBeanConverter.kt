package test.taylor.com.taylorcode.data_persistence

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TestBeanConverter {
    @TypeConverter
    fun beanToString(bean: TestBean): String {
        return Gson().toJson(bean)
    }

    @TypeConverter
    fun stringToBean(str: String): TestBean {
        val type = object : TypeToken<TestBean>() {}.type
        val bean = Gson().fromJson<TestBean>(str, type)
        return bean
    }
}