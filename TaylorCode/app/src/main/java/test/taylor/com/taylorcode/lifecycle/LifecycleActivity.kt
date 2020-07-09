package test.taylor.com.taylorcode.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LifecycleActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val observer = TaylorLifeCycleObserver()
        lifecycle.addObserver(observer)
    }
}