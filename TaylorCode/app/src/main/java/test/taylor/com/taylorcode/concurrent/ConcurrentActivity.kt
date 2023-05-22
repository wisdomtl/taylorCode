package test.taylor.com.taylorcode.concurrent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.util.print
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicInteger

class ConcurrentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * case: ConcurrentHashMap ConcurrentModificationException(if use hashmap,it will happen)
         */
        val map = ConcurrentHashMap<Int, String>().apply {
            put(1, "dsf")
            put(2, "derr")
            put(3, "iouio")
            put(4, "cvcccccx")
            put(5, "iuyphpiodfa")
            put(6, "33")
            put(7, "44")
            put(8, "55")
            put(9, "66")
            put(10, "77")
            put(11, "88")
            put(12, "99")

        }
        Thread {
            map.remove(5)
        }.start()

        Thread {
            repeat(100) {
                map.forEach { entry ->

                    Log.v("ttaylor", "tag=map, ConcurrentActivity.onCreate()  key=${entry.key}, value=${entry.value}")
                }
            }

        }.start()

        Thread {
            map.remove(12)
        }.start()

        Thread {
            repeat(10) {
                map[7]
            }
        }.start()

        Thread {
            map.remove(7)
        }.start()


//        val demo = ConcurrentLinkedQueueDemo()
//        demo.ddd()


        val step = 5
//        val queue = ConcurrentLinkedQueue<Int>()
//        repeat(100) {
//            Thread {
//                queue.add(it)
//                if (queue.size >= step) {
//                    lifecycleScope.launch(Dispatchers.IO) {
//                        print(queue, step)
//                    }
//                }
//            }.start()
//        }
//
//        lifecycleScope.launch {
//            delay(5000)
//
//            val list = queue.print { it.toString() }
//            Log.i("ttaylor", "[concurrent iterator] all=${list}");
//        }

        val flow = MutableSharedFlow<Int>(0, 20)// buffer capacity is necessary for buffering blocking log
        val queue2 = ConcurrentLinkedQueue<Int>()
        lifecycleScope.launch(Dispatchers.IO) {
//            while (true){
//                if(queue2.size >= step){
//                   print(queue2, step)
//                }
//            }
            flow.onEach { print(queue2, step) }.collect()
        }
        var count = AtomicInteger()
        repeat(100) {
            Thread {
                queue2.add(it)
                count.incrementAndGet()
                // this wont work due to multiple thread reading queue2.size, size is not right
//                if (queue2.size >= step) {
//                    lifecycleScope.launch(Dispatchers.IO) {
//                        Log.w("ttaylor", "${count.incrementAndGet()}[concurrent iterator] emit");
//                        flow.emit(1)
//                    }
//                }
            }.start()
        }

    }

    private var count1 = AtomicInteger()

    /**
     * case: iterator of ConcurrentLinkedQueue is snapshot,it wont work in multi-thread, the printed log is disordered
     */
    private fun getSnapShot(queue2: ConcurrentLinkedQueue<Int>): List<Int> {
        val iterator = queue2.iterator()
        val list = mutableListOf<Int>()
        while (iterator.hasNext()) {
            val value = iterator.next()
            list.add(value)
            iterator.remove()
        }
        return list
    }


    private fun print(queue2: ConcurrentLinkedQueue<Int>, step: Int) = try {

//        val iterator = queue2.iterator()
//        val list = mutableListOf<Int>()
//        var count = step
//        while (iterator.hasNext() && count > 0) {
//            val value = iterator.next()
//            list.add(value)
//            iterator.remove()
//            count--
//        }
        val list = mutableListOf<Int>()
        var count = step
        while (count > 0) {
            queue2.poll()?.let {
                list.add(it)
            }
            count--
        }
        list.print { it.toString() }.let { Log.i("ttaylor", "${count1.incrementAndGet()}[concurrent iterator]=${it}"); }
    } catch (e: Exception) {

    }
}