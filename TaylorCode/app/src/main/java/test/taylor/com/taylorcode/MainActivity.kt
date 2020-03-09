package test.taylor.com.taylorcode

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*
import test.taylor.com.taylorcode.aysnc.workmanager.WorkManagerActivity
import test.taylor.com.taylorcode.concurrent.ConcurrentActivity
import test.taylor.com.taylorcode.concurrent.ThreadPoolActivity
import test.taylor.com.taylorcode.data_persistence.RoomActivity
import test.taylor.com.taylorcode.file.FileActivity
import test.taylor.com.taylorcode.gson.GsonActivity
import test.taylor.com.taylorcode.kotlin.AnoymousFunActivity
import test.taylor.com.taylorcode.kotlin.KotlinExample
import test.taylor.com.taylorcode.kotlin.collection.KotlinCollectionActivity
import test.taylor.com.taylorcode.kotlin.coroutine.CoroutineActivity
import test.taylor.com.taylorcode.kotlin.invoke.InvokeActivity
import test.taylor.com.taylorcode.kotlin.override_property.OverridePropertyActivity
import test.taylor.com.taylorcode.list.ListActivity
import test.taylor.com.taylorcode.proxy.remote.RemoteDynamicProxyActivity
import test.taylor.com.taylorcode.rxjava.LoginActivity
import test.taylor.com.taylorcode.sp.SharedPreferenceActivity
import test.taylor.com.taylorcode.ui.*
import test.taylor.com.taylorcode.ui.anim.AnimActivity
import test.taylor.com.taylorcode.ui.anim.TransitionManagerActivity
import test.taylor.com.taylorcode.ui.custom_view.selector.SelectorDemoActivity
import test.taylor.com.taylorcode.ui.custom_view.tag_view.TagTextViewActivity
import test.taylor.com.taylorcode.ui.custom_view.treasure_box.TreasureActivity
import test.taylor.com.taylorcode.ui.line_feed_layout.TagActivity
import test.taylor.com.taylorcode.ui.material_design.CoordinateActivity
import test.taylor.com.taylorcode.ui.navigation.NavigationActivity
import test.taylor.com.taylorcode.ui.pagers.ViewPagerActivity
import test.taylor.com.taylorcode.ui.state_cross_activities.LiveDataActivity1
import test.taylor.com.taylorcode.ui.surface_view.SurfaceViewActivity
import test.taylor.com.taylorcode.ui.touch_event.TouchEventActivity
import test.taylor.com.taylorcode.ui.transparent_fragment.TransparentFragmentActivity
import test.taylor.com.taylorcode.ui.viewstub.ViewStubActivity
import test.taylor.com.taylorcode.ui.window.WindowActivity
import test.taylor.com.taylorcode.util.PhoneUtil
import test.taylor.com.taylorcode.webview.WebViewActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initView()
        readPhoneInfo()
        testValueDiliver();
        Log.v("ttaylor", "MainActivity.onCreate()" + "  ")
    }

    private fun testValueDiliver() {
    }

    private fun readPhoneInfo() {
        Log.v("ttaylor", "tag=, MainActivity.readPhoneInfo()  version=${PhoneUtil.getSystemVersion()}")
        Log.v("ttaylor", "tag=, MainActivity.readPhoneInfo()  model=${PhoneUtil.getSystemModel()}")
        Log.v("ttaylor", "tag=, MainActivity.readPhoneInfo()  brand=${PhoneUtil.getBrand()}")
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
        btn_llive.setOnClickListener {
            Log.v("ttaylor", "tag=, MainActivity.initView()  ")
            startActivity(LiveDataActivity1::class.java)
        }
        btn_window.setOnClickListener { startActivity(WindowActivity::class.java) }
        btn_touch_event.setOnClickListener { startActivity(TouchEventActivity::class.java) }
        btn_selector.setOnClickListener { startActivity(SelectorDemoActivity::class.java) }
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
        btn_transparent_fragment.setOnClickListener { startActivity(TransparentFragmentActivity::class.java) }
        btnOverrideProperty.setOnClickListener { startActivity(OverridePropertyActivity::class.java) }
        btnKotlinCollection.setOnClickListener { startActivity(KotlinCollectionActivity::class.java) }
        btnInvoke.setOnClickListener { startActivity(InvokeActivity::class.java) }
        btn_anonymous_fun.setOnClickListener { startActivity(AnoymousFunActivity::class.java) }
        btn_constraint_set_layout.setOnClickListener { startActivity(TransitionManagerActivity::class.java) }
        btn_tag_activity.setOnClickListener { startActivity(TagActivity::class.java) }
        btn_tag_view_activity.setOnClickListener { startActivity(TagViewActivity::class.java) }
        btn_constraint_add.setOnClickListener { startActivity(ConstraintLayoutActivity2::class.java) }
        btn_tag_textview.setOnClickListener { startActivity(TagTextViewActivity::class.java) }
        btn_ipc.setOnClickListener { startActivity(RemoteDynamicProxyActivity::class.java) }
        btn_coroutine.setOnClickListener { startActivity(CoroutineActivity::class.java) }
        btn_spannable.setOnClickListener { startActivity(SpannableActivity::class.java) }
        btn_concurrent.setOnClickListener { startActivity(ConcurrentActivity::class.java) }
        btn_webview.setOnClickListener { startActivity(WebViewActivity::class.java) }
        btn_list.setOnClickListener { startActivity(ListActivity::class.java) }
        btn_threadpool.setOnClickListener { startActivity(ThreadPoolActivity::class.java) }
        btn_output_stream.setOnClickListener { startActivity(FileActivity::class.java) }
        btn_threadpool.setOnClickListener { startActivity(ThreadPoolActivity::class.java) }
        btn_output_stream.setOnClickListener { startActivity(FileActivity::class.java) }
        btn_treasure.setOnClickListener { startActivity(TreasureActivity::class.java) }
        btn_workmanager.setOnClickListener { startActivity(WorkManagerActivity::class.java) }
        btn_gson.setOnClickListener { startActivity(GsonActivity::class.java) }
        btn_sp.setOnClickListener { startActivity(SharedPreferenceActivity::class.java) }

        //SAM case:
        val onClickListener = View.OnClickListener { Log.v("ttaylor", "tag=SAM, view id=${it.id}") }

        btn_room.setOnClickListener(onClickListener)
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
