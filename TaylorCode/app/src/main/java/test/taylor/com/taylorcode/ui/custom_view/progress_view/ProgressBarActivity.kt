package test.taylor.com.taylorcode.ui.custom_view.progress_view

import android.os.Build
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
            barColor  = "#F5F5F5"
            padding = 1f
            orientation = ProgressBar.VERTICAL
            colors = arrayOf("#FFC39E","#FF6797")
        }
    }
}