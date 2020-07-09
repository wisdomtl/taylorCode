package test.taylor.com.taylorcode.lifecycle

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class TaylorLifeCycleObserver: LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(){
        Log.v("ttaylor","tag=, TaylorLifeCycleObserver.onStart()  ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(){
        Log.v("ttaylor","tag=, TaylorLifeCycleObserver.onStop()  ")
    }
}