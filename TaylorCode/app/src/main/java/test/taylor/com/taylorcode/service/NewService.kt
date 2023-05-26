package test.taylor.com.taylorcode.service

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import test.taylor.com.taylorcode.proxy.remote.RemoteService

class NewService : Service() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("bound-service-in-service", "NewService.onCreate()");
        bindService(Intent(this.applicationContext, RemoteService::class.java),object :ServiceConnection{
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.i("bound-service-in-service", "NewService.onServiceConnected()");
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.i("bound-service-in-service", "NewService.onServiceDisconnected()");
            }

        }, BIND_AUTO_CREATE)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i("bound-service-in-service", "NewService.onUnbind()");
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.i("bound-service-in-service", "NewService.onDestroy()");
        super.onDestroy()
    }
}