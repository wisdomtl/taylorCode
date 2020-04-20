package test.taylor.com.taylorcode.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintProperties
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.constraintlayou_activity2.*
import test.taylor.com.taylorcode.R

class ConstraintLayoutActivity3 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constraintlayou_activity2)
        build()
    }

    private fun build() {
        val tv1 = TextView(this).apply {
            text = "dkfsdlkfjksd"
            textSize = 20f
            setBackgroundColor(Color.parseColor("#00ff00"))

            id = ViewCompat.generateViewId()
        }.also { croot2.addView(it) }

        val tv2 = TextView(this).apply {
            text = "eieieieeiei"
            textSize = 20f
            setBackgroundColor(Color.parseColor("#00ff00"))
            id = ViewCompat.generateViewId()
        }.also { croot2.addView(it) }

        ConstraintProperties(tv1)
            .addToHorizontalChain(ConstraintProperties.PARENT_ID, tv2.id)
            .apply()

        ConstraintProperties(tv2)
            .addToHorizontalChain(tv1.id, ConstraintProperties.PARENT_ID)
            .apply()
    }
}