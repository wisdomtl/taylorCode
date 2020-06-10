package test.taylor.com.taylorcode.no_field

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.startActivity


/**
 * case: there is no business data field in Activity. Use coroutine instead
 */
class NoFieldActivity : AppCompatActivity(),CoroutineScope by MainScope() {

    private val completeDeferred = CompletableDeferred<String>()

    private val rootView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            Button {
                layout_width = match_parent
                layout_height = wrap_content
                text = "start h5"
                top_toTopOf = parent_id
                center_horizontal = true
                onClick = startH5
            }
        }
    }

    private val startH5 = {_: View ->
        val activityId = "a1"
        startActivity<H5Activity>()

        // activity id wont be a field in Activity
        launch {
            completeDeferred.await()
            recordActivityId(activityId)
        }
        Unit
    }

    private fun recordActivityId(activityId: String) {
        Log.v("ttaylor","tag=, NoFieldActivity.recordActivityId()  activity id=${activityId}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)
        LocalBroadcastManager.getInstance(this).registerReceiver(h5Receiver, IntentFilter("h5-back-press"))
    }

    private val h5Receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            completeDeferred.complete("")
        }
    }
}