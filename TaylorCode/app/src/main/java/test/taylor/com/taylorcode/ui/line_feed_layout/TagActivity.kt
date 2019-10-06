package test.taylor.com.taylorcode.ui.line_feed_layout

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.tag_actiivty.*
import test.taylor.com.taylorcode.R

class TagActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tag_actiivty)
        addTags()
    }

    private fun addTags() {
        lineFeed.addView(TextView(this).apply{
            text ="11111111"
            textSize = 30f
            setTextColor(Color.parseColor("#ff00ff"))
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                topMargin = 50
            }
        })
        lineFeed.addView(TextView(this).apply{
            text ="222222"
            textSize = 30f
            setTextColor(Color.parseColor("#ff00ff"))
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                leftMargin = 50
                topMargin = 50
            }
        })
        lineFeed.addView(TextView(this).apply{
            text ="33333"
            textSize = 30f
            setTextColor(Color.parseColor("#ff00ff"))
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                leftMargin = 50
                topMargin = 50
            }
        })
        lineFeed.addView(TextView(this).apply{
            text ="4444444"
            textSize = 30f
            setTextColor(Color.parseColor("#ff00ff"))
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                leftMargin = 40
                topMargin = 50
            }
        })
        lineFeed.addView(TextView(this).apply{
            text ="55555"
            textSize = 30f
            setTextColor(Color.parseColor("#ff00ff"))
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                leftMargin = 40
                topMargin = 50
            }
        })
        lineFeed.addView(TextView(this).apply{
            text ="66666"
            textSize = 30f
            setTextColor(Color.parseColor("#ff00ff"))
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                leftMargin = 40
                topMargin = 50
            }
        })
    }
}