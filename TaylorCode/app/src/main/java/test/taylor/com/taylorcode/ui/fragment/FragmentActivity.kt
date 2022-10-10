package test.taylor.com.taylorcode.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentResultListener
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*

class FragmentActivity : AppCompatActivity() {


    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                textColor = "#ffffff"
                text = "Activity"
                gravity = gravity_center
                top_toTopOf = parent_id
                center_horizontal = true
            }

            FrameLayout {
                layout_id = "container"
                layout_width = match_parent
                layout_height = 0
                top_toTopOf = "tvChange"
                bottom_toBottomOf = parent_id
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        //        supportFragmentManager.beginTransaction().add("container".toLayoutId(), Fragment1()).commit()
        DialogFragment1().show(supportFragmentManager, "ddd")
        supportFragmentManager.setFragmentResultListener("abc", this) { requestKey, result -> Log.v("ttaylor", "activity.onCreate() result=${result["111"]}") }
        supportFragmentManager.setFragmentResultListener("efg", this) { requestKey, result -> Log.v("ttaylor", "activity.onCreate() result=${result["112"]}") }

        val iterator = listOf("a", "b", "c", "d").iterator(4)
        while (iterator.hasNext()) {
            Log.v("ttaylor", "onCreate()[iterator] nextItem=${iterator.next()}")
        }
        listOf("1","2","3").forEach {
            if(it == "2") {
                return@forEach
            }
            Log.v("ttaylor","foreach = ${it}")
        }
    }
}

/**
 * case: custom iterator
 */
fun <T> List<T>.iterator(startIndex: Int): Iterator<IndexedValue<T>> = object : Iterator<IndexedValue<T>> {
    private var index: Int = startIndex
    private var bigger = true

    init {
        require(startIndex >= 0 && startIndex < this@iterator.size) { "Index out of bounds of List" }
    }

    override fun hasNext(): Boolean {
        return if (bigger) index >= startIndex else index < startIndex
    }

    override fun next(): IndexedValue<T> {
        val current = this@iterator[index]
        return IndexedValue(index, current).also {
            val nextIndex = index + 1
            index = nextIndex.mod(this@iterator.size)
            if (index == 0) bigger = false
        }
    }
}