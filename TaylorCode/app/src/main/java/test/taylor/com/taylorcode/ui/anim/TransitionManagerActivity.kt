package test.taylor.com.taylorcode.ui.anim

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.transition_activity.*
import test.taylor.com.taylorcode.R

class TransitionManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transition_activity)

        btnAnim.setOnClickListener {
            val constraintSet = ConstraintSet()
            constraintSet.clone(this@TransitionManagerActivity,R.layout.transition_activity2)

            val transition = ChangeBounds()
            transition.interpolator = AnticipateOvershootInterpolator(1.0f)
            transition.duration = 1200
            TransitionManager.beginDelayedTransition(layoutroot22,transition)
            constraintSet.applyTo(layoutroot22)
        }
        btnReverseAnim.setOnClickListener {
        }
    }
}