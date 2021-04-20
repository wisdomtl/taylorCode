package test.taylor.com.taylorcode.ui.one

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*

@RequiresApi(Build.VERSION_CODES.M)
class OneActivity : AppCompatActivity() {

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            OneViewGroup(this@OneActivity).apply {
                layout_width = 200
                layout_height = 300
                background_color = "#ff00ff"
                center_horizontal = true
                center_vertical = true

                text {
                    drawable_layout_id = "title"
                    drawable_max_width = 100
                    text = "title"
                    drawable_text_size = 40f
                    drawable_text_color = "#ffffff"
                    leftPercent = 0.2f
                    topPercent = 0.2f
                    drawable_padding_start = 10
                    drawable_padding_end = 10
                    drawableShape = drawableShape {
                        color = "#ff0000"
                        corners = corners{
                            leftBottomRx = 20f
                            LeftBottomRy = 20f
                        }
                    }
                }

                text {
                    drawable_layout_id = "content"
                    drawable_max_width = 80
                    text = "content"
                    drawable_text_size = 15f
                    drawable_text_color ="#88ffffff"
                    drawable_top_to_bottom_of = "title"
                    drawable_start_to_start_of = "title"
                    gravity = gravity_center
                    drawable_padding_start = 8
                    drawable_padding_top = 5
                    drawable_padding_bottom = 5
                    drawable_padding_end = 8
                    drawableShape = drawableShape {
                        color = "#ff0000"
                        radius = 10f
                    }
                }

                text {
                    drawable_max_width = 70
                    text = "subTitle"
                    drawable_text_size = 18f
                    drawable_text_color = "#ffffff"
                    drawable_center_vertical_of = "title"
                    drawable_start_to_end_of = "title"
                }

                text {
                    drawable_max_width = 70
                    text = "left"
                    drawable_text_size = 15f
                    drawable_text_color = "#ffffff"
                    drawable_end_to_start_of = "title"
                    drawable_center_vertical_of = "title"
                    drawable_right_margin = 10
                }

                text {
                    maxWidth = 70
                    text = "top"
                    drawable_text_size = 15f
                    drawable_text_color = "#ffffff"
                    drawable_bottom_to_top_of = "title"
                    drawable_center_horizontal_of = "title"
                    drawable_bottom_margin = 20
                }

                image {
                    drawable_layout_id = "image1"
                    layout_width = 40
                    layout_height = 40
                    scaleType = scale_fit_xy
                    src = R.drawable.old_man
                    drawable_top_to_bottom_of = "content"
                    drawable_start_to_start_of = "content"
                }

                image {
                    drawable_layout_id = "image2"
                    layout_width = 40
                    layout_height = 40
                    scaleType = scale_fit_xy
                    src = R.drawable.old_man
                    drawable_start_to_end_of  = "content"
                    drawable_center_vertical_of = "content"
                }

                setOnItemClickListener {
                    Log.v("ttaylor","id ($it) is clicked")
                }

            }.also { addView(it) }

//            TextView {
//                layout_id = "title"
//                layout_width = wrap_content
//                layout_height = wrap_content
//                textSize = 20f
//                textColor = "#ffffff"
//                text = "title"
//                gravity = gravity_center
//                start_toStartOf = parent_id
//                top_toTopOf = parent_id
//                background_color = "#00ff00"
//            }
//
//            TextView {
//                layout_id = "content"
//                layout_width = wrap_content
//                layout_height = wrap_content
//                textSize = 12f
//                textColor = "#ffffff"
//                text = "content"
//                gravity = gravity_center
//                top_toBottomOf = "title"
//                start_toStartOf = "title"
//                background_color = "#ffff00"
//            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.v("ttaylor","activity ontouch action=${event?.action}")
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.v("ttaylor","activity dispatch  action=${ev?.action}")
        return super.dispatchTouchEvent(ev)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }
}