package test.taylor.com.taylorcode.kotlin.coroutine.flow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlowActivity5 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            Log.d("ttaylor34434", "launch thread=${Thread.currentThread().id}")
            (1..100).asFlow()
                /**
                 * flatMapMerge will do multi-thread operation for the downstream operator
                 */
                .flatMapMerge {
                    flow {
                        Log.d("ttaylor34434", "flatMapMerge number=$it thread=${Thread.currentThread().id}")
                        // case: use withContext and emit inside will crash in runtime, flowOn is recommended
//                        withContext(Dispatchers.IO){
                            emit(it)
//                        }
                    }
                }
                .onEach {
                    withContext(Dispatchers.Main) {Log.d("ttaylor34434", "onEach number=$it thread=${Thread.currentThread().id}")}
                }
                .collect {
                    Log.d("ttaylor34434", "collect number=$it thread=${Thread.currentThread().id}")
                }

        }
    }
}