package test.taylor.com.taylorcode.no_field

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import test.taylor.com.taylorcode.kotlin.*

class H5Activity:AppCompatActivity() {

    private val rootView by lazy {
        TextView {
            layout_width = match_parent
            layout_height = match_parent
            text = "h5 activity"
            textSize = 50f
            gravity = gravity_center
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent("h5-back-press"))
    }
}