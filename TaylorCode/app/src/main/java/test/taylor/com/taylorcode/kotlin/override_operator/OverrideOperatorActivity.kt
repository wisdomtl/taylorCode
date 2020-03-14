package test.taylor.com.taylorcode.kotlin.override_operator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class OverrideOperatorActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var point = Point(1,0)
        Log.v("ttaylor","tag=, OverrideOperatorActivity.onCreate()  point.x="+point[0])
    }
}