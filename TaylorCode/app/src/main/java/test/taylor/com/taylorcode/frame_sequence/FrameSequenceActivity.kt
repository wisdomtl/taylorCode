package test.taylor.com.taylorcode.frame_sequence

import android.os.Bundle
import android.support.rastermill.FrameSequence
import android.support.rastermill.FrameSequenceDrawable
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*

class FrameSequenceActivity:AppCompatActivity() {

    private lateinit var iv:ImageView

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            iv = ImageView {
                layout_id = "ivChange"
                layout_width = 50
                layout_height = 50
                center_horizontal = true
                center_vertical = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        val inputStream = resources.openRawResource(R.raw.aaa)
        val drawable = FrameSequenceDrawable(FrameSequence.decodeStream(inputStream))
        iv.setImageDrawable(drawable)
    }
}