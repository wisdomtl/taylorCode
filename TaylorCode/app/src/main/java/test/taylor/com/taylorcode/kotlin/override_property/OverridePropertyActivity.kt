package test.taylor.com.taylorcode.kotlin.override_property

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.my_activity.*
import test.taylor.com.taylorcode.R

class OverridePropertyActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(MyViewModel::class.java) }
    private var myAdapter: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity)
        registerObserver()
        initView()
        initData()
    }

    private fun registerObserver() {
        viewModel.colorLiveData.observe(this@OverridePropertyActivity, Observer {
            myAdapter?.notifyDataSetChanged()
        })

        viewModel.beanLiveData.observe(this@OverridePropertyActivity, Observer {
            myAdapter = object : MyAdapter(it) {
                override val color: ColorBean?
                    get() = viewModel.colorLiveData.value
            }
            myRv.adapter = myAdapter
        })
    }

    private fun initData() {
        viewModel.fetchBean()
        viewModel.fetchColor()
    }

    private fun initView() {
        myRv.layoutManager = LinearLayoutManager(this)
    }
}