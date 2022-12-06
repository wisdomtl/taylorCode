package test.taylor.com.taylorcode.ui.custom_view.overlap_anim

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class OverlapCarouselActivity2 : AppCompatActivity() {

    private val carouselAdapter by lazy {
        VarietyAdapter2().apply {
            addItemBuilder(CarouselProxy())
        }
    }

    private val mainScope = MainScope()

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            RecyclerView {
                layout_width = match_parent
                layout_height = match_parent
                layoutManager = LinearLayoutManager(context)
                adapter = carouselAdapter
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        mainScope.launch(Dispatchers.Default) {
            Log.v("ttaylor", "onCreate() thread id =${Thread.currentThread().id}")

        }
    }
}