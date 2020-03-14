package test.taylor.com.taylorcode.kotlin.delegate

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Value SharedPreference delegation
 */
class Preference<T>(var name: String, var mode: Int, private val key: String, private val default: T) : ReadWriteProperty<Context, T> {
    private var sp: SharedPreferences? = null

    override fun getValue(thisRef: Context, property: KProperty<*>): T {
        if (sp == null) sp = thisRef.getSharedPreferences(name, mode)
        return with(sp!!) {
            when (default) {
                is Long -> getLong(key, default)
                is Int -> getInt(key, default)
                is Boolean -> getBoolean(key, default)
                is Float -> getFloat(key, default)
                is String -> getString(key, default)
                else -> throw IllegalArgumentException("unsupported type of value")
            } as T
        }
    }

    override fun setValue(thisRef: Context, property: KProperty<*>, value: T) {
        if (sp == null) sp = thisRef.getSharedPreferences(name, mode)
        with(sp!!.edit()) {
            when (value) {
                is Long -> putLong(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is String -> putString(key, value)
                else -> throw IllegalArgumentException("unsupported type of value")
            }
            apply()
        }
    }

}