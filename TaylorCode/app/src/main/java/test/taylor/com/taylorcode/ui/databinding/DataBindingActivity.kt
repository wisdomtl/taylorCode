package test.taylor.com.taylorcode.ui.databinding

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import kotlinx.android.synthetic.main.data_binding_activity.*
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.databinding.DataBindingActivityBinding

class DataBindingActivity: AppCompatActivity(),OnTextViewClick {
    private  var user:User = User(ObservableField("taylor"), ObservableField(22))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * data binding case: init data binding
         */
        val binding = DataBindingUtil.setContentView<DataBindingActivityBinding>(this, R.layout.data_binding_activity);
        binding.user = user
        binding.users = listOf(User(ObservableField("cc"),ObservableField(22)),User(ObservableField("bb"),ObservableField(22)))
        binding.onClick = this


        /**
         * data binding case: change ObservableFiled by set, and bound TextView will be update
         */
        tvChange?.setOnClickListener {
            user.name.set("linda")
        }
    }

    /**
     * data binding case: click event bound to TextView
     */
    override fun onClick(v: View) {
        Toast.makeText(this,"on click",Toast.LENGTH_SHORT).show()
    }
}