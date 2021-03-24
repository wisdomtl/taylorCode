package test.taylor.com.taylorcode.ui

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.ViewGroupUtils
import test.taylor.com.taylorcode.kotlin.*

class TouchDelegateActivity : AppCompatActivity() {

    private lateinit var f1: FrameLayout
    private lateinit var f2: FrameLayout
    private lateinit var f3: FrameLayout
    private lateinit var t1: TextView

    private val contentView by lazy {
        f1 = FrameLayout {
            layout_width = match_parent
            layout_height = match_parent
            background_color = "#000000"

            f2 = FrameLayout {
                layout_width = 400
                layout_height = 400
                background_color = "#0000ff"
                margin_start = 2
                margin_top = 2

                f3 = FrameLayout {
                    layout_width = 200
                    layout_height = 200
                    background_color = "#00ff00"
                    margin_start = 2
                    margin_top = 2

                    t1 = TextView {
                        layout_id = "tvChange"
                        layout_width = 100
                        layout_height = 100
                        textSize = 30f
                        textColor = "#ffffff"
                        text = "save"
                        gravity = gravity_center
                        background_color = "#ff00ff"
                        margin_start = 2
                        margin_top = 2
                    }
                }
            }
        }
        f1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        f1.post {
            Rect().also { f3.offsetDescendantRectToMyCoords(t1,it) }.let { Log.v("ttaylor","t1 in f3 rect = $it") }
            Rect().also { f2.offsetDescendantRectToMyCoords(t1,it) }.let { Log.v("ttaylor","t1 in f2 rect = $it") }
            Rect().also { f1.offsetDescendantRectToMyCoords(t1,it) }.let { Log.v("ttaylor","t1 in f1 rect = $it") }
            Rect().also { ViewGroupUtils.getDescendantRect(f1,t1,it) }.let { Log.v("ttaylor","t1 in f1 rect = $it") }
        }
    }
}



