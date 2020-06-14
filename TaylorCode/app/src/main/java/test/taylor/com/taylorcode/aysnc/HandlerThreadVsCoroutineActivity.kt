package test.taylor.com.taylorcode.aysnc

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import test.taylor.com.taylorcode.kotlin.*

class HandlerThreadVsCoroutineActivity : AppCompatActivity() {

    private lateinit var tvName: TextView

    private val rootView by lazy {
        ConstraintLayout {
            tvName = TextView {
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)

//        val mainHandler = Handler(Looper.getMainLooper())
//        val handlerThread = HandlerThread("user")
//        handlerThread.start()
//        val handler = Handler(handlerThread.looper)
//        runOnUiThread { }
//        handler.post(object : Runnable {
//            override fun run() {
//                val user = fetchUser()
//                mainHandler.post(object : Runnable {
//                    override fun run() {
//                        tvName.text = user.name
//                    }
//                })
//            }
//        })

        GlobalScope.launch {
            val user = async(Dispatchers.IO) { fetchUser() }
        }
    }

    suspend fun fetchUser(): User {
        delay(1000)
        return User("taylor", 20, 0)
    }

//    private fun fetchUser(): User {
//        Thread.sleep(1000)
//        return User("taylor", 20, 0)
//    }
}

data class User(var name: String, var age: Int, var gender: Int)