package test.taylor.com.taylorcode.activitystack

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import test.taylor.com.taylorcode.activitystack.ActivityStack.fragmentLifecycleCallbacks
import test.taylor.com.taylorcode.activitystack.ActivityStack.fragments
import test.taylor.com.taylorcode.activitystack.ActivityStack.stack
import test.taylor.com.taylorcode.kotlin.extension.onVisibilityChange
import test.taylor.com.taylorcode.ui.custom_view.bullet_screen.LiveComment.activity
import java.util.LinkedList

object ActivityStack : Application.ActivityLifecycleCallbacks {
    val stack = LinkedList<Activity>()
    val fragments = hashMapOf<Activity, MutableList<Fragment>>()

    private val fragmentLifecycleCallbacks by lazy(LazyThreadSafetyMode.NONE) {
        object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
                f.activity?.also { activity ->
                    fragments[activity]?.also { it.add(f) } ?: run { fragments[activity] = mutableListOf(f) }
                }
            }
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        stack.add(activity)
        (activity as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)
    }

    override fun onActivityDestroyed(activity: Activity) {
        stack.remove(activity)
        (activity as? FragmentActivity)?.supportFragmentManager?.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks)
        fragments[activity]?.clear()
        fragments.remove(activity)
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }
}