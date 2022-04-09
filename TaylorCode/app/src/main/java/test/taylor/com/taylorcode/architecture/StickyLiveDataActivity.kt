package test.taylor.com.taylorcode.architecture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.*

class StickyLiveDataActivity : AppCompatActivity() {

    private val contentView by lazy {
        ConstraintLayout {
            layout_id = "container"
            layout_width = match_parent
            layout_height = match_parent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        supportFragmentManager.beginTransaction().add("container".toLayoutId(), TrolleyFragment()).commit()
    }
}