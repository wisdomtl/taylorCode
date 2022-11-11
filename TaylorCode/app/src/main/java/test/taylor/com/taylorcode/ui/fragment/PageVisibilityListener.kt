package test.taylor.com.taylorcode.ui.fragment

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import test.taylor.com.taylorcode.kotlin.extension.onVisibilityChange

/**
 * A listener which will be invoked when [Fragment]/[Activity] 's visibility has changed through [onPageVisibilityChange]/[onActivityVisibilityChange]
 */
class PageVisibilityListener : Application.ActivityLifecycleCallbacks {
    var onPageVisibilityChange: ((page: Any, isVisible: Boolean) -> Unit)? = null

    private val fragmentLifecycleCallbacks by lazy(LazyThreadSafetyMode.NONE) {
        object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
                if (f is ExposureParam) {
                    v.onVisibilityChange { view, isVisible ->
                        onPageVisibilityChange?.invoke(f, isVisible)
                    }
                }
            }
        }
    }

    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        (activity as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)
    }

    override fun onActivityDestroyed(activity: Activity) {
        (activity as? FragmentActivity)?.supportFragmentManager?.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks)
    }

    override fun onActivityStarted(p0: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
        if (activity is ExposureParam) onPageVisibilityChange?.invoke(activity, true)
    }

    override fun onActivityPaused(activity: Activity) {
        if (activity is ExposureParam) onPageVisibilityChange?.invoke(activity, false)
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

}