package test.taylor.com.taylorcode.rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SingleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Observable.fromIterable(listOf(1, 2, 3, 4, 5, 6))
            .flatMap { value ->
                Observable.create<String> {
                    Thread.sleep(1000)
                    Log.v("ttaylor","emitter thread id =${Thread.currentThread().id}")
                    it.onNext("${value}xx")
                }
                    .subscribeOn(Schedulers.io())// when subsribeOn is here ,parallelism is happening
            }
            .subscribeOn(Schedulers.io())// when subsribeOn is here, sequence is happening
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("ttaylor", "success ret=${it} thread id=${Thread.currentThread().id}")
            }, {
                Log.v("ttaylor", "failed")
            })
    }
}