package test.taylor.com.taylorcode.kotlin.collection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

class KotlinCollectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fold()
    }

    fun fold() {
        val list = listOf(1, 2, 3)
        val sum = list.fold(0) { acc, i -> acc + i }
        Log.v("ttaylor","tag=fold, KotlinCollectionActivity.fold()  sum=${sum}")
        val multiple = list.fold(1) {acc, i -> acc*i }
        Log.v("ttaylor","tag=fold, KotlinCollectionActivity.fold()  multiple=${multiple}")
    }
}