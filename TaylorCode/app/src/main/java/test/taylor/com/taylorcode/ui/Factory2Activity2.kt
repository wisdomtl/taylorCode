package test.taylor.com.taylorcode.ui

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import test.taylor.com.taylorcode.R
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Factory2Activity2 : AppCompatActivity() {

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this@Factory2Activity2), object : LayoutInflater.Factory2 {
            override fun onCreateView(parent: View?, name: String?, context: Context?, attrs: AttributeSet?): View? {
                val (view, duration) = measureTimedValue { delegate.createView(parent, name, context!!, attrs!!) }
                Log.v("ttaylor","tag=inflate duration, Factory2Activity2.onCreateView()  duration=${duration}")
                return view
            }

            override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View? {
                return null
            }
        })
        super.onCreate(savedInstanceState)
    }
}