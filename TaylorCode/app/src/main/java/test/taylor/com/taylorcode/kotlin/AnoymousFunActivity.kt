package test.taylor.com.taylorcode.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class AnoymousFunActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listOf("a", "b", "c").forEach filter@{
            if (it.startsWith("a")) return@filter
            Log.v("ttaylor", "tag=tag, AnoymousFunActivity.onCreate()  it=${it}")
        }


        listOf("a", "b", "c").forEach(fun(it: String) {
            if (it.startsWith("a")) return
            Log.e("ttaylor", "tag=tag, AnoymousFunActivity.onCreate()  it=${it}")
        })
        listOf("a", "b", "c").forEach {
            if (it.startsWith("a")) return
            Log.d("ttaylor", "tag=tag, AnoymousFunActivity.onCreate()  it=${it}")
        }
    }


}