package test.taylor.com.taylorcode.audio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.*

class AudioRecorderActivity : AppCompatActivity() {

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            Button {
                layout_id = "btnStart"
                layout_width = wrap_content
                layout_height = wrap_content
                text = "start record"
                textAllCaps = false
                start_toStartOf = parent_id
                end_toStartOf = "btnStop"
                center_vertical = true
            }

            Button {
                layout_id = "btnStop"
                layout_width = wrap_content
                layout_height = wrap_content
                text = "stop record"
                textAllCaps = false
                start_toEndOf = "btnStart"
                end_toEndOf = parent_id
                center_vertical = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }


}