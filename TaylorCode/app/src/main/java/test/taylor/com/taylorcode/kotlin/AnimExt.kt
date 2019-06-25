package test.taylor.com.taylorcode.kotlin

import android.animation.Animator
import android.animation.AnimatorSet
import android.util.Log


fun AnimatorSet.addListener(action: AnimatorListenerBuilder.() -> Unit) {
    val listenerBuilder = AnimatorListenerBuilder().apply{action}
    addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
            animation?.let { listenerBuilder.repeatAction?.invoke(animation) }
        }

        override fun onAnimationEnd(animation: Animator?) {
            Log.v("ttaylor","tag=anim-callback, .onAnimationEnd()  ")
            animation?.let { listenerBuilder.endAction?.invoke(animation) }
        }

        override fun onAnimationCancel(animation: Animator?) {
            animation?.let { listenerBuilder.cancelAction?.invoke(animation) }
        }

        override fun onAnimationStart(animation: Animator?) {
            Log.v("ttaylor","tag=anim-callback, .onAnimationStart()  ")
            animation?.let { listenerBuilder.startActon?.invoke(animation) }
        }

    })
}

class AnimatorListenerBuilder {
    var repeatAction: ((Animator) -> Unit)? = null
    var endAction: ((Animator) -> Unit)? = null
    var cancelAction: ((Animator) -> Unit)? = null
    var startActon: ((Animator) -> Unit)? = null

    fun onRepeat(action: (Animator) -> Unit) {
        repeatAction = action
    }

    fun onEnd(action: (Animator) -> Unit) {
        endAction = action
    }

    fun onCancel(action: (Animator) -> Unit) {
        cancelAction = cancelAction
    }

    fun onStart(action: (Animator) -> Unit) {
        startActon = action
    }
}

