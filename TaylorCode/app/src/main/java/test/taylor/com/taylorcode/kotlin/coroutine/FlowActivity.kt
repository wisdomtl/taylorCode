package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

import test.taylor.com.taylorcode.kotlin.extension.contentView

class FlowActivity : AppCompatActivity() {
    var count = 0

    var flow: Flow<Int>? = null
    var job: Job? = null

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        countdown(200000, 100, contentView()) {
           Log.e("ttaylor","cout down by flow ")
        }
    }

    override fun onStop() {
        super.onStop()
    }
}
