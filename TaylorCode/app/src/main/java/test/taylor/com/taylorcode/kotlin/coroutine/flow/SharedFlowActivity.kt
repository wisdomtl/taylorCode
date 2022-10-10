package test.taylor.com.taylorcode.kotlin.coroutine.flow

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*

class SharedFlowActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this)[SharedFlowViewModel::class.java] }

    private lateinit var tv: TextView

    private val intents by lazy {
        merge(
            tv.clickFlow { listOf(Intent.ShowDialog) }.onEach { delay(3000) },
        )
    }

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            tv = TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                textColor = "#000000"
                text = "emit value by delay"
                gravity = gravity_center
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        intents
            .onEach(viewModel::send)
            .launchIn(lifecycleScope)

        /**
         * case: SharedFlow will drop the value when there is no subscribers
         */
        viewModel.viewState
            .collectIn(this) { Log.v("ttaylor[SharedFlow test]", "toast show =${(it as? ViewState.ShowDialog)?.v}") }
    }
}

sealed class Intent {
    object ShowDialog : Intent()
}

sealed class ViewState {
    class ShowDialog(val v: Int) : ViewState()
}