package test.taylor.com.taylorcode.aysnc.priority

import android.os.Bundle
import android.util.Log
import android.view.View
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
                onClick = onObserve
            }
        }
    }

    private val onObserve = {_: View ->
        val pr = Priority()
        pr.add(PriorityItem().apply {
            suspendAction = { fetchUser() }
            resumeAction = { Log.v("ttaylor", "tag=, fetch user resume  thread id=${Thread.currentThread().id}") }
            priority = 3
        })

        pr.add(PriorityItem().apply {
            suspendAction = { fetchActivity() }
            resumeAction = { Log.v("ttaylor", "tag=, fetch activity resume  thread id=${Thread.currentThread().id}") }
            priority = 1
        })

        pr.add(PriorityItem().apply {
            suspendAction = { fetchUpdateInfo() }
            resumeAction = { Log.v("ttaylor", "tag=, fetch update info resume  thread id=${Thread.currentThread().id}") }
            priority = 2
        })

        pr.observe()
        Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)

    }

    suspend fun fetchUser() {
        Log.v("ttaylor"," PriorityActivity.fetchUser()  thread id=${Thread.currentThread().id}")
        delay(4000)
        Log.w("ttaylor"," PriorityActivity.fetchUser()  thread id=${Thread.currentThread().id}")
    }

    suspend fun fetchActivity() {
        Log.v("ttaylor"," PriorityActivity.fetchActivity()  thread id=${Thread.currentThread().id}")
        delay(5000)
        Log.w("ttaylor"," PriorityActivity.fetchActivity()  thread id=${Thread.currentThread().id}")
    }

    suspend fun fetchUpdateInfo() {
        Log.v("ttaylor"," PriorityActivity.fetchUpdateInfo()  thread id=${Thread.currentThread().id}")
        delay(8000)
        Log.w("ttaylor"," PriorityActivity.fetchUpdateInfo()  thread id=${Thread.currentThread().id}")
    }
}