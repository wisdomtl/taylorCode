package test.taylor.com.taylorcode.ui.night_mode

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.delegate.Preference
import test.taylor.com.taylorcode.kotlin.extension.nightMode

open class BaseActivity : AppCompatActivity() {

    private val preference by lazy { Preference(getSharedPreferences("dark-mode", Context.MODE_PRIVATE)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nightMode(preference["dark-mode",false])
    }
}