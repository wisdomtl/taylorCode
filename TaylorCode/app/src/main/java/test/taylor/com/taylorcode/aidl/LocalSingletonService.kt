package test.taylor.com.taylorcode.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * a bound service make LocalSingleton could be access by a remote activity
 */
class LocalSingletonService: Service() {

    private val binder = LocalSingleton
    override fun onBind(intent: Intent?): IBinder? {
        Log.d("ttaylor","tag=, LocalSingletonService.onBind()  ")
        return binder
    }


    override fun onCreate() {
        Log.d("ttaylor","tag=, LocalSingletonService.onCreate()  ")
        super.onCreate()
    }
}