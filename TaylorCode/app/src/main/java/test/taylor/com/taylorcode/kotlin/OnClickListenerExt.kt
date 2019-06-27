package test.taylor.com.taylorcode.kotlin

import android.util.Log
import android.view.View


class OnClickListenerBuilder {
    var onClick: ((View) -> Unit)? = null

    fun onClick(action: (View) -> Unit) {
        onClick = action
    }
}

fun View.setOnDataClickListener(action: OnClickListenerBuilder.() -> Unit) {
    setOnClickListener(
            OnClickListenerBuilder().apply(action).let { builder ->
                View.OnClickListener { view ->
                    Log.v("ttaylor", "tag=onClick, .getDataPointClickListener()  view{$view} is clicked")
                    builder.onClick?.invoke(view)
                }
            }
    )
}