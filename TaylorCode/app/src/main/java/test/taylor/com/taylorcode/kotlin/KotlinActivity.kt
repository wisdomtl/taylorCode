package test.taylor.com.taylorcode.kotlin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.constraint_layout_activity.*
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.util.ofMap
import test.taylor.com.taylorcode.util.print
import test.taylor.com.taylorcode.util.splitByDigit
import test.taylor.com.taylorcode.util.subDigit
import java.util.*
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class KotlinActivity : AppCompatActivity() {
    val list1 = listOf<String>("abd", "add", "fff")
    val list2 = listOf<String>("abd", "add", "fff")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constraint_layout_activity)


        val list1 = listOf(
            "11",
            "22",
            "33",
            "44"
        )

        val list1ReadOnly = Collections.unmodifiableList(list1)
        val newList1 = list1ReadOnly.toMutableList()
        newList1[1] = "111"
        list1ReadOnly.print { it.toString() }.let { Log.v("ttaylor"," read only list=$it") }
        newList1.print { it.toString() }.let { Log.v("ttaylor","new list = ${it}  ") }


        val readOnlyList1 = list1.toList()
        val mutableList1 = readOnlyList1.toMutableList()
        mutableList1[1] = "aaaa"
        readOnlyList1.print { it.toString() }.let { Log.v("ttaylor"," readOnlyList1=$it") }
        mutableList1.print { it.toString() }.let { Log.v("ttaylor","mutableList1= ${it}  ") }




        btn3.setOnClickListener { Toast.makeText(this, "onclick for kotlin", Toast.LENGTH_LONG).show() }

        val oldList = listOf("1", "2", "3")
        val newList =  oldList.toMutableList().apply {
            clear()
            add("11")
            add("22")
        }
        oldList.print { it.toString() }.let { Log.v("ttaylor", "old list=${it}  ") }
        newList.print { it.toString() }.let { Log.v("ttaylor", "new list=${it}  ") }

        /**
         * case: copy
         */
        data class ChinaPerson(var name: String, var age: Int)

        val cp1 = ChinaPerson("sb", 10)
        val cp2 = cp1.copy()
        cp1.age = 20
        Log.v("ttaylor", "tag=copy , KotlinActivity.onCreate()  cp2.age=${cp2.age}")


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
        data class Coordinate(var x: Int, var y: Int)
        data class Location(var country: String, var city: String, var coordinate: Coordinate)
        data class Person(var name: String, var age: Int, var locaton: Location? = null)


        Person("Peter", 16, Location("china", "shanghai", Coordinate(10, 20))).ofMap()?.print()
            .let { Log.v("ttaylor", "tag=343434, KotlinActivity.onCreate()  $it") }

        val persons = listOf(
            Person("Peter", 16),
            Person("Anna", 28),
            Person("Anna", 23),
            Person("Sonya", 39)
        )
        persons.print { it.ofMap()?.print() ?: "" }.let { Log.v("ttaylor", "tag=kfdksfjdlk, KotlinActivity.onCreate()  $it") }
        val result3 = persons
            .asSequence()
            .map { it.name }
            .distinct()
            .joinToString();

        print(result3)   // "Peter, Anna, Sonya"

        /**
         * lazy case:
         */
        val lazyValue: String by lazy {
            //this block will be invoked only once
            println("computed!")
            "Hello"
        }

        fun main() {
            println(lazyValue)
            println(lazyValue)
        }

        /**
         * Delegates.observable() case
         */
        var name: String by Delegates.observable("init") { property, old, new ->
            Log.v("ttaylor", "tag=observable, KotlinActivity.onCreate()  ${property.name} has been changed from $old to $new")
        }
        name = "taylor"

        /**
         * Delegates.vetoable() case
         */
        var time: String by Delegates.vetoable("13:34") { property, old, new ->
            !new.startsWith("2")
        }

        time = "11:22"
        Log.v("ttaylor", "tag=vetoable, KotlinActivity.onCreate()  time=$time")
        time = "22:22"
        Log.v("ttaylor", "tag=vetoable, KotlinActivity.onCreate()  time=$time")


        /**
         * create delegate as member function
         */
        class Delegate {
            operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
                return "$thisRef, thank you for delegating '${property.name}' to me!"
            }

            operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
                Log.v("ttaylor", "tag=, Delegate.setValue()  $value has been assigned to '${property.name}' in $thisRef.")
            }
        }

        class Example {
            var p: String by Delegate()
        }

        Example().p = "ddd"


        /**
         * create delegate as extension
         */
        class Delegate2 : ReadWriteProperty<Any?, String> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): String {
                return "aaa"
            }

            override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
                Log.v("ttaylor", "tag=, Delegate2.setValue() $value has been assigned to ${property.name} ")
            }
        }

        var p: String by Delegate2()

        p = "bbb"
        Log.v("ttaylor", "tag=, KotlinActivity.onCreate()  ${p}")


        /**
         * create delegate as extension function
         */
        class Delegate3 {}

        operator fun Delegate3.getValue(thisRef: Any?, property: KProperty<*>): String {
            return "eee"
        }

        operator fun Delegate3.setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            Log.v("ttaylor", "tag=, KotlinActivity.setValue()  ${value} has assigned to ${property.name}")
        }

        var p2: String by Delegate3()

        p2 = "qqq"
        Log.v("ttaylor", "tag=, KotlinActivity.onCreate()  p2=${p2}")


        val objects = listOf("11", 1, "22")
        objects.filterIsInstance<String>().print { it }.also { Log.v("ttaylor", "tag=filterIsInstance, instance=${it} ") }


        /**
         * print map
         */
        val map2 = mutableMapOf(
            "12" to "dafdf",
            "23" to "eeee"
        )

        map2.print().let { Log.v("ttaylor", "tag=print map, KotlinActivity.onCreate()  ${it}") }


        /**
         * turn data class into map
         */
        val course = Course("computer", 50, true)
        course.ofMap()?.print().let { Log.v("ttaylor", "tag=data map, KotlinActivity.onCreate()  ${it}") }

        /**
         * invoke 约定
         */
        Invoke()
        Invoke2()

        /**
         * first day of week
         */
        Log.v("ttaylor", "tag=date, KotlinActivity.onCreate()  first day of week=${thisMondayInMillis()}, end day of week=${thisSundayInMillis()}")

        val charSequence = "零一二三四五六30002二三"
        Log.v("ttaylor", "tag=subdigit, KotlinActivity.onCreate()  digit=${charSequence.subDigit}")

        charSequence.splitByDigit().forEach {
            Log.v("ttaylor", "tag=split by digit, KotlinActivity.onCreate()  c=${it}")
        }
        textCompanionObjec.sec
        textCompanionObjec.sec2
        textCompanionObjec.sec3
        textCompanionObjec.CC.doB()
        textCompanionObjec.doA()


        /**
         * spread array to listOf()
         */
        val otherList = mutableListOf<String>().apply {
            add("bbbb")
            add("ccc")
        }
        val list = listOf<Any>(
            "aaa",
            *otherList.toTypedArray()
        )
        val list2 = listOf<Any>(
            "aaa",
            otherList
        )
        list.print { it.toString() }?.let { Log.v("ttaylor", "tag=spread array, list=$it") }
        list2.print { it.toString() }?.let { Log.v("ttaylor", "tag=spread array failed, list=$it") }
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

    fun thisMondayInMillis() = Calendar.getInstance().let { c ->
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) c.add(Calendar.DATE, -1)
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        c.timeInMillis
    }

    fun thisSundayInMillis() = Calendar.getInstance().let { c ->
        if (c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
            c.add(Calendar.DATE, 1)
        }
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        c.timeInMillis
    }

    interface Iinterface {
        fun dod()
    }
}

class textCompanionObjec {
    companion object {
        val sec = 1

        @JvmStatic
        val sec2 = 2

        @JvmField
        val sec3 = 3
        fun doA() {}
    }

    object CC {
        fun doB() {}
    }
}
