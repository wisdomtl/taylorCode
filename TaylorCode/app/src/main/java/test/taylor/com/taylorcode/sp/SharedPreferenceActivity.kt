package test.taylor.com.taylorcode.sp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import test.taylor.com.taylorcode.util.print

class SharedPreferenceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sp: SharedPreferences = getSharedPreferences("spList", Context.MODE_PRIVATE)

        //gson case: convert bean to string
        val json = Gson().toJson(generateSpBean())
        Log.v("ttaylor", "tag=, SharedPreferenceActivity.onCreate()  gson=${json}")
        sp.edit().putString("beanString", json).commit()


        val jsonFromSp = sp.getString("beanString", "")
        val type = object : TypeToken<List<SpBean>>() {}.type
        val bean = Gson().fromJson<List<SpBean>>(jsonFromSp, type)
        bean.print { "${it.id},${it.read}" }.also { Log.v("ttaylor", "tag=, SharedPreferenceActivity.onCreate()  bean from json=${it}") }

    }

    private fun generateSpBean(): List<SpBean> = mutableListOf<SpBean>().apply {
        add(SpBean("123", false))
        add(SpBean("223", false))
        add(SpBean("23", false))
    }


}