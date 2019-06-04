package test.taylor.com.taylorcode

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.main_activity.*

import test.taylor.com.taylorcode.aysnc.workmanager.WorkManagerActivity
import test.taylor.com.taylorcode.data_persistence.RoomActivity
import test.taylor.com.taylorcode.kotlin.KotlinExample
import test.taylor.com.taylorcode.rxjava.LoginActivity
import test.taylor.com.taylorcode.ui.ConstraintLayoutActivity
import test.taylor.com.taylorcode.ui.DialogActivity
import test.taylor.com.taylorcode.ui.DrawerLayoutActivity
import test.taylor.com.taylorcode.ui.KotlinActivity
import test.taylor.com.taylorcode.ui.TableLayoutActivity
import test.taylor.com.taylorcode.ui.anim.AnimActivity
import test.taylor.com.taylorcode.ui.custom_view.SelectorDemoActivity
import test.taylor.com.taylorcode.ui.material_design.CoordinateActivity
import test.taylor.com.taylorcode.ui.navigation.NavigationActivity
import test.taylor.com.taylorcode.ui.pagers.ViewPagerActivity
import test.taylor.com.taylorcode.ui.state_cross_activities.Activity1
import test.taylor.com.taylorcode.ui.surface_view.SurfaceViewActivity
import test.taylor.com.taylorcode.ui.touch_event.TouchEventActivity
import test.taylor.com.taylorcode.ui.viewstub.ViewStubActivity
import test.taylor.com.taylorcode.ui.window.WindowActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initView()
        Log.v("ttaylor", "MainActivity.onCreate()" + "  ")
    }

    override fun onStart() {
        super.onStart()
        Log.v("ttaylor", "MainActivity.onStart()" + "  ")
    }

    override fun onResume() {
        super.onResume()
        Log.v("ttaylor", "MainActivity.onResume()" + "  ")
    }

    override fun onPause() {
        super.onPause()
        Log.v("ttaylor", "MainActivity.onPause()" + "  ")
    }

    override fun onStop() {
        super.onStop()
        Log.v("ttaylor", "MainActivity.onStop()" + "  ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("ttaylor", "MainActivity.onDestroy()" + "  ")
    }

    private fun initView() {
        btn_room.setOnClickListener { startActivity(RoomActivity::class.java) }
        btn_livedata.setOnClickListener { startActivity(Activity1::class.java) }
        btn_window.setOnClickListener { startActivity(WindowActivity::class.java) }
        btn_touch_event.setOnClickListener { startActivity(TouchEventActivity::class.java) }
        btn_selector.setOnClickListener { startActivity(SelectorDemoActivity::class.java) }
        btn_workmanager.setOnClickListener { startActivity(WorkManagerActivity::class.java) }
        btn_anim.setOnClickListener { startActivity(AnimActivity::class.java) }
        btn_view_pager.setOnClickListener { startActivity(ViewPagerActivity::class.java) }
        btn_navigation.setOnClickListener { startActivity(NavigationActivity::class.java) }
        btn_drawer_layout.setOnClickListener { startActivity(DrawerLayoutActivity::class.java) }
        btn_constraint_layout.setOnClickListener { startActivity(ConstraintLayoutActivity::class.java) }
        btn_tab_layout.setOnClickListener { startActivity(TableLayoutActivity::class.java) }
        btn_dialog.setOnClickListener { startActivity(DialogActivity::class.java) }
        btn_coordinate_layout.setOnClickListener { startActivity(CoordinateActivity::class.java) }
        btn_rx_binding.setOnClickListener { startActivity(LoginActivity::class.java) }
        btn_surface_view.setOnClickListener { startActivity(SurfaceViewActivity::class.java) }
        btn_kotlin_activity.setOnClickListener { startActivity(KotlinActivity::class.java) }
        btn_kotlin_example.setOnClickListener { startActivity(KotlinExample::class.java) }
        btn_vs.setOnClickListener { startActivity(ViewStubActivity::class.java) }


        //SAM case:
        val onClickListener = View.OnClickListener { Log.v("ttaylor", "tag=SAM, view id=${it.id}") }

        btn_room.setOnClickListener(onClickListener)
        btn_livedata.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Log.v("ttaylor", "tag=SAM object, view id=${v?.id}")
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.v("ttaylor", "MainActivity.onSaveInstanceState()" + "  ")
    }

    /**
     * kotlin case1:anonymous inner class instance
     */
    object clickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    override fun onRestart() {
        super.onRestart()
        Log.v("ttaylor", "MainActivity.onRestart()" + "  ")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.v("ttaylor", "MainActivity.onRestoreInstanceState()" + "  ")
    }

    private fun startActivity(cls: Class<*>) {
        //kotlin case:start activity by intent
        Intent(this, cls).apply {
            putExtra("data1", "spring")
            putExtra("data2", "spring2")
        }.also {
            startActivity(it)
        }
    }
}
