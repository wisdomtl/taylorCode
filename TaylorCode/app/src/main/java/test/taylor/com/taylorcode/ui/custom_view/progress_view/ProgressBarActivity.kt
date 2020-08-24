package test.taylor.com.taylorcode.ui.custom_view.progress_view

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.R

class ProgressBarActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.progresss_bar)

        findViewById<ProgressBar>(R.id.pb).apply {
            progress = 0.3f
            rx = 30f
            ry = 30f
            progressBackgroundColor  = Color.parseColor("#F5F5F5")
            colors = intArrayOf(Color.parseColor("#FFC39E"),Color.parseColor("#FF6797"))
        }
    }
}