package test.taylor.com.taylorcode.ui.night_mode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TestMaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TaylorDialogFragment.show(this)
    }
}