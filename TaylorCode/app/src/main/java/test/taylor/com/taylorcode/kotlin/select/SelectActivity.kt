package test.taylor.com.taylorcode.kotlin.select

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.custom_view_activity.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import test.taylor.com.taylorcode.kotlin.*

/**
 * case: kotlin select
 */
class SelectActivity : AppCompatActivity() {
    private val contentView by lazy {
        LinearLayout {
            layout_width = match_parent
            layout_height = match_parent
            orientation = vertical

            TextView {
                layout_id = "tv_d1"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                text = "deferred1 complete"
                onClick = {
                    deferred1.complete(true)
                }
            }
            TextView {
                layout_id = "tv_d2"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                text = "deferred2 complete"
                onClick = {
                    deferred2.complete(true)
                }
            }

            TextView {
                layout_id = "tv_d2"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                text = "deferred2 complete"
                onClick = {
                    deferred2.completeExceptionally(java.lang.IllegalArgumentException("wrong arguement"))// the select below will crash app due to IllegalArgumentException
                }
            }
        }
    }

    private val deferred1 = CompletableDeferred<Boolean>()
    private val deferred2 = CompletableDeferred<Boolean>()

    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        scope.launch {
            /**
             * the first return deferred will continue the suspend function select
             */
            val value = select<Int> {
                deferred1.onAwait { 1 }
                deferred2.onAwait { 2 }
            }
            Log.i("ttaylor", "after select value=${value}")
        }

        scope.launch {
            select {
                deferred1.onAwait {1}
                // if  deferred1 没有值，则超时逻辑会被执行
                onTimeout(5000){
                    Log.e("ttaylor", "onTimeout");
                }
            }
        }
    }
}