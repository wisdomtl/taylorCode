package test.taylor.com.taylorcode.aysnc.priority

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.delay
import test.taylor.com.taylorcode.kotlin.*

class SplashActivity : AppCompatActivity() {
    private val rootView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent
            TextView {
                layout_width = wrap_content
                layout_height = wrap_content
                text = "Splash activity"
                textSize = 30f
                center_vertical = true
                center_horizontal = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)

        SuspendList.of("start-up") {
            Item {
                suspendAction = { fetchUpdateInfo() }
                resumeAction = { resumeUpdateInfo() }
                priority = 2
            }
        }

        delaySplash()
    }

    private fun delaySplash() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
                {
                    startActivity(Intent(this@SplashActivity, PriorityActivity::class.java))
                    finish()
                },
                5000
        )
    }

    private fun resumeUpdateInfo() {
        Log.v("ttaylor", "tag=suspend list, SplashActivity.resumeUpdateInfo()  thread id =${Thread.currentThread().id}")
    }

    suspend fun fetchUpdateInfo() {
        delay(2000)
    }
}