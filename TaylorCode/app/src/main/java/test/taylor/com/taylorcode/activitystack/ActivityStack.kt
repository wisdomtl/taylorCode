package test.taylor.com.taylorcode.activitystack

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.LinkedList

object ActivityStack : Application.ActivityLifecycleCallbacks {
    val stack = LinkedList<Param>()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is Param) stack.add(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (activity is Param) stack.remove(activity)
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