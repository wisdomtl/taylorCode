package test.taylor.com.taylorcode.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.constraint_layout_activity.*
import test.taylor.com.taylorcode.R
import java.util.*

class KotlinActivity : AppCompatActivity() {
    val list1 = listOf<String>("abd", "add", "fff")
    val list2 = listOf<String>("abd", "add", "fff")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constraint_layout_activity)
        btn3.setOnClickListener { Toast.makeText(this, "onclick for kotlin", Toast.LENGTH_LONG).show() }

        listEquals()
        split()
        Log.v("ttaylor", "tag=sss, KotlinActivity.onCreate() isInToday=${isInToday(1567057536000)} [savedInstanceState]")
        val interface1 = object : Iinterface {
            override fun dod() {

            }
        }
        val interface2 = object : Iinterface {
            override fun dod() {
            }
        }
        val map = mutableMapOf(
            "1" to mutableListOf(interface1, interface2)
        )

        map.forEach { entry ->
            val iterator = entry.value.iterator()
            while (iterator.hasNext()) {
                val ite = iterator.next()
                if (ite == interface1) {
                    iterator.remove()
                }
            }
        }

        val str = "kfsklfjdklj$"
        str.substring(0, str.length - 1)
        Log.v("ttaylor", "tag=string, KotlinActivity.onCreate() str=${str.substring(0, str.length - 1)} ")


        /**
         * sequence case: early exit, better performance
         */
        sequenceOf("a", "b", "c")
            .map {
                print(it)
                it.toUpperCase()
            }
            .any {
                print(it)
                it.startsWith("B")
            }

        /**
         * sequence case: always put data-reducing operations befor data-transforming operations
         */
        //poor performance
        sequenceOf("a", "b", "c", "d")
            .map {
                println("map: $it")
                it.toUpperCase()
            }
            .filter {
                println("filter: $it")
                it.startsWith("a", ignoreCase = true)
            }
            .forEach {
                println("forEach: $it")
            }

        //better performance
        sequenceOf("a", "b", "c", "d")
            .filter {
                println("filter: $it")
                it.startsWith("a", ignoreCase = true)
            }
            .map {
                println("map: $it")
                it.toUpperCase()
            }
            .forEach {
                println("forEach: $it")
            }

        /**
         * sequence case: flapMap
         */
        val result = sequenceOf(listOf(1, 2, 3), listOf(4, 5, 6))
            .flatMap {
                it.asSequence().filter { it % 2 == 1 }
            }
            .toList()

        print(result)   // [1, 3, 5]

        /**
         * sequence case: withIndex()
         */
        val result2 = sequenceOf("a", "b", "c", "d")
            .withIndex()
            .filter { it.index % 2 == 0 }
            .map { it.value }
            .toList()

        print(result2)   // [a, c]

        /**
         * sequence case: joinToString()
         */
        data class Person(var name:String,var age:Int)
        val persons = listOf(
            Person("Peter", 16),
            Person("Anna", 28),
            Person("Anna", 23),
            Person("Sonya", 39)
        )
        val result3 = persons
            .asSequence()
            .map { it.name }
            .distinct()
            .joinToString();

        print(result3)   // "Peter, Anna, Sonya"
    }

    private fun split() {
        val str = "asb;ddd;"
        Log.v("ttaylor", "tag=split, KotlinActivity.split()  split=${str.split(";")}")
    }

    private fun listEquals() {
        Log.v("ttaylor", "tag=equals, KotlinActivity.listEquals() isEquals=${list1 == list2}")
    }

    fun isInToday(timestamp: Long): Boolean {

        val beginningOfToday = Calendar.getInstance().let {
            it.set(Calendar.HOUR_OF_DAY, 0)
            it.set(Calendar.MINUTE, 0)
            it.set(Calendar.SECOND, 0)
            it.set(Calendar.MILLISECOND, 0)
            it.timeInMillis
        }

        val endingOfToday = Calendar.getInstance().let {
            it.set(Calendar.HOUR_OF_DAY, 23)
            it.set(Calendar.MINUTE, 59)
            it.set(Calendar.SECOND, 59)
            it.set(Calendar.MILLISECOND, 999)
            it.timeInMillis
        }

        return timestamp in beginningOfToday..endingOfToday

    }

    interface Iinterface {
        fun dod()
    }
}
