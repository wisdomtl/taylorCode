package test.taylor.com.taylorcode.kotlin

import android.app.Activity
import android.os.Bundle
import android.util.Log

class KotlinExample : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val students = listOf(
                Student("taylor", 33, isMale = false, courses = listOf(Course("physics", 50), Course("chemistry", 78))),
                Student("milo", 20, isMale = false, courses = listOf(Course("computer", 50, true))),
                Student("lili", 40, isMale = true, courses = listOf(Course("chemistry", 78), Course("science", 50))),
                Student("meto", 10, isMale = false, courses = listOf(Course("mathematics", 48), Course("computer", 50, true)))
        )
        Log.d("ttaylor", students.find { it.age > 30 }.toString())

        //kotlin collection case1:filter and modify the first element in list
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
    }
}