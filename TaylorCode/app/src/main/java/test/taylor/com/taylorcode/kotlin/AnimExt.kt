package test.taylor.com.taylorcode.kotlin

import android.animation.Animator
import android.animation.AnimatorSet
import android.util.Log


fun AnimatorSet.addListener(action: AnimatorListenerBuilder.() -> Unit) {
    AnimatorListenerBuilder().apply { action }.let { builder ->
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                animation?.let { builder.onRepeat?.invoke(animation) }
            }

            override fun onAnimationEnd(animation: Animator?) {
                Log.v("ttaylor", "tag=anim-callback, .onAnimationEnd()  ")
                animation?.let { builder.onEnd?.invoke(animation) }
            }

            override fun onAnimationCancel(animation: Animator?) {
                animation?.let { builder.onCancel?.invoke(animation) }
            }

            override fun onAnimationStart(animation: Animator?) {
                Log.v("ttaylor", "tag=anim-callback, .onAnimationStart()  ")
                animation?.let { builder.onStart?.invoke(animation) }
            }

        })
    }
}

class AnimatorListenerBuilder {
    var onRepeat: ((Animator) -> Unit)? = null
    var onEnd: ((Animator) -> Unit)? = null
    var onCancel: ((Animator) -> Unit)? = null
    var onStart: ((Animator) -> Unit)? = null
}

