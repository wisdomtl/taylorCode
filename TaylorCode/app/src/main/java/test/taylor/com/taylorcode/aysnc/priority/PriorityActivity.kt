package test.taylor.com.taylorcode.aysnc.priority

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.delay
import test.taylor.com.taylorcode.kotlin.*

class PriorityActivity : AppCompatActivity() {

    private val rootView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent
            TextView {
                layout_width = wrap_content
                layout_height = wrap_content
                text = "observe"
                center_vertical = true
                center_horizontal = true
            }
        }
    }

    private val onObserve = {
        val pr = SuspendList.of("start-up")
        pr.add(suspendItem {
            suspendAction = { fetchUser() }
            resumeAction = { onUserResume() }
            priority = 3
        })

        pr.add(suspendItem {
            suspendAction = { fetchActivity() }
            resumeAction = { onActivityResume() }
            priority = 1
        })

        pr.observe()
        Unit
    }


    private fun onActivityResume() {
        Log.v("ttaylor", "tag=suspend list, fetch activity resume  thread id=${Thread.currentThread().id}")
    }

    private fun onUserResume() {
        Log.v("ttaylor", "tag=suspend list, fetch user resume  thread id=${Thread.currentThread().id}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)

        onObserve()
    }

    suspend fun fetchUser() {
        Log.v("ttaylor", " PriorityActivity.fetchUser()  thread id=${Thread.currentThread().id}")
        delay(4000)
        Log.w("ttaylor", " PriorityActivity.fetchUser()  thread id=${Thread.currentThread().id}")
    }

    suspend fun fetchActivity() {
        Log.v("ttaylor", " PriorityActivity.fetchActivity()  thread id=${Thread.currentThread().id}")
        delay(5000)
        Log.w("ttaylor", " PriorityActivity.fetchActivity()  thread id=${Thread.currentThread().id}")
    }

}