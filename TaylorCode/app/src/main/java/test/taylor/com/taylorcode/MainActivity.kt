package test.taylor.com.taylorcode

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.annotation.Keep
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import test.taylor.com.taylorcode.activitystack.NewActivity
import test.taylor.com.taylorcode.activitystack.Param
import test.taylor.com.taylorcode.annotations.AnnotationActivity2
import test.taylor.com.taylorcode.architecture.StickyLiveDataActivity
import test.taylor.com.taylorcode.architecture.flow.lifecycle.NavigationFragmentActivity
import test.taylor.com.taylorcode.audio.AudioRecorderActivity
import test.taylor.com.taylorcode.audio.encoder.HWRecorderActivity
import test.taylor.com.taylorcode.aysnc.HandlerThreadVsCoroutineActivity
import test.taylor.com.taylorcode.aysnc.priority.SplashActivity
import test.taylor.com.taylorcode.aysnc.workmanager.WorkManagerActivity
import test.taylor.com.taylorcode.broadcast.BroadcastActivity
import test.taylor.com.taylorcode.concurrent.ConcurrentActivity
import test.taylor.com.taylorcode.concurrent.ConcurrentInitActivity
import test.taylor.com.taylorcode.concurrent.ThreadPoolActivity
import test.taylor.com.taylorcode.concurrent.countdownlatch.CountDownLatchActivity
import test.taylor.com.taylorcode.cover_by_fragment.CoveredByFragmentActivity
import test.taylor.com.taylorcode.data_persistence.RoomActivity
import test.taylor.com.taylorcode.data_persistence.okio.OkioActivity
import test.taylor.com.taylorcode.design_mode.responsible_chain.ResponsibilityChainActivity
import test.taylor.com.taylorcode.dns.DnsActivity
import test.taylor.com.taylorcode.file.FileActivity
import test.taylor.com.taylorcode.gson.GsonActivity
import test.taylor.com.taylorcode.interview.InterViewActivity
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.Channel.ChannelActivity
import test.taylor.com.taylorcode.kotlin.collection.KotlinCollectionActivity
import test.taylor.com.taylorcode.kotlin.coroutine.*
import test.taylor.com.taylorcode.kotlin.coroutine.flow.FlowActivity2
import test.taylor.com.taylorcode.kotlin.coroutine.flow.SharedFlowActivity
import test.taylor.com.taylorcode.kotlin.coroutine.mvi.StateFlowActivity
import test.taylor.com.taylorcode.kotlin.delegate.DelegateActivity
import test.taylor.com.taylorcode.kotlin.extension.contentView
import test.taylor.com.taylorcode.kotlin.extension.decorView
import test.taylor.com.taylorcode.kotlin.extension.onVisibilityChange
import test.taylor.com.taylorcode.kotlin.invoke.InvokeActivity
import test.taylor.com.taylorcode.kotlin.override_operator.OverrideOperatorActivity
import test.taylor.com.taylorcode.kotlin.override_property.OverridePropertyActivity
import test.taylor.com.taylorcode.lifecycle.LifecycleActivity
import test.taylor.com.taylorcode.list.ListActivity
import test.taylor.com.taylorcode.log.LogActivity
import test.taylor.com.taylorcode.new_activity_result.NewActivityResultActivity
import test.taylor.com.taylorcode.no_field.NoFieldActivity
import test.taylor.com.taylorcode.photo.GlideActivity
import test.taylor.com.taylorcode.photo.GlideActivity3
import test.taylor.com.taylorcode.proxy.remote.RemoteDynamicProxyActivity
import test.taylor.com.taylorcode.retrofit.god_activity.GodActivity
import test.taylor.com.taylorcode.retrofit.repository_single.RetrofitActivity
import test.taylor.com.taylorcode.rxjava.LoginActivity
import test.taylor.com.taylorcode.rxjava.SingleActivity
import test.taylor.com.taylorcode.sp.SharedPreferenceActivity
import test.taylor.com.taylorcode.type_parameter.TypeParameterActivity
import test.taylor.com.taylorcode.ui.*
import test.taylor.com.taylorcode.ui.anim.AddViewActivity
import test.taylor.com.taylorcode.ui.anim.AnimActivity
import test.taylor.com.taylorcode.ui.anim.TransitionManagerActivity
import test.taylor.com.taylorcode.ui.custom_view.DrawTriangleActivity
import test.taylor.com.taylorcode.ui.custom_view.blur.BlurActivity
import test.taylor.com.taylorcode.ui.custom_view.blur.BlurActivity2
import test.taylor.com.taylorcode.ui.custom_view.bullet_screen.LaneLayoutManagerActivity
import test.taylor.com.taylorcode.ui.custom_view.bullet_screen.LaneViewActivity
import test.taylor.com.taylorcode.ui.custom_view.bullet_screen.LiveCommentActivity
import test.taylor.com.taylorcode.ui.custom_view.crop_view.CropActivity
import test.taylor.com.taylorcode.ui.custom_view.overlap_anim.OverlapAnimActivity
import test.taylor.com.taylorcode.ui.custom_view.path.PathActivity
import test.taylor.com.taylorcode.ui.custom_view.progress_view.ProgressBarActivity
import test.taylor.com.taylorcode.ui.custom_view.recyclerview_indicator.IndicatorActivity
import test.taylor.com.taylorcode.ui.custom_view.selector.SelectorDemoActivity
import test.taylor.com.taylorcode.ui.custom_view.shader.ShaderActivity
import test.taylor.com.taylorcode.ui.custom_view.tag_view.TagTextViewActivity
import test.taylor.com.taylorcode.ui.custom_view.time_picker.TimePickerActivity
import test.taylor.com.taylorcode.ui.custom_view.treasure_box.TreasureActivity
import test.taylor.com.taylorcode.ui.databinding.DataBindingActivity
import test.taylor.com.taylorcode.ui.flow.ConstraintLayoutFlowActivity
import test.taylor.com.taylorcode.ui.flow.FlowActivity
import test.taylor.com.taylorcode.ui.fragment.DialogFragment1
import test.taylor.com.taylorcode.ui.fragment.FragmentActivity
import test.taylor.com.taylorcode.ui.line_feed_layout.TagActivity
import test.taylor.com.taylorcode.ui.material_design.ViewPagerActivity2
import test.taylor.com.taylorcode.ui.material_design.CoordinateLayoutActivity
import test.taylor.com.taylorcode.ui.material_design.nested.NestedScrollCoordinateLayoutActivity
import test.taylor.com.taylorcode.ui.material_design.nested.NestedScrollViewActivity
import test.taylor.com.taylorcode.ui.navigation.NavigationActivity
import test.taylor.com.taylorcode.ui.night_mode.BaseActivity
import test.taylor.com.taylorcode.ui.night_mode.TestMaskActivity
import test.taylor.com.taylorcode.ui.notification.NotificationActivity
import test.taylor.com.taylorcode.ui.one.OneActivity
import test.taylor.com.taylorcode.ui.pagers.ViewPager2Activity
import test.taylor.com.taylorcode.ui.pagers.ViewPager2Activity2
import test.taylor.com.taylorcode.ui.pagers.ViewPagerActivity
import test.taylor.com.taylorcode.ui.performance.RecyclerViewPerformanceActivity
import test.taylor.com.taylorcode.ui.performance.recyclerview_item_anim.RecyclerViewItemAnimActivity
import test.taylor.com.taylorcode.ui.performance.widget.PercentActivity
import test.taylor.com.taylorcode.ui.recyclerview.StaggerLayoutManagerActivity
import test.taylor.com.taylorcode.ui.recyclerview.anim.RecyclerViewAnimActivity
import test.taylor.com.taylorcode.ui.recyclerview.anim2.RecyclerViewItemAnimatorActivity
import test.taylor.com.taylorcode.ui.recyclerview.grid_layout.GridLayoutActivity
import test.taylor.com.taylorcode.ui.recyclerview.nest_recyclerView.NestedRecyclerViewActivity
import test.taylor.com.taylorcode.ui.recyclerview.select.SelectRecycleViewActivity
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapterActivity
import test.taylor.com.taylorcode.ui.state_cross_activities.LiveDataActivity
import test.taylor.com.taylorcode.ui.state_cross_activities.LiveDataActivity1
import test.taylor.com.taylorcode.ui.surface_view.SurfaceViewActivity
import test.taylor.com.taylorcode.ui.tablayout.BottomNavigationViewActivity
import test.taylor.com.taylorcode.ui.touch_event.TouchEventActivity
import test.taylor.com.taylorcode.ui.transparent_fragment.TransparentFragmentActivity
import test.taylor.com.taylorcode.ui.viewstub.ViewStubActivity
import test.taylor.com.taylorcode.ui.window.WindowActivity
import test.taylor.com.taylorcode.ui.zoom.ZoomActivity
import test.taylor.com.taylorcode.util.Countdown
import test.taylor.com.taylorcode.util.PhoneUtil
import test.taylor.com.taylorcode.webview.WebViewActivity
import kotlin.reflect.KClass

@Keep
class MainActivity : BaseActivity(), Param {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initView()
        readPhoneInfo()
        testValueDiliver();
        //        detectFrame()

        val job = Job()
        Log.v("ttaylor3333", "onCreate() job is active=${job.isActive}")
        val scope = CoroutineScope(Job() + Dispatchers.Default)
        Log.v("ttaylor3333", "onCreate() scope is active =${scope.isActive}")
    }

    private fun testValueDiliver() {
        Log.v("ttaylor", "tag=, MainActivity.testValueDiliver()  ")
        Log.v("ttaylor", "tag=, MainActivity.testValueDiliver()  ")
        Log.v("ttaylor", "tag=, MainActivity.testValueDiliver()  ")

        Log.v("ttaylor", "testValueDiliver() ${"0".toLong()}")
    }

    private fun readPhoneInfo() {
        Log.v(
            "ttaylor",
            "tag=, MainActivity.readPhoneInfo()  version=${PhoneUtil.getSystemVersion()}"
        )
        Log.v("ttaylor", "tag=, MainActivity.readPhoneInfo()  model=${PhoneUtil.getSystemModel()}")
        Log.v("ttaylor", "tag=, MainActivity.readPhoneInfo()  brand=${PhoneUtil.getBrand()}")
    }

    override fun onStart() {
        super.onStart()
        Log.v("ttaylor", "MainActivity.onStart()" + "  ")
    }

    override fun onResume() {
        super.onResume()
        Log.v("ttaylor", "MainActivity.onResume()" + "  btn_touch_event.width=${btn_touch_event.measuredWidth}")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.v("ttaylor", "MainActivity.onWindowFocusChanged() btn_touch_event.width=${btn_touch_event.measuredWidth}") // view has an dimension here
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



        val holder = Holder(reference)
        holder.doSth()
        reference.count = 2
        holder.doSth()
        val list = listOf<String>()
        btn_window.setOnClickListener { startActivity<WindowActivity>() }
        btn_touch_event.setOnClickListener { startActivity<TouchEventActivity>() }
        btn_selector.setOnClickListener { startActivity(SelectorDemoActivity::class.java) }
        btn_anim.setOnClickListener { startActivity(AnimActivity::class.java) }
        btn_view_pager.setOnClickListener { startActivity(ViewPagerActivity::class.java) }
        btn_navigation.setOnClickListener { startActivity(NavigationActivity::class.java) }
        btn_drawer_layout.setOnClickListener { startActivity(DrawerLayoutActivity::class.java) }
        btn_constraint_layout.setOnClickListener { startActivity(ConstraintLayoutActivity::class.java) }
        btn_tab_layout.setOnClickListener { startActivity(TableLayoutActivity::class.java) }
        btn_dialog.setOnClickListener {
            showDialog2()
            //            startActivity(DialogActivity::class.java)
        }
        btn_coordinate_layout.setOnClickListener { startActivity(CoordinateLayoutActivity::class.java) }
        btn_rx_binding.setOnClickListener { startActivity(LoginActivity::class.java) }
        btn_surface_view.setOnClickListener { startActivity(SurfaceViewActivity::class.java) }
        btn_kotlin_activity.setOnClickListener { startActivity<KotlinActivity>() }
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
        btn_broadcat.setOnClickListener { startActivity(BroadcastActivity::class.java) }
        btn_delegate.setOnClickListener { startActivity(DelegateActivity::class.java) }
        btn_operator.setOnClickListener { startActivity(OverrideOperatorActivity::class.java) }
        btn_vp2.setOnClickListener { startActivity<ViewPager2Activity>() }
        btn_flow.setOnClickListener { startActivity<FlowActivity>() }
        btn_constraintlayou.setOnClickListener { startActivity<ConstraintLayoutActivity3>() }
        btn_factory2.setOnClickListener { startActivity<Factory2Activity>() }
        btn_factory22.setOnClickListener { startActivity<Factory2Activity2>() }
        btn_data_binding.setOnClickListener { startActivity<DataBindingActivity>() }
        btnTransformations.setOnClickListener { startActivity<LiveDataActivity>() }
        btnRetrofit.setOnClickListener { startActivity<test.taylor.com.taylorcode.retrofit.viewmodel.RetrofitActivity>() }
        godActivity.setOnClickListener { startActivity<GodActivity>() }
        repository_single.setOnClickListener { startActivity<RetrofitActivity>() }
        repository_livedata.setOnClickListener { startActivity<test.taylor.com.taylorcode.retrofit.repository_livedata.RetrofitActivity>() }
        presenter.setOnClickListener { startActivity<test.taylor.com.taylorcode.retrofit.presenter.RetrofitActivity>() }
        btn_no_field.setOnClickListener { startActivity<NoFieldActivity>() }
        btnViewFlipper.setOnClickListener { startActivity<ViewFlipperActivity>() }
        btnCoroutineSample.setOnClickListener { startActivity<HandlerThreadVsCoroutineActivity>() }
        btnSuspendCrossActivities.setOnClickListener { startActivity<SplashActivity>() }
        btnCancelCoroutine.setOnClickListener { startActivity<CoroutineCancelActivity>() }
        btnSuspendCoroutine.setOnClickListener { startActivity<SuspendCoroutineActivity>() }
        btnDarkActivity.setOnClickListener { startActivity<DarkActivity>() }
        btnLifecycleActivity.setOnClickListener { startActivity<LifecycleActivity>() }
        btnChannelActivity.setOnClickListener { startActivity<ChannelActivity>() }
        btnDynamic.setOnClickListener { startActivity<DynamicalLayoutActivity>() }
        btnDns.setOnClickListener { startActivity<DnsActivity>() }
        btnMaskViewGroup.setOnClickListener { startActivity<TestMaskActivity>() }
        btnProgressBar.setOnClickListener { startActivity<ProgressBarActivity>() }
        btnVarietyAdapter.setOnClickListener { startActivity<VarietyAdapterActivity>() }
        btnSelectRecyclerView.setOnClickListener { startActivity<SelectRecycleViewActivity>() }
        btnRecyclerViewIndicator.setOnClickListener { startActivity<IndicatorActivity>() }
        btnTypeParameter.setOnClickListener { startActivity<TypeParameterActivity>() }
        btn_new_activity_result.setOnClickListener { startActivity<NewActivityResultActivity>() }
        btnPpath.setOnClickListener { startActivity<PathActivity>() }
        btnLiveComment.setOnClickListener { startActivity<LiveCommentActivity>() }
        btnAddViewActivity.setOnClickListener { startActivity<AddViewActivity>() }
        btn_livecomment.setOnClickListener { startActivity<LaneViewActivity>() }
        btnTransition.setOnClickListener { startActivity<test.taylor.com.taylorcode.ui.anim.transitionmanager.TransitionManagerActivity>() }
        btn_constraintlayout4.setOnClickListener { startActivity<ConstraintLayout4>() }
        btn_kotlin_flow.setOnClickListener { startActivity<test.taylor.com.taylorcode.kotlin.coroutine.flow.FlowActivity>() }
        btnBlur.setOnClickListener { startActivity<BlurActivity>() }
        btnBlur2.setOnClickListener { startActivity<BlurActivity2>() }
        btnCrop.setOnClickListener { startActivity<CropActivity>() }
        btnCrop2.setOnClickListener { startActivity<ShaderActivity>() }
        btnRecyclerViewPerformance.setOnClickListener { startActivity<RecyclerViewPerformanceActivity>() }
        btnPercentLayout.setOnClickListener { startActivity<PercentActivity>() }
        btnInterView.setOnClickListener { startActivity<InterViewActivity>() }
        btnTimePicker.setOnClickListener { startActivity<TimePickerActivity>() }
        btn_touch_delegate.setOnClickListener { startActivity<TouchDelegateActivity>() }
        btn_coordinate_layout2.setOnClickListener { startActivity<NestedScrollCoordinateLayoutActivity>() }
        recyclerview_item_anim.setOnClickListener { startActivity<RecyclerViewItemAnimActivity> { } }
        oneViewGroup.setOnClickListener { startActivity<OneActivity> { } }
        btnMultiTouchDelegate.setOnClickListener { startActivity<test.taylor.com.taylorcode.ui.touch_event.touch_delegate.TouchDelegateActivity> { } }
        poorDialogFragment.setOnClickListener { PoorDialogFragment.show(this@MainActivity) }
        btnNotificationActivity.setOnClickListener { startActivity<NotificationActivity> { } }
        btnAudioRecorder.setOnClickListener { startActivity<AudioRecorderActivity> { } }
        btnAudioEncoder.setOnClickListener { startActivity<HWRecorderActivity> { } }
        btnCoroutine.setOnClickListener { startActivity<CoroutineActivity2> { } }
        count_down_latch.setOnClickListener { startActivity<CountDownLatchActivity> { } }
        dutyChain.setOnClickListener { startActivity<ResponsibilityChainActivity> { } }
        RxSingle.setOnClickListener { startActivity<SingleActivity> { } }
        LaneLayoutManager.setOnClickListener { startActivity<LaneLayoutManagerActivity> { } }
        gridlayout.setOnClickListener { startActivity<GridLayoutActivity> { } }
        kotlinActivity2.setOnClickListener { startActivity<KotlinActivity2> { } }
        btn_vp3.setOnClickListener { startActivity<ViewPager2Activity2> { } }
        btn_overlap.setOnClickListener { startActivity<RecyclerViewAnimActivity> { } }
        btn_item_animator.setOnClickListener { startActivity<RecyclerViewItemAnimatorActivity> { } }
        btn_overlap_anim.setOnClickListener { startActivity<OverlapAnimActivity> { } }
        btn_imageView_scaleType.setOnClickListener { startActivity<ImageViewScaleTypeActivity> { } }
        btn_zoom.setOnClickListener { startActivity<ZoomActivity> { } }
        btn_okio.setOnClickListener { startActivity<OkioActivity> { } }
        btn_log.setOnClickListener { startActivity<LogActivity> { } }
        btn_concurrent_list.setOnClickListener { startActivity<test.taylor.com.taylorcode.aysnc.concurrent.ConcurrentActivity> { } }
        btn_test.setOnClickListener { startActivity<HookSystemServiceActivity> { } }
        btn_cover.setOnClickListener { startActivity<CoveredByFragmentActivity> { } }
        btnStickyFragment.setOnClickListener {
            startActivity<StickyLiveDataActivity> { }
            //            decorView?.addView(
            //                TextView {
            //                    layout_id = "tvChange"
            //                    layout_width = wrap_content
            //                    layout_height = wrap_content
            //                    textSize = 100f
            //                    textColor = "#FFff78"
            //                    text = "dkfjdlskfjsldfkj"
            //                    gravity = gravity_center
            //                    background_color = "#0000ff"
            //                }, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 700.dp))
        }
        btnStateFlow.setOnClickListener { startActivity<StateFlowActivity> { } }
        annotation.setOnClickListener { startActivity<AnnotationActivity2> { } }
        coroutineException.setOnClickListener { startActivity<CoroutineActivity3> { } }
        btnExceptionCoroutine.setOnClickListener { startActivity<CoroutineExceptionActivity> { } }
        btnStructConcurrency.setOnClickListener { startActivity<StructureConcurrencyActivity> { } }
        continuation.setOnClickListener { startActivity<test.taylor.com.taylorcode.kotlin.coroutine.mvvm.SuspendCoroutineActivity> { } }
        coroutineJob.setOnClickListener { startActivity<CoroutineJobActivity> { } }
        flowBackPressure.setOnClickListener { startActivity<FlowActivity2> { } }
        sharedFlow.setOnClickListener { startActivity<SharedFlowActivity> { } }
        btn_glide.setOnClickListener { startActivity<GlideActivity> { } }
        btn_glide_performance.setOnClickListener { startActivity<GlideActivity3> { } }
        btnConcurrentInit.setOnClickListener { startActivity<ConcurrentInitActivity> { } }
        btnChannelActivity2.setOnClickListener { startActivity<test.taylor.com.taylorcode.kotlin.coroutine.channel.ChannelActivity> { } }
        btn_collapsing.setOnClickListener { startActivity<ViewPagerActivity2> { } }
        btnTriangle.setOnClickListener { startActivity<DrawTriangleActivity> { } }
        bottom_navigation_view.setOnClickListener { startActivity<BottomNavigationViewActivity> { } }
        btn_constraintLayout_flow.setOnClickListener { startActivity<ConstraintLayoutFlowActivity> { } }
        staggerLayout.setOnClickListener { startActivity<StaggerLayoutManagerActivity> { } }
        staggerLayout.onVisibilityChange { view, isVisible ->
            Log.d("ttaylor", "MainActivity.initView[view(staggerLayout), isVisible($isVisible)]: ")
        }
        navigation.setOnClickListener { startActivity<NavigationFragmentActivity> { } }
        btnNestedRecyclerView.setOnClickListener { startActivity<NestedRecyclerViewActivity> { } }
        newActivity.setOnClickListener { startActivity<NewActivity> { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) } }
        DialogFragment.setOnClickListener {  DialogFragment1().show(supportFragmentManager, "ddd") }
        btn_nestedScrollView.setOnClickListener {
            startActivity<NestedScrollViewActivity> { }
            //            btn_javassist.visibility = if (btn_javassist.visibility == View.GONE) View.VISIBLE else View.GONE

        }
        btn_flow_lifecycle.setOnClickListener {
            PoorDialogFragment.show(this@MainActivity)
            //            startActivity<FlowLifecycleActivity> { }
        }
        btn_fragment_communicate.setOnClickListener { startActivity<FragmentActivity> { } }
        btn_javassist.setOnClickListener {

            //            startActivity<JavassistActivity> { }
        }

        btn_javassist.onVisibilityChange(listOf(decorView!!)) { view, i ->
            Log.w("ttaylor", "[onVisibilityChange]MainActivity.onVisibilityChange view.visibility=${visible},isShow=$i")
        }


        //SAM case:
        val onClickListener = View.OnClickListener { Log.v("ttaylor", "tag=SAM, view id=${it.id}") }

        /**
         * case: list.flat and list.flatMap
         */
        val lists = listOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6)
        )
        Log.v("ttaylor", "[flatten] list.flatten=${lists.flatten()}")
        Log.v("ttaylor", "[flatten] list.=${lists.flatMap { it.map { it + 1 } }}")


        /**
         * case:  flow.flatMapConcat
         */
        fun plusFlow(num: Int) = flow {
            emit(num + 1)
            kotlinx.coroutines.delay(1000)
        }

        lifecycleScope.launch {
            /**
             * turn Flow<Int> into Flow<Flow<Int>> and flat it
             */
            (1..6).asFlow().flatMapConcat { plusFlow(it) }.collect {
                Log.v("ttaylor", "[flatMapConcat] num=${it}")
            }
        }

        /**
         * case: concat flows by serial
         */
        lifecycleScope.launch {
            flowOf(
                flow {
                    emit(1)
                    emit(2)
                },
                flow { emit(3) },
                flow {
                    emit(4)
                    emit(5)
                },
            ).flattenConcat().collect {
                Log.v("ttaylor", "[flattenConcat] num=${it}")
            }
        }


        btn_room.setOnClickListener(onClickListener)
        1280.fmtCount().let { Log.v("ttaylor", "tag=aaaadf, MainActivity.initView()  it=${it}") }

//        val str: String = (java.lang.String("0").bytes.sum() - 48).toString()
//        Log.v("ttaylor", "tag=asdff, MainActivity.initView()  ${java.lang.String(str).bytes.sum()}")

        Countdown(10000, 1000) { it }.apply {
            onStart = { Log.v("ttaylor", "countdown start---------") }
            onEnd = { ret -> Log.v("ttaylor", "countdown end--------- ret=${ret}") }
            accumulator = { acc, value -> acc + value }
        }.start()


        /**
         * content view case : add view to content view
         */
        val tv = TextView {
            layout_width = wrap_content
            layout_height = wrap_content
            text = "add from content view"
            textSize = 20f
            gravity = gravity_center
        }

        contentView()?.addView(
            tv,
            android.widget.FrameLayout.LayoutParams(wrap_content, wrap_content).apply {
                this.topMargin = 100.dp
                this.marginStart = 50.dp
            })

        val atString = "//@有毛病：早安哦//@天天：小甜甜"

        val sb = StringBuffer()
        atString.splitAtString(
            { string, highlight ->
                sb.append("$string($highlight)")
            }, {
                sb.append("//")
            }, {
                sb.append("：")
            }
        )
        Log.v("ttaylor", "tag=, MainActivity.initView()   splitAtString=${sb.toString()}")

    }

    override fun onBackPressed() {
        val view = decorView?.find<TextView>("tvChange")
        decorView?.removeView(view)
        //        super.onBackPressed()
    }

    private fun showDialog2() {
        val bottomDialog = Dialog(this, R.style.BottomDialog)
        val contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width = ViewGroup.MarginLayoutParams.MATCH_PARENT
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.BOTTOM)
        bottomDialog.window?.attributes?.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        bottomDialog.window!!.setWindowAnimations(R.style.ActionSheetDialogAnimation)
        bottomDialog.show()
    }

    /**
     * dialog case2:show dialog in the center of screen
     */
    private fun showDialog3() {
        val bottomDialog = Dialog(this, R.style.TransparentDialog)
        val contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content, null)
        bottomDialog.setContentView(contentView)
        bottomDialog.setCanceledOnTouchOutside(false)
        //        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.show()
    }


    private fun String.splitAtString(
        onEach: (String, Boolean) -> Unit,
        onEntryEnd: () -> Unit,
        onSubEntryEnd: () -> Unit
    ) {
        val entryList = split("//")
        entryList.forEachIndexed { index, s ->
            val subEntryList = s.split("：")
            subEntryList.forEachIndexed { subIndex, splitString ->
                onEach(splitString, splitString.startsWith("@"))
                if (subIndex != subEntryList.size - 1) {
                    onSubEntryEnd()
                }
            }
            if (index != entryList.size - 1) {
                onEntryEnd()
            }
        }
    }


    fun Int.fmtCount(): String = let {
        return when {
            it >= 1000 -> {
                val integer = it / 1000
                val digit = it % 1000
                StringBuilder()
                    .append(integer)
                    .apply {
                        if (digit != 0) {
                            append(".${digit / 100}")
                        }
                    }
                    .append("k")
                    .toString()
            }
            else -> it.toString()
        }
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

    override val paramMap: Map<String, Any>
        get() = mapOf(
            "type" to 1,
            "tabName" to "ddd",
            "map" to mapOf(
                "111" to 1,
                "222" to 2,
            )
        )
}

/**
 * reified case: type wont be erased
 */
inline fun <reified T> Context.startActivity(extras: Intent.() -> Unit = {}) {
    Intent(this, T::class.java).apply(extras).also { startActivity(it) }
}

fun Float.cutEndZero(): String = this.toString().reversed().let {
    val dotIndex = it.indexOf('.')
    var zeroCount = 0
    for (i in 0 until dotIndex) {
        if (it[i] != '0') break
        zeroCount++
    }
    val ret = it.takeLast(it.length - zeroCount).reversed()
    if (ret.endsWith('.')) ret.dropLast(1) else ret
}

open class Message(val id: String = "", val content: String = "")
interface MessageHandler<T : Message> {
    fun handleMessage(message: T)
}

class VoiceMessage(id: String = "", content: String = "", val voiceData: String = "") :
    Message(id, content)

class VideoMessage(id: String = "", content: String = "", val videoData: String = "") :
    Message(id, content)

class VoiceMessageHandler : MessageHandler<VoiceMessage> {
    override fun handleMessage(message: VoiceMessage) {
        Log.v("ttaylor", "voice message is handled id = ${message.id} data=${message.voiceData}")
    }
}

class VideoMessageHandler : MessageHandler<VideoMessage> {
    override fun handleMessage(message: VideoMessage) {
        Log.v("ttaylor", "video message is handled id = ${message.id} data=${message.videoData}")
    }
}

class MessageReceiver {
    val messageHandlerMap = mutableMapOf<KClass<*>, MessageHandler<*>>()

    inline fun <reified T : Message> addMessageHandler(handler: MessageHandler<T>) {
        messageHandlerMap[T::class] = handler
    }

    fun onReceiveMessage(message: Message) {
        (messageHandlerMap[message::class] as? MessageHandler<Message>)?.handleMessage(message)
    }
}

/**
 * case: @IntDef in kotlin
 */
@IntDef(INT1, INT2)
annotation class IntType

const val INT1 = 1
const val INT2 = 2

class Reference(var count: Int, var str: String)

var reference = Reference(1, "ddd")


class Holder(private var reference: Reference) {
    fun doSth() {
        Log.v("ttaylor", "[reference test] ${reference.count}")
    }
}