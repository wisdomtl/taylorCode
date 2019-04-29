package test.taylor.com.taylorcode.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.constraint_layout_activity.*
import test.taylor.com.taylorcode.R

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constraint_layout_activity)
        btn3.setOnClickListener {Toast.makeText(this,"onclick for kotlin",Toast.LENGTH_LONG).show()}

    }
}