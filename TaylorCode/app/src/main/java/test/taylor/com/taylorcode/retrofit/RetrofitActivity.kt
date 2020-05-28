package test.taylor.com.taylorcode.retrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import test.taylor.com.taylorcode.kotlin.*

class RetrofitActivity : AppCompatActivity() {


    private val rootView by lazy {
        ConstraintLayout {
            TextView {
                layout_id = "tvCoin"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                center_horizontal = true
                top_toTopOf = parent_id
            }

            TextView {
                layout_id = "tvDiamond"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                top_toBottomOf = "tvCoin"
                center_horizontal = true
            }
        }
    }

    private val jokeViewModel by lazy { ViewModelProviders.of(this).get(NewsViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)
        jokeViewModel.newsLiveData.observe(this, Observer {
            Log.v("ttaylor","tag=, RetrofitActivity.onCreate()  ")
        })
        jokeViewModel.fetchJoke()


    }
}