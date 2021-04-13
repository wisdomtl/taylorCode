package test.taylor.com.taylorcode.ui.one

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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
                    layout_id = "title"
                    max_width = 100
                    text = "title"
                    text_size = 40f
                    text_color = "#ffffff"
                    leftPercent = 0.2f
                    topPercent = 0.2f
                    padding_start = 10
                    padding_end = 10
                    shape = shape {
                        color = "#ff0000"
                        corners = corners{
                            leftBottomRx = 20f
                            LeftBottomRy = 20f
                        }
                    }
                }

                text {
                    max_width = 80
                    text = "content"
                    text_size = 15f
                    text_color ="#88ffffff"
                    top_to_bottom_of = "title"
                    start_to_start_of = "title"
                    gravity = gravity_center
                    padding_start = 8
                    padding_top = 5
                    padding_bottom = 5
                    padding_end = 8
                    shape = shape {
                        color = "#ff0000"
                        radius = 10f
                    }
                }

                text {
                    max_width = 70
                    text = "subTitle"
                    text_size = 18f
                    text_color = "#ffffff"
                    center_vertical_of = "title"
                    start_to_end_of = "title"
                }

                text {
                    max_width = 70
                    text = "left"
                    text_size = 15f
                    text_color = "#ffffff"
                    end_to_start_of = "title"
                    center_vertical_of = "title"
                    right_margin = 10
                }

                text {
                    maxWidth = 70
                    text = "top"
                    text_size = 15f
                    text_color = "#ffffff"
                    bottom_to_top_of = "title"
                    center_horizontal_of = "title"
                    bottom_margin = 20
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }
}