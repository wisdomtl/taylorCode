package test.taylor.com.taylorcode.kotlin

import android.animation.*
import android.util.Log
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator


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

/**
 * a container for several Animator
 */
class AnimSet {
    private val animatorSet = AnimatorSet()
    private val animators by lazy { mutableListOf<ValueAnim>() }
    var interpolator
        get() = LinearInterpolator() as Interpolator
        set(value) {
            animatorSet.interpolator = value
        }
    var duration
        get() = 300L
        set(value) {
            animatorSet.duration = value
        }
    var onRepeat: ((Animator) -> Unit)? = null
    var onEnd: ((Animator) -> Unit)? = null
    var onCancel: ((Animator) -> Unit)? = null
    var onStart: ((Animator) -> Unit)? = null
    var isReverse: Boolean = false

    /**
     * build an Animator with a much shorter code by DSL
     */
    fun anim(action: ValueAnim.() -> Unit) {
        ValueAnim().apply(action).let { animators.add(it) }
    }

    fun reverse() {
        animators.takeIf { !isReverse }?.forEach { valueAnim -> valueAnim.reverseValues() }
        animatorSet.start()
        isReverse = true
    }

    fun init() {
        animatorSet.playTogether(animators.map { it.getAnim() })
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                animation?.let { onRepeat?.invoke(it) }
            }

            override fun onAnimationEnd(animation: Animator?) {
                animation?.let { onEnd?.invoke(it) }
            }

            override fun onAnimationCancel(animation: Animator?) {
                animation?.let { onCancel?.invoke(it) }
            }

            override fun onAnimationStart(animation: Animator?) {
                animation?.let { onStart?.invoke(it) }
            }
        })
    }

    fun start() {
        animators.takeIf { isReverse }?.forEach { valueAnim -> valueAnim.reverseValues() }
        animatorSet.start()
        isReverse = false
    }
}

abstract class Anim{
    abstract var anim:Animator
    abstract fun reverseValues()
}

//class ObjectAnim : ValueAnim() {
//    var anim: ValueAnimator = ObjectAnimator()
//    var animTarget: Any? = null
//        set(value) {
//            field = value
//            anim.target = value
//        }
//    var propertyValueHolder: Any? = null
//        set(value) {
//            field = value
//            anim.setValues(value as PropertyValuesHolder)
//        }
//}

/**
 * a container for a single Animator
 */
open class ValueAnim {
    private var anim: ValueAnimator = ValueAnimator()
    var duration
        get() = 300L
        set(value) {
            anim.duration = value
        }
    var interpolator
        get() = LinearInterpolator() as Interpolator
        set(value) {
            anim.interpolator = value
        }
    var values: Any? = null
        set(value) {
            field = value
            value?.let {
                when (it) {
                    is FloatArray -> anim.setFloatValues(*it)
                    is IntArray -> anim.setIntValues(*it)
                    else -> throw IllegalArgumentException("unsupported type of value")
                }
            }
        }
    var action: ((Any) -> Unit)? = null
        set(value) {
            field = value
            anim.addUpdateListener { valueAnimator ->
                valueAnimator.animatedValue.let { value?.invoke(it) }
            }
        }

    fun getAnim() = anim

    fun reverseValues() {
        values?.let {
            when (it) {
                is FloatArray -> {
                    it.reverse()
                    anim.setFloatValues(*it)
                }
                is IntArray -> {
                    it.reverse()
                    anim.setIntValues(*it)
                }
                else -> throw IllegalArgumentException("unsupported type of value")
            }
        }
    }
}

/**
 * build an AnimatorSet with a much shorter code by DSL
 */
fun animSet(action: AnimSet.() -> Unit) = AnimSet().apply { action() }.also { it.init() }

