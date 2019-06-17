package test.taylor.com.taylorcode.kotlin

import com.google.gson.annotations.SerializedName

data class Student(var name: String, var age: Int, var isMale: Boolean, var courses: List<Course> = listOf(),@SerializedName("sex") var sex: Int = 0) : Comparable<Student> {
    override  fun compareTo(other: Student): Int {
        if (this.sex == 2) {
            return 1
        }
        if (KotlinExample.SEX == 1) {
            return other.sex - this.sex
        } else {
            return this.sex - other.sex
        }
    }

}
