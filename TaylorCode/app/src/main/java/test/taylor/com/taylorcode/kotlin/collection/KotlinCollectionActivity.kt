package test.taylor.com.taylorcode.kotlin.collection

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.util.print
import test.taylor.com.taylorcode.util.printCallStack

class KotlinCollectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fold()

        listOf(1, 2, 3, 4, 5, 6).print("Array") { element -> element.toString() }

        mutableListOf<String>().print("Empty list") { element -> element }
    }

    fun fold() {
        val list = listOf(1, 2, 3)
        val sum = list.fold(0) { acc, i -> acc + i }
        Log.v("ttaylor", "tag=fold, KotlinCollectionActivity.fold()  sum=${sum}")
        val multiple = list.fold(1) { acc, i -> acc * i }
        Log.v("ttaylor", "tag=fold, KotlinCollectionActivity.fold()  multiple=${multiple}")

        printCallStack("fold call stack",5)
    }
}