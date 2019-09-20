package test.taylor.com.taylorcode.ui.custom_view.tag_view

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.TypedValue
import kotlinx.android.synthetic.main.tag_textview_activity.*
import test.taylor.com.taylorcode.R

class TagTextViewActivity:AppCompatActivity() {
    private val pingfang by lazy {
        Typeface.createFromAsset(assets, "pingfang.ttf")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tag_textview_activity)


        ttv_1.tagText = "123"
        ttv_1.tagTextSize = dip(8F)
        ttv_1.tagTextColor = Color.YELLOW
        ttv_1.tagTextTypeFace = pingfang
//        ttv_1.tagTextPaddingStart = dip(3f)
//        ttv_1.tagTextPaddingEnd = dip(3f)
//        ttv_1.tagTextPaddingTop = dip(1f)
//        ttv_1.tagTextPaddingBottom = dip(1f)

        btnLarger.setOnClickListener {
            ttv_1.setTextSize(dip(35f))
        }

        btnSmaller.setOnClickListener {
            ttv_1.setTextSize(dip(10f))
        }
    }
}


/**
 * Get [DisplayMetrics] from [Resources]
 */
val Context.displayMetrics: DisplayMetrics get() = resources.displayMetrics

/**
 * Calculate dimensions in pixel of dip
 */
fun Context.dip(value: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, displayMetrics)