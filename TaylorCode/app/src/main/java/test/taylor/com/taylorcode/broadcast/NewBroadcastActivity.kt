package test.taylor.com.taylorcode.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class NewBroadcastActivity : AppCompatActivity() {
    private val receiver: LazyBroadcastReceiver = LazyBroadcastReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("ttaylor","tag=ttreceiver, NewBroadcastActivity.onCreate()  ")
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, IntentFilter("lazy-register"))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("ttaylor","tag=ttreceiver, NewBroadcastActivity.onDestroy()  ")
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
    }

    class LazyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.v("ttaylor", "tag=, LazyBroadcastReceiver.onReceive()  ")
        }
    }
}