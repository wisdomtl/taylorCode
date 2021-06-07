package test.taylor.com.taylorcode.audio

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.*

@SuppressLint("ClickableViewAccessibility")
class AudioRecorderActivity : AppCompatActivity() {

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            Button {
                layout_id = "btnAudioRecorder"
                layout_width = wrap_content
                layout_height = wrap_content
                text = "start"
                textAllCaps = false
                textSize = 40f
                start_toStartOf = parent_id
                end_toStartOf = "btnMediaRecorder"
                center_vertical = true
                shape = shape {
                    corner_radius = 40
                    solid_color = "#ff00ff"
                }
                setOnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            startAudioRecord()
                        }
                        MotionEvent.ACTION_UP -> {
                            stopAudioRecord()
                        }
                    }

                    false
                }
            }

            TextView {
                layout_id = "tvAudioRecorder"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 12f
                text = "AudioRecorder"
                gravity = gravity_center
                align_horizontal_to = "btnAudioRecorder"
                top_toBottomOf = "btnAudioRecorder"
            }

            Button {
                layout_id = "btnMediaRecorder"
                layout_width = wrap_content
                layout_height = wrap_content
                text = "start"
                textAllCaps = false
                start_toEndOf = "btnAudioRecorder"
                end_toEndOf = parent_id
                center_vertical = true
                textSize = 40f
                shape = shape {
                    corner_radius = 40
                    solid_color = "#ff00ff"
                }
                setOnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            startMediaRecord()
                        }
                        MotionEvent.ACTION_UP -> {
                            stopMediaRecord()
                        }
                    }

                    false
                }

            }

            TextView {
                layout_id = "tvAudioRecorder"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 12f
                text = "MediaRecorder"
                gravity = gravity_center
                align_horizontal_to = "btnMediaRecorder"
                top_toBottomOf = "btnMediaRecorder"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }

    private fun startAudioRecord() {
        Log.v("ttaylor", "startAudioRecord()")

    }

    private fun startMediaRecord() {
        Log.v("ttaylor", "startMediaRecord()")

    }

    private fun stopAudioRecord() {
        Log.v("ttaylor", "stopAudioRecord()")
    }

    private fun stopMediaRecord() {
        Log.v("ttaylor", "stopMediaRecord()")

    }
}

