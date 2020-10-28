package test.taylor.com.taylorcode.ui.custom_view.progress_view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.R

class ProgressBarActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var tvProgress: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.progresss_bar)

        progressBar = findViewById<ProgressBar>(R.id.pb)
        tvProgress = findViewById<TextView>(R.id.tvProgress)

        /**
         * case 1: solid color progress
         */
//        findViewById<ProgressBar>(R.id.pb).apply {
//            percentage = 30
//            backgroundRx = 20f
//            backgroundRy = 20f
//            backgroundColor = "#e9e9e9"
//            progressRx = 15f
//            progressRy = 15f
//            padding = 2f
//            progress = SolidColorProgress("#ff00ff")
//        }
        /**
         * case 2: gradient progress according to state
         */
        progressBar.apply {
            backgroundRx = 20f
            backgroundRy = 20f
            backgroundColor = "#F5F5F5"
            progressRx = 15f
            progressRy = 15f
            padding = 3f
            progress = stateListOf(
                0..19 to arrayOf(0x8000FFE5, 0x80E7FFAA),
                20..59 to arrayOf(0xFF00FFE5, 0xFFE7FFAA),
                60..79 to arrayOf(0xCCFE579B, 0xCCF9FF19),
                80..100 to arrayOf(0xFFFE579B, 0xFFF9FF19)
            )
            percentage = 10
            postDelayed(runnable, 500)
        }
    }


    val runnable: Runnable = object : Runnable {
        override fun run() {
            progressBar.percentage += 5
            tvProgress.text = progressBar.percentage.toString()
            if (progressBar.percentage < 100) {
                progressBar.postDelayed(this, 200)
            }
        }
    }
}