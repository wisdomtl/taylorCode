package test.taylor.com.taylorcode.ui.anim.transitionmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.Scene
import androidx.transition.TransitionManager
import test.taylor.com.taylorcode.kotlin.*

class TransitionManagerActivity : AppCompatActivity() {

    private lateinit var sceneRoot: ConstraintLayout


    val startScene by lazy {
        Scene(sceneRoot, TextView {
            layout_id = "tv"
            layout_width = wrap_content
            layout_height = wrap_content
            textSize = 20f
            textColor = "#3F4658"
            gravity = gravity_center
            center_vertical = true
            start_toStartOf = parent_id
            text = "Scene"
            background_color = "#ff00ff"
        })
    }

    val endScene by lazy {
        Scene(sceneRoot, TextView {
            layout_id = "tv"
            layout_width = wrap_content
            layout_height = wrap_content
            textSize = 20f
            textColor = "#3F4658"
            gravity = gravity_center
            center_vertical = true
            end_toEndOf = parent_id
            text = "Scene"
            background_color = "#ff00ff"
        })
    }

    private val contentView by lazy {
        sceneRoot = ConstraintLayout {
            layout_id = "sceneRoot"
            layout_width = match_parent
            layout_height = match_parent

            TextView {
                layout_id = "tv"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                textColor = "#3F4658"
                gravity = gravity_center
                center_vertical = true
                start_toStartOf = parent_id
                text = "Scene"
                background_color = "#ff00ff"
            }

            Button {
                layout_id = "btn"
                layout_width = wrap_content
                layout_height = wrap_content
                text = "trigger animation"
                textSize = 20f
                center_horizontal = true
                bottom_toBottomOf = parent_id
                onClick = {
                    TransitionManager.go(endScene)
                }
            }

            Button {
                layout_id = "btn2"
                layout_width = wrap_content
                layout_height = wrap_content
                text = "trigger animation2"
                textSize = 20f
                center_horizontal = true
                bottom_toTopOf = "btn"
                onClick = {
                    TransitionManager.beginDelayedTransition(sceneRoot)
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(sceneRoot)
                    constraintSet.clear("tv".toLayoutId(),ConstraintSet.START)
                    constraintSet.connect("tv".toLayoutId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                    constraintSet.applyTo(sceneRoot)
                }
            }

        }

        sceneRoot
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }
}