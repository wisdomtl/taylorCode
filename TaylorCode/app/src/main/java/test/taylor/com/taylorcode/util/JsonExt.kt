package test.taylor.com.taylorcode.util

import org.json.JSONArray
import org.json.JSONException

@Throws(JSONException::class)
fun <T> JSONArray.toList():List<T>{
    val list = mutableListOf<T>()
    for(i in 0 until  this.length()){
        val value = this.get(i) as? T
        value?.also { list.add(it) }
    }
    return list
}