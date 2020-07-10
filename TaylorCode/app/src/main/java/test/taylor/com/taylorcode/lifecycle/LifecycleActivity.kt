package test.taylor.com.taylorcode.lifecycle

import android.os.Bundle
import test.taylor.com.taylorcode.ui.night_mode.BaseActivity

class LifecycleActivity:BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val observer = TaylorLifeCycleObserver()
        lifecycle.addObserver(observer)
    }
}