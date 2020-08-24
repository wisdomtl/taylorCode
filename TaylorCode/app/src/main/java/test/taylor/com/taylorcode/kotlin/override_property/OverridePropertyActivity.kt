package test.taylor.com.taylorcode.kotlin.override_property

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.my_activity.*
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.setOnItemClickListener

class OverridePropertyActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(MyViewModel::class.java) }
    private var myAdapter: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity)
        registerObserver()
        initView()
        initData()
        if (!viewModel.showed){
            Log.v("ttaylor","tag=view model, OverridePropertyActivity.onCreate()  ")
            viewModel.showed = true
        }
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
        myRv.setOnItemClickListener { view, pos ->
            Log.v("ttaylor", "tag=onRecyclerView click, OverridePropertyActivity.initView()  pos=${pos}")
        }
    }
}

