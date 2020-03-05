package test.taylor.com.taylorcode.gson

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class GsonActivity : AppCompatActivity() {

    var json: String = "{\n" +
            "  \"name\": \"taylor\",\n" +
            "  \"bean\": {\n" +
            "    \"id\": 30\n" +
            "  }\n" +
            "}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        convertTypedBean(json)
    }


    /**
     * gson case: convert generic type bean
     */
    private fun convertTypedBean(json: String) {
        val type = object : TypeToken<Bean1<Bean2>>() {}.type
        val b: Bean1<Bean2> = Gson().fromJson<Bean1<Bean2>>(json, type)
        Log.v("ttaylor","tag=, GsonActivity.convertTypedBean()  id=${b.bean.id}")
    }
}