package test.taylor.com.taylorcode.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
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
