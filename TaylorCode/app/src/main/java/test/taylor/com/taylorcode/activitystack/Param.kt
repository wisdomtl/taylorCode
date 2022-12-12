package test.taylor.com.taylorcode.activitystack

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import test.taylor.com.taylorcode.ui.line_feed_layout.orZero
import test.taylor.com.taylorcode.util.print

/**
 * A Parameter could be implemented by [Activity]/[Fragment]
 * An [Activity]/[Fragment] implemented [Param] means that it claims I have a parameter in the form of Map<String,Any>,
 * the follow-up Activities could acquire it by [getParam].
 * This is a substitution for passing parameter through [Intent]
 */
interface Param {
    val paramMap: Map<String, Any>
}

/**
 * Get the [Param] implemented by the previous [Activity]/[Fragment] by [key].
 * If there are more than one Activity to be acquired parameter, make sure their keys are different.
 * Throw [IllegalArgumentException] if there is no such value of [key] to warn a maybe missing value passing.
 * It will crash if the acquired value is not the same type as [T] to warn a wrong cast.
 */
fun <T> Activity.getParam(key: String): T {
    val iterator = PageStack.stack.let { it.listIterator(it.size) }
    var value: T? = null
    while (iterator.hasPrevious()) {
        val activity = iterator.previous()
        //1. find value in activity
        val v = (activity as? Param)?.paramMap?.getOrDefault(key, null)
        if (v != null) {
            if (value == null) {
                value = v as? T
            } else {
                throw IllegalArgumentException("duplicated key=${key} in previous ${activity.javaClass.simpleName}")
            }
        }
        // if failed in activity then try it's fragments
        val paramFragments = PageStack.fragments[activity]?.filter { (it as? Param)?.paramMap?.getOrDefault(key, null) != null }
        if (paramFragments?.size.orZero > 1) throw IllegalArgumentException("duplicated key=${key} in previous fragments=${paramFragments?.print { it.javaClass.simpleName }}")
        else if (paramFragments?.size.orZero == 1) {
            if (value == null) {
                value = (paramFragments?.first() as? Param)?.paramMap?.get(key) as? T
            } else {
                throw IllegalArgumentException("duplicated key=${key} in previous ${paramFragments?.first()?.javaClass?.simpleName}")
            }
        }
    }
    return value ?: throw IllegalArgumentException("missing Parameter for key=$key the previous Activity/Fragment")
}