package test.taylor.com.taylorcode.concurrent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ConcurrentHashMap

class ConcurrentActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * case: ConcurrentHashMap ConcurrentModificationException(if use hashmap,it will happen)
         */
        val map = ConcurrentHashMap<Int,String>().apply {
            put(1,"dsf")
            put(2,"derr")
            put(3,"iouio")
            put(4,"cvcccccx")
            put(5,"iuyphpiodfa")
            put(6,"33")
            put(7,"44")
            put(8,"55")
            put(9,"66")
            put(10,"77")
            put(11,"88")
            put(12,"99")

        }
        Thread{
            map.remove(5)
        }.start()

        Thread{
            repeat(100){
                map.forEach{entry->

                    Log.v("ttaylor","tag=map, ConcurrentActivity.onCreate()  key=${entry.key}, value=${entry.value}")
                }
            }

        }.start()

        Thread{
            map.remove(12)
        }.start()

        Thread{
            repeat(10){
                map[7]
            }
        }.start()

        Thread{
            map.remove(7)
        }.start()
    }
}