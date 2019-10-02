package test.taylor.com.taylorcode.proxy.remote

import android.os.Bundle
import android.os.Process
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import test.taylor.com.taylorcode.R

class RemoteActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.remote_activity)

        findViewById<Button>(R.id.btn_print).setOnClickListener {
            Log.e("ttaylor","tag=remote value, RemoteActivity.onCreate() pid=${Process.myPid()} count=${RemoteServiceSingleton.count2}")
            Log.e("ttaylor","tag=remote value, RemoteActivity.onCreate() pid=${Process.myPid()} text=${RemoteServiceSingleton.text}")
            RemoteServiceSingleton.list2.forEach{
                Log.e("ttaylor","tag=remote value, RemoteActivity.onCreate() pid=${Process.myPid()} element=${it}")
            }
        }
    }
}