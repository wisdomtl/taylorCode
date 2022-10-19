package test.taylor.com.taylorcode.ui.delegate_activity

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class DelegateActivity:AppCompatActivity() {

    override fun getDelegate(): AppCompatDelegate {
        return super.getDelegate()
    }
}