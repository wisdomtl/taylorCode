package test.taylor.com.taylorcode.ui.line_feed_layout

import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
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
            text ="11111111111"
            textSize = 20f
            setTextColor(Color.parseColor("#ff00ff"))
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        })
        lineFeed.addView(TextView(this).apply{
            text ="222222"
            textSize = 20f
            setTextColor(Color.parseColor("#ff00ff"))
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                leftMargin = 20
            }
        })
        lineFeed.addView(TextView(this).apply{
            text ="333333333"
            textSize = 20f
            setTextColor(Color.parseColor("#ff00ff"))
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                leftMargin = 20
            }
        })
        lineFeed.addView(TextView(this).apply{
            text ="4444444"
            textSize = 20f
            setTextColor(Color.parseColor("#ff00ff"))
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                leftMargin = 20
                topMargin = 30
            }
        })
    }
}