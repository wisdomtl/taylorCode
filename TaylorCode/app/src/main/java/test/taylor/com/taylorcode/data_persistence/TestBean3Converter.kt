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
        //其实不需要这样转换，可以直接使用public <T> T fromJson(String json, Class<T> classOfT),只有对于泛型才需要使用下面这个方法
        val bean = Gson().fromJson<List<TestBean2>>(str, type)
        return bean
    }
}