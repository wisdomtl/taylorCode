package test.taylor.com.taylorcode.kotlin

data class Course(var name: String, var period: Int, var isMust: Boolean = false) : Comparable<Course> {
    override fun compareTo(other: Course): Int {
        return this.period - other.period
    }

}