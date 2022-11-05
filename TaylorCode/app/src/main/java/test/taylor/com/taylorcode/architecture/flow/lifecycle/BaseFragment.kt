package test.taylor.com.taylorcode.architecture.flow.lifecycle

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import test.taylor.com.taylorcode.kotlin.extension.onVisibilityChange

open class BaseFragment:Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.onVisibilityChange { view, isVisible ->
            Log.d("ttaylor", "BaseFragment.onViewCreated[Fragment(${this@BaseFragment.javaClass.simpleName}), isVisible($isVisible)]: ")
        }
    }
}