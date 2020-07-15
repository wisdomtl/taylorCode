package test.taylor.com.taylorcode.kotlin.Channel

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.coroutine.autoDispose

class ChannelActivity : AppCompatActivity() {

    private lateinit var btn: Button

    private val rootView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent
            btn = Button {
                layout_id = "btnClick"
                layout_width = match_parent
                layout_height = wrap_content
                text = "click test"
                textAllCaps = false
                textSize = 20f
            }

            EditText et@{
                layout_width = match_parent
                layout_height = 50
                top_toBottomOf = "btnClick"
                textSize = 20f
                onTextChange = textWatcher {
                    val mainScope = MainScope()
                    val textActor = mainScope.actor<CharSequence?>(capacity = Channel.CONFLATED) {

                    }.autoDispose(this@et)
                    onTextChanged = { text: CharSequence?, start: Int, count: Int, after: Int ->
                        mainScope.launch {
                            textActor.send(text)
                        }.autoDispose(this@et)
                    }
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)
        btn.setShakelessClickListener(1000) {
            Log.v("ttaylor", "tag=asdfff, ChannelActivity.onCreate()  ")
        }
    }
}

/**
 * actor case: shakeless click listener
 */
fun View.setShakelessClickListener(threshold: Long, onClick: (View) -> Unit) {
    class Click(var view: View, var onClick: (View) -> Unit)

    val mainScope = MainScope()
    val clickActor = mainScope.actor<Click>(capacity = Channel.UNLIMITED) {
        var lastClickTime = System.currentTimeMillis()
        val isShake = { last: Long, now: Long -> now - last < threshold }
        for (click in channel) {
            if (!isShake(lastClickTime, System.currentTimeMillis())) {
                click.onClick(click.view)
            }
            lastClickTime = System.currentTimeMillis()
        }
    }.autoDispose(this)
    setOnClickListener { view ->
        mainScope.launch {
            clickActor.send(Click(view) {
                onClick(view)
            })
        }.autoDispose(this)
    }
}
