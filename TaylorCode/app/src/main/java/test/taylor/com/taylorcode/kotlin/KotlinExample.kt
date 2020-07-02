package test.taylor.com.taylorcode.kotlin

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.kotlin_activity.*
import test.taylor.com.taylorcode.R

class KotlinExample : Activity() {
    companion object {
        val SEX = 0
        @JvmStatic
        val SEX2 =1
    }

    private var trolley: MutableMap<Int, String> = mutableMapOf(Pair(1, "q"), Pair(2, "r"), Pair(3, "e"), Pair(4, "w"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_activity)
        /**
         * kotlin DSL case1:onclick listener wrapper
         */
        btnOnClick.setOnDataClickListener {
            onClick {
                Toast.makeText(this@KotlinExample, "btn is click", Toast.LENGTH_LONG).show()
            }
        }

        val students = listOf(
                Student("taylor", 33, isMale = false, courses = listOf(Course("physics", 50), Course("chemistry", 78)), sex = 1),
                Student("milo", 20, isMale = false, courses = listOf(Course("computer", 50, true)), sex = 2),
                Student("lili", 40, isMale = true, courses = listOf(Course("chemistry", 78), Course("science", 50)), sex = 1),
                Student("meto", 10, isMale = false, courses = listOf(Course("mathematics", 48), Course("computer", 50, true)), sex = 2),
                Student("ddd", 80, isMale = false, courses = listOf(Course("mathematics", 48)), sex = 0),
                Student("ddd", 70, isMale = false, courses = listOf(Course("mathematics", 48)), sex = 0)
        )
        Log.d("ttaylor", students.find { it.age > 30 }.toString())


        /**
         *kotlin collection case1:filter and modify the first element in list
         */
        val youngStudents = students.filter { !it.isMale && it.age < 30 }
        youngStudents.first().apply { this.name = this.name.toUpperCase() }
        Log.v("ttaylor", "filter.()$youngStudents")

        //kotlin collection case2:
        val friends = students
                .flatMap { it.courses }
                .toSet()
                .filter {
                    it.period < 70 && !it.isMust
                }
                .map {
                    it.apply {
                        name = name.replace(name.first(), name.first().toUpperCase())
                    }
                }
                .sortedWith(compareBy({ it.period }, { it.name }))

        Log.v("ttaylor", "flatMap.()$friends")

        /**
         *kotlin map case:remove key and value when iterate map
         */
        val iterator = trolley.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.key == 1) iterator.remove()
            Log.v("ttaylor", "tag=next, next=" + iterator.next())
        }
        Log.v("ttaylor", "tag=map, trolley=${trolley}")


        /**
         *kotlin sort case1:sorted()
         */
        students.sorted().forEach { Log.v("ttaylor", "tag=sorted, KotlinExample.onCreate()  student=${it.sex}") }
        students.forEach { Log.v("ttaylor", "tag=sorted, KotlinExample.onCreate()  student=${it.sex}") }

        /**
         * kotlin sort case2:List.sortedWith()
         */
        val goods = mutableListOf(GoodsDetail(sex = 1), GoodsDetail(sex = 2), GoodsDetail(sex = 1), GoodsDetail(sex = 2), GoodsDetail(sex = 1))
        val com = Comparator { o1: GoodsDetail, o2: GoodsDetail ->
            if (o1.sex == 2) {
                1
            }
            //male is in the front of female
            //sort wont work if a method is invoked
            if (KotlinExample.SEX == 1) {
                o2.sex - o1.sex
            }
            //female is in the front of male
            else {
                o1.sex - o2.sex
            }
        }
        goods.sortedWith(com).forEach { Log.w("ttaylor", "tag=sort, KotlinExample.onCreate()  sex=${it.sex}") }
        goods.forEach { Log.e("ttaylor", "tag=sort, KotlinExample.onCreate()  sex=${it.sex}") }

        /**
         * kotlin sort case3:MutableList.sortWith()
         */
        goods.sortWith(com)
        goods.forEach { Log.w("ttaylor", "tag=sortWith(), KotlinExample.onCreate()  sex=${it.sex}") }

        val longger = Course("a", 10) > Course("b", 9)
        Log.v("ttaylor", "tag=operator >, KotlinExample.onCreate()  longger=" + longger)

        /**
         * kotlin sort case4: sort with different condition
         */
        val com2 = Comparator { o1: Student, o2: Student ->
            //lambda is able to access class member
            Log.e("ttaylor", "tag=lambda, KotlinExample.onCreate()  trolley=${trolley}")
            if (o1.sex == o2.sex) {
                o2.age - o1.age
            } else if (o1.sex == KotlinExample.SEX) {
                -1
            } else if (o1.sex != 2) {
                1
            } else {
                if (o2.sex == KotlinExample.SEX) {
                    1
                } else {
                    -1
                }
            }
        }
        students.sortedWith(com2).forEach { Log.v("ttaylor", "tag=sotr multiple, KotlinExample.onCreate()  age=${it.age} ,sex=${it.sex}") }

        Log.v("ttaylor", "tag=buy, KotlinExample.onCreate()  num=${60000.formatNums()}")
        Log.v("ttaylor", "tag=div, KotlinExample.onCreate()  num=${1000.rem(1000)}")

    }

}

