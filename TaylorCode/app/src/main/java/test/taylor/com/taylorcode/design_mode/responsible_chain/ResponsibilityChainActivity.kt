package test.taylor.com.taylorcode.design_mode.responsible_chain

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*

class ResponsibilityChainActivity : AppCompatActivity() {

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            TextView {
                layout_id = "process"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 22f
                textColor = "#ffffff"
                text = "start process"
                gravity = gravity_center
                center_horizontal = true
                shape = shape {
                    corner_radius = 10
                    solid_color = "#8f8f8f"
                }
                onClick = {
                    val ret = dutyChain.execute()
                    Log.v("ttaylor","ret = $ret")
                }
            }
        }
    }

    private val dutyChain = DutyChain<String, String>("my name is taylor,i am an android developer.there is so many bugs waiting for me!").apply {
        addHandler(UppercaseHandler())
        addHandler(SpaceHandler())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }
}

class UppercaseHandler : Handler<String, String> {
    override fun handle(data: String, chain: Handler.Chain<String, String>): String {
        val uppercaseString = data.let {
            it.first().toUpperCase() + it.drop(1)
        }
        return chain.next(uppercaseString)
    }
}

class SpaceHandler : Handler<String, String> {
    override fun handle(data: String, chain: Handler.Chain<String, String>): String {
        val spaceString = data.replace(",", ", ").replace(".", ". ").replace("!", "! ")
        return chain.next(spaceString)
    }
}