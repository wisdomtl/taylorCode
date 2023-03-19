package test.taylor.com.taylorcode.kotlin.select

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            TextView {
                layout_id = "tv_d1"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                text = "deferred1 complete"
                center_horizontal = true
                center_vertical = true
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
                top_toBottomOf = "tv_d1"
                center_horizontal = true
                onClick = {
                    deferred2.complete(true)
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
    }
}