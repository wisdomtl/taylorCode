package test.taylor.com.taylorcode.ui.custom_view.bullet_screen

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.*

class LiveCommentActivity : AppCompatActivity() {

    private lateinit var container: LinearLayout
    private var tagCount = 1

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

            MyTextView(this@LiveCommentActivity).apply  {
                layout_width = wrap_content
                layout_height = wrap_content
                text = "show live comments"
                bottom_toBottomOf = parent_id
                center_horizontal = true
                textSize = 30f
                margin_bottom = 10
                background_color = "#ffff00"
                tag = "dddd"
                onClick = {
                    LiveComment.show(CommentBean("1234567891011"))
                }
            }.also { addView(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        LiveComment.apply {
            activity = this@LiveCommentActivity
            anchorView = container
            createView = {
                MyTextView(this@LiveCommentActivity).apply {
                    layout_width = wrap_content
                    layout_height = 60
                    textColor = "#0000ff"
                    maxLines = 1
                    maxLength = 10
                    ellipsize = ellipsize_end
                    background_color = "#00ff00"
                    gravity = gravity_center
                    tag = "${tagCount++}"
                }
            }

            bindView = { data, view ->
                (view as? TextView)?.text = (data as? CommentBean)?.title
            }

            animateView = { view, rect, width, height, onAnimationEnd ->
                val translationX = -(rect.right - rect.left) - width
                view.animate()
                    .setDuration(3000)
                    .translationXBy(translationX.toFloat())
                    .setInterpolator(LinearInterpolator())
                    .setListener(object : AnimatorListener {
                        override fun onAnimationStart(animation: Animator?) {
                        }

                        override fun onAnimationEnd(animation: Animator?) {
                            Log.v("ttaylor", "tag=, LiveCommentActivity.onAnimationEnd()  ")
//                            onAnimationEnd(view)
                        }

                        override fun onAnimationCancel(animation: Animator?) {
                        }

                        override fun onAnimationRepeat(animation: Animator?) {
                        }
                    })
                    .start()
            }
        }
    }
}

class MyTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.v("ttaylor", "tag=, MyTextView.onLayout(tag=${tag}) change=${changed},left=$left,top=$top,right=$right,bottom=$bottom")
    }
}