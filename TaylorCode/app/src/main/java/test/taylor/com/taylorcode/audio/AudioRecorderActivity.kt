package test.taylor.com.taylorcode.audio

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import test.taylor.com.taylorcode.kotlin.*
import java.io.File

@SuppressLint("ClickableViewAccessibility")
class AudioRecorderActivity : AppCompatActivity() {

    private var audioFile: File? = null
    private val audioManager by lazy {
        AudioManager(this, AudioManager.PCM).apply {
            onRecordSuccess = { file: File, l: Long ->
                audioFile = file
            }
        }
    }

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
                textSize = 30f
                start_toStartOf = parent_id
                end_toStartOf = "btnMediaRecorder"
                center_vertical = true
                padding = 10
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
                padding = 10
                center_vertical = true
                textSize = 30f
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
                layout_id = "tvMediaRecorder"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 12f
                text = "MediaRecorder"
                gravity = gravity_center
                align_horizontal_to = "btnMediaRecorder"
                top_toBottomOf = "btnMediaRecorder"
            }

            TextView {
                layout_id = "tvPlayAudioRecord"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                textColor = "#ffffff"
                text = "play"
                gravity = gravity_center
                padding = 10
                align_horizontal_to = "tvAudioRecorder"
                top_toBottomOf = "tvAudioRecorder"
                shape = shape {
                    corner_radius = 20
                    solid_color = "#0000ff"
                }
                onClick = {
                    audioFile?.absolutePath?.let {
                        AudioTrackManager.instance?.startPlay(it)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, listOf(Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE).toTypedArray(), 1)
            }
        }
    }

    private fun startAudioRecord() {
        Log.v("ttaylor", "startAudioRecord()")
        audioManager.start()
    }

    private fun startMediaRecord() {
        Log.v("ttaylor", "startMediaRecord()")

    }

    private fun stopAudioRecord() {
        Log.v("ttaylor", "stopAudioRecord()")
        audioManager.stop()
    }

    private fun stopMediaRecord() {
        Log.v("ttaylor", "stopMediaRecord()")

    }
}

