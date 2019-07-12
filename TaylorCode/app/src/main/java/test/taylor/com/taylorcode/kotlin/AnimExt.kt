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
    private val animators by lazy { mutableListOf<Anim>() }
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

    /**
     * build an Animator with a much shorter code by DSL
     */
    fun animObject(action: ObjectAnim.() -> Unit) {
        ObjectAnim().apply(action).also { it.setPropertyValueHolder() }.let { animators.add(it) }
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

abstract class Anim {
    abstract var animation: ValueAnimator
    var duration
        get() = 300L
        set(value) {
            animation.duration = value
        }
    var interpolator
        get() = LinearInterpolator() as Interpolator
        set(value) {
            animation.interpolator = value
        }

    abstract fun reverseValues()
    fun getAnim() = animation
}

class ObjectAnim : Anim() {
    override var animation: ValueAnimator = ObjectAnimator()

    var translationX: FloatArray? = null
    var translationY: FloatArray? = null
    var scaleX: FloatArray? = null
    var scaleY: FloatArray? = null
    var alpha: FloatArray? = null
    var target: Any? = null
        set(value) {
            field = value
            (animation as ObjectAnimator).target = value
        }
    private val valuesHolder = mutableListOf<PropertyValuesHolder>()

    override fun reverseValues() {
        valuesHolder.forEach { valuesHolder ->
            when (valuesHolder.propertyName) {
                "translationX" -> translationX?.let {
                    it.reverse()
                    valuesHolder.setFloatValues(*it)
                }
                "translationY" -> translationY?.let {
                    it.reverse()
                    valuesHolder.setFloatValues(*it)
                }
                "scaleX" -> scaleX?.let {
                    it.reverse()
                    valuesHolder.setFloatValues(*it)
                }
                "scaleY" -> scaleY?.let {
                    it.reverse()
                    valuesHolder.setFloatValues(*it)
                }
                "alpha" -> alpha?.let {
                    it.reverse()
                    valuesHolder.setFloatValues(*it)
                }
            }
        }
    }

    fun setPropertyValueHolder() {
        translationX?.let { PropertyValuesHolder.ofFloat("translationX", *it) }?.let { valuesHolder.add(it) }
        translationY?.let { PropertyValuesHolder.ofFloat("translationY", *it) }?.let { valuesHolder.add(it) }
        scaleX?.let { PropertyValuesHolder.ofFloat("scaleX", *it) }?.let { valuesHolder.add(it) }
        scaleY?.let { PropertyValuesHolder.ofFloat("scaleY", *it) }?.let { valuesHolder.add(it) }
        alpha?.let { PropertyValuesHolder.ofFloat("alpha", *it) }?.let { valuesHolder.add(it) }
        animation.setValues(*valuesHolder.toTypedArray())
    }

}

/**
 * a container for a single Animator
 */
class ValueAnim : Anim() {
    override var animation: ValueAnimator = ValueAnimator()

    var values: Any? = null
        set(value) {
            field = value
            value?.let {
                when (it) {
                    is FloatArray -> animation.setFloatValues(*it)
                    is IntArray -> animation.setIntValues(*it)
                    else -> throw IllegalArgumentException("unsupported type of value")
                }
            }
        }

    var action: ((Any) -> Unit)? = null
        set(value) {
            field = value
            animation.addUpdateListener { valueAnimator ->
                valueAnimator.animatedValue.let { value?.invoke(it) }
            }
        }

    override fun reverseValues() {
        values?.let {
            when (it) {
                is FloatArray -> {
                    it.reverse()
                    animation.setFloatValues(*it)
                }
                is IntArray -> {
                    it.reverse()
                    animation.setIntValues(*it)
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

