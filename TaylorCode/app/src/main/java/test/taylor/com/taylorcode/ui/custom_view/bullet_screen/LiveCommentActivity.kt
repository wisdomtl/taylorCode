package test.taylor.com.taylorcode.ui.custom_view.bullet_screen

import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.*

class LiveCommentActivity : AppCompatActivity() {

    private lateinit var container: LinearLayout

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            container = LinearLayout {
                layout_width = 400
                layout_height = 120
                center_horizontal = true
                center_vertical = true
                background_color = "#ff00ff"
            }

            TextView {
                layout_width = wrap_content
                layout_height = wrap_content
                text = "show live comments"
                bottom_toBottomOf = parent_id
                center_horizontal = true
                textSize = 30f
                margin_bottom = 10
                background_color = "#ffff00"
                onClick = {
                    LiveComment.show(CommentBean("1234567891011"))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        LiveComment.apply {
            activity = this@LiveCommentActivity
            anchorView = container
            createView = {
                TextView {
                    layout_width = wrap_content
                    layout_height = 60
                    textColor = "#0000ff"
                    maxLines = 1
                    maxLength = 10
                    ellipsize = ellipsize_end
                    background_color = "#00ff00"
                    gravity = gravity_center
                }
            }

            bindView = { data, view ->
                (view as? TextView)?.text = (data as? CommentBean)?.title
            }

            animateView = { view, rect, width, height ->
                val translationX = -(rect.right - rect.left) - width
                view.animate()
                    .setDuration(3000)
                    .translationXBy(translationX.toFloat())
                    .setInterpolator(LinearInterpolator())
                    .start()
            }
        }
    }
}