package test.taylor.com.taylorcode.proxy.remote

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Process
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import test.taylor.com.taylorcode.ILocalServiceBoundByRemote
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.aidl.LocalSingleton
import test.taylor.com.taylorcode.aidl.LocalSingletonService

/**
 * a activity in a new process different from app main process
 */
class RemoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.remote_activity)

        findViewById<Button>(R.id.btn_print).setOnClickListener {
            Log.e("ttaylor", "tag=remote value, RemoteActivity.onCreate() pid=${Process.myPid()} count=${RemoteServiceSingleton.count2}")
            Log.e("ttaylor", "tag=remote value, RemoteActivity.onCreate() pid=${Process.myPid()} text=${RemoteServiceSingleton.text}")
            RemoteServiceSingleton.list2.forEach {
                Log.e("ttaylor", "tag=remote value, RemoteActivity.onCreate() pid=${Process.myPid()} element=${it}")
            }
        }

        findViewById<Button>(R.id.btn_print_local).setOnClickListener {
            Log.e("ttaylor", "tag=local value, RemoteActivity.onCreate() pid=${Process.myPid()} string=${iLocalServiceBoundByRemote?.string}")

        }

        findViewById<Button>(R.id.btn_set_local).setOnClickListener {
            iLocalServiceBoundByRemote?.string = "changed by remote activity"
        }


        bindLocalSingletonService()
    }

    /**
     * case: bind a service in main process
     */
    private fun bindLocalSingletonService() {
        Intent(this, LocalSingletonService::class.java).also { bindService(it, serviceConnection, BIND_AUTO_CREATE) }
    }

    private var iLocalServiceBoundByRemote: ILocalServiceBoundByRemote? = null

    private var serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iLocalServiceBoundByRemote = ILocalServiceBoundByRemote.Stub.asInterface(service)
        }
    }

}