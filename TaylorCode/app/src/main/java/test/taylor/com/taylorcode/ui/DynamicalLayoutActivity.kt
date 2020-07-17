package test.taylor.com.taylorcode.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.*

class DynamicalLayoutActivity : AppCompatActivity() {

    private val games = listOf(
        Game("region", listOf("wechat", "qq", "msn")),
        Game("mode", listOf("qualifier", "normal", "entertainment")),
        Game("level", listOf("gold", "silver", "diamond", "star", "master", "super star"))
    )

    private val rootView by lazy {
        LinearLayout {
            layout_width = match_parent
            layout_height = match_parent
            orientation = vertical
        }
    }

    private val titleView: TextView
        get() = TextView {
            layout_width = wrap_content
            layout_height = wrap_content
            textSize = 20f
            textColor = "#00ff00"
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)

        games.forEach { game ->
            rootView.apply {
                addView(titleView.apply {
                    text = game.title
                    margin_bottom = 20
                })
                addView(LineFeedLayout {

                })
            }
        }
    }


}

data class Game(var title: String, var subTitles: List<String>)

