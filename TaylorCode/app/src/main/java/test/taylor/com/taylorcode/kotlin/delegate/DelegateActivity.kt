package test.taylor.com.taylorcode.kotlin.delegate

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class DelegateActivity : AppCompatActivity() {

    private lateinit var pre: Preference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // operator case
        pre = Preference(getSharedPreferences("conid", Context.MODE_PRIVATE))
        pre["a"] = 1
        pre["b",true] = "把"
        pre["c"] = mutableSetOf("cc","dd")
        pre.all.forEach { entry ->
            Log.v("ttaylor", "tag=, DelegateActivity.onCreate()  key-${entry.key},value=${entry.value}")
        }
        pre["a",1]
    }

}