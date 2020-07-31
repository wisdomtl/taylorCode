package test.taylor.com.taylorcode.dns

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.net.Inet6Address
import java.net.InetAddress

class DnsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val handlerThread = HandlerThread("dns")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        handler.post {
            try {
                val baiduIps = Inet6Address.getAllByName("www.baidu.com")
                baiduIps.forEach {
                    Log.v(
                        "ttaylor",
                        "tag=Inet6Address.getAllByName, DnsActivity.onCreate()  baidu host=${it.hostName},address=${it.address},hostAdreess=${it.hostAddress}"
                    )
                }
            } catch (e: Exception) {
                Log.v("ttaylor","tag=Inet6Address.getAllByName, DnsActivity.onCreate() exception=${e.message},cause=${e.cause} ")
            }
        }

    }


}