package test.taylor.com.taylorcode.log

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.log.interceptor.DailyFileWriterLogInterceptor
import test.taylor.com.taylorcode.log.interceptor.DailyOkioLogInterceptor
import test.taylor.com.taylorcode.log.interceptor.LogcatInterceptor

class LogActivity : AppCompatActivity() {

    val str1 =
        "tangtang 2022-03-08 19:52:29.419 4522-6891/? E/TencentNLP: java.lang.SecurityException: getSubscriberId: The user 10652 does not meet the requirements to access device identifiers.\n" +
                "2022-03-08 19:52:29.495 20915-20915/? E/DownloadManager: Could not get ApplicationInfo for com.android.defconatiner\n" +
                "    android.content.pm.PackageManagerNameNotFoundException: com.android.defcontainer\n" +
                "        at android.app.ApplicationPackageManager.getApplicationInfoAsUser(ApplicationPackageManager.java:442)\n" +
                "        at android.app.ApplicationPackageManager.getApplicationInfo(ApplicationPackageManager.java:423)\n" +
                "        at com.android.providers.downloads.DownloadProvider.onCreate(DownloadProvider.java:749)\n" +
                "        at android.content.ContentProvider.attachInfo(ContentProvider.java:2102)\n" +
                "        at android.content.ContentProvider.attachInfo(ContentProvider.java:2076)\n" +
                "        at android.app.ActivityThread.installProvider(ActivityThread.java:7446)\n" +
                "        at android.app.ActivityThread.installContentProviders(ActivityThread.java:6952)\n" +
                "        at android.app.ActivityThread.handleBindApplication(ActivityThread.java:6847)\n" +
                "        at android.app.ActivityThread.access\$1800(ActivityThread.java:243)\n" +
                "        at android.app.ActivityThread$.handleMessage(ActivityThread.java:2080)\n" +
                "        at android.os.Handler.dispatchMessage(Handler.java:107)\n" +
                "        at android.os.Looper.loop(Looper.java:227)\n" +
                "        at android.app.ActivityThread.main(ActivityThread.java:7824)\n" +
                "        at java.lang.reflect.Method.invoke(Native Method)\n" +
                "        at com.android.internal.os.RuntimeInitMethodAndArgsCaller.run(RuntimeInit.java:492)\n" +
                "        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:984)\n" +
                "2022-03-08 19:52:29.524 574-574/? E/SELinux: avc:  denied  { find } for interface=vendor.qti.hardw"


    val str2 =
        "tangtang 2022-03-08 19:47:04.415 574-574/? E/SELinux: avc:  denied  { find } for interface=vendor.qti.hardware.servicetracker::IServicetracker sid=u:r:system_server:s0 pid=2218 scontext=u:r:system_server:s0 tcontext=u:object_r:default_android_hwservice:s0 tclass=hwservice_manager permissive=0\n" +
                "2022-03-08 19:47:04.420 574-574/? E/SELinux: avc:  denied  { find } for interface=vendor.qti.hardware.servicetracker::IServicetracker sid=u:r:system_server:s0 pid=2218 scontext=u:r:system_server:s0 tcontext=u:object_r:default_android_hwservice:s0 tclass=hwservice_manager permissive=0\n" +
                "2022-03-08 19:47:04.428 1560-20622/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:47:09.148 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:47:09.430 574-574/? E/SELinux: avc:  denied  { find } for interface=vendor.qti.hardware.servicetracker::IServicetracker sid=u:r:system_server:s0 pid=2218 scontext=u:r:system_server:s0 tcontext=u:object_r:default_android_hwservice:s0 tclass=hwservice_manager permissive=0\n" +
                "2022-03-08 19:47:09.433 574-574/? E/SELinux: avc:  denied  { find } for interface=vendor.qti.hardware.servicetracker::IServicetracker sid=u:r:system_server:s0 pid=2218 scontext=u:r:system_server:s0 tcontext=u:object_r:default_android_hwservice:s0 tclass=hwservice_manager permissive=0\n" +
                "2022-03-08 19:47:15.119 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:47:15.977 25611-25612/? E/rutils: releaseProcess gCount = 1\n" +
                "2022-03-08 19:47:17.224 1560-20630/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:47:17.226 1560-20629/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:47:17.241 1560-20631/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:47:19.443 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:47:21.292 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:47:27.069 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:47:32.131 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:47:35.512 18868-20642/? E/RegService_OppoDmykTelephonyManager: getPrimarySlotId: no SIM.\n" +
                "2022-03-08 19:47:35.582 1560-20647/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:47:38.101 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:47:39.235 1560-20671/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:47:39.236 1560-20672/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:47:39.238 1560-20670/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:47:44.332 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:47:45.977 25611-25612/? E/rutils: releaseProcess gCount = 1\n" +
                "2022-03-08 19:47:50.105 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:47:56.027 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:48:01.061 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:48:06.094 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:48:07.249 1560-20687/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:48:07.249 1560-20685/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:48:07.251 1560-20686/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:48:11.146 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:48:15.977 25611-25612/? E/rutils: releaseProcess gCount = 1\n" +
                "2022-03-08 19:48:16.986 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:48:21.274 1560-20697/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:48:21.275 1560-20696/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:48:21.280 1560-20698/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:48:22.106 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:48:27.994 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:48:33.229 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:48:37.286 1560-20706/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:48:37.286 1560-20707/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:48:37.304 1560-20708/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:48:39.003 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:48:44.237 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:48:45.977 25611-25612/? E/rutils: releaseProcess gCount = 1\n" +
                "2022-03-08 19:48:50.014 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:48:51.735 1560-20721/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:48:51.736 1560-20722/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:48:51.747 1560-20724/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:48:52.033 1560-20732/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:48:55.078 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:48:55.301 1560-20736/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:48:55.719 1560-20737/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:49:00.093 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:49:05.148 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:49:11.095 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:49:15.305 1560-20762/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:49:15.305 1560-20763/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:49:15.307 1560-20764/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:49:15.978 25611-25612/? E/rutils: releaseProcess gCount = 1\n" +
                "2022-03-08 19:49:16.154 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:49:19.443 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:49:22.122 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:49:22.911 30856-9952/? E/memtrack: Couldn't load memtrack module\n" +
                "2022-03-08 19:49:24.367 30914-9659/? E/memtrack: Couldn't load memtrack module\n" +
                "2022-03-08 19:49:28.242 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:49:30.332 4273-19755/? E/ChromeSync: [Sync,SyncIntentOperation] Error handling the intent: Intent { act=android.intent.action.PACKAGE_ADDED dat=package:test.taylor.com.taylorcode flg=0x4000010 cmp=com.google.android.gms/.chimera.GmsIntentOperationService (has extras) }.\n" +
                "2022-03-08 19:49:34.154 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:49:37.343 574-574/? E/SELinux: avc:  denied  { find } for interface=vendor.qti.hardware.servicetracker::IServicetracker sid=u:r:system_server:s0 pid=2218 scontext=u:r:system_server:s0 tcontext=u:object_r:default_android_hwservice:s0 tclass=hwservice_manager permissive=0\n" +
                "2022-03-08 19:49:40.187 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:49:45.978 25611-25612/? E/rutils: releaseProcess gCount = 1\n" +
                "2022-03-08 19:49:46.171 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:49:52.219 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:49:58.138 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:50:04.107 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:50:04.794 2218-2376/? E/AppIdleHistory: Error writing app idle file for user 0\n" +
                "2022-03-08 19:50:08.822 3236-13599/? E/Quality-PerformanceUtils: /sys/devices/system/cpu/cpufreq/policy6/freq_change_info doesn't exist\n" +
                "2022-03-08 19:50:09.003 3236-13599/? E/Quality-PerformanceUtils: /sys/kernel/hypnus/status doesn't exist\n" +
                "2022-03-08 19:50:10.212 1728-1728/? E/OPPO_KEVENT_RECORD: oppo_kevent Receive message from kernel, event_type=1\n" +
                "2022-03-08 19:50:10.225 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:50:11.322 3236-13599/? E/Quality-PerformanceUtils: /sys/devices/system/cpu/cpufreq/policy6/freq_change_info doesn't exist\n" +
                "2022-03-08 19:50:11.497 3236-13599/? E/Quality-PerformanceUtils: /sys/kernel/hypnus/status doesn't exist\n" +
                "2022-03-08 19:50:13.285 574-574/? E/SELinux: avc:  denied  { find } for interface=vendor.qti.hardware.servicetracker::IServicetracker sid=u:r:system_server:s0 pid=2218 scontext=u:r:system_server:s0 tcontext=u:object_r:default_android_hwservice:s0 tclass=hwservice_manager permissive=0\n" +
                "2022-03-08 19:50:13.338 574-574/? E/SELinux: avc:  denied  { find } for interface=vendor.qti.hardware.servicetracker::IServicetracker sid=u:r:system_server:s0 pid=2218 scontext=u:r:system_server:s0 tcontext=u:object_r:default_android_hwservice:s0 tclass=hwservice_manager permissive=0\n" +
                "2022-03-08 19:50:13.341 3236-14188/? E/SafeCenter.DWB.ActivityMonitor: onProcessDied android.uid.system:1000\n" +
                "2022-03-08 19:50:13.876 3236-13599/? E/Quality-PerformanceUtils: /sys/devices/system/cpu/cpufreq/policy6/freq_change_info doesn't exist\n" +
                "2022-03-08 19:50:14.065 3236-13599/? E/Quality-PerformanceUtils: /sys/kernel/hypnus/status doesn't exist\n" +
                "2022-03-08 19:50:15.978 25611-25612/? E/rutils: releaseProcess gCount = 1\n" +
                "2022-03-08 19:50:16.142 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:50:16.378 3236-13599/? E/Quality-PerformanceUtils: /sys/devices/system/cpu/cpufreq/policy6/freq_change_info doesn't exist\n" +
                "2022-03-08 19:50:16.547 3236-13599/? E/Quality-PerformanceUtils: /sys/kernel/hypnus/status doesn't exist\n" +
                "2022-03-08 19:50:16.560 3236-13599/? E/Quality-PerformanceUtils: /sys/devices/system/cpu/cpufreq/policy6/freq_change_info doesn't exist\n" +
                "2022-03-08 19:50:16.737 3236-13599/? E/Quality-PerformanceUtils: /sys/kernel/hypnus/status doesn't exist\n" +
                "2022-03-08 19:50:17.360 1560-20835/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:50:17.362 1560-20837/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:50:17.377 1560-20836/? E/ResolverController: No valid NAT64 prefix (100, <unspecified>/0)\n" +
                "2022-03-08 19:50:22.199 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:50:28.058 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-08 19:50:32.312 29943-30117/? E/OpenID: com.nearme.statistics.rom:DUID:ret:T\n" +
                "2022-03-08 19:50:32.315 29943-30117/? E/OpenID: com.nearme.statistics.rom:OUID:ret:T\n" +
                "2022-03-08 19:50:32.318 29943-29960/? E/OpenID: com.nearme.statistics.rom:GUID:ret:T\n" +
                "2022-03-08 19:50:32.321 29943-29960/? E/OpenID: com.nearme.statistics.rom:OUID_STATUS:ret:T"


    var count = 0

    val str3 = "tangtang dkfdlkfjlds ${count++}"
    val strs = listOf(str1,str2,str3)
    private lateinit var tv:TextView
    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            tv = TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                textColor = "#ffffff"
                gravity = gravity_center
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(contentView)
//        EasyLog.addInterceptor(DailyFileWriterLogInterceptor.getInstance(this.filesDir.absolutePath))
        EasyLog.addInterceptor(DailyOkioLogInterceptor.getInstance(this.filesDir.absolutePath))
        EasyLog.addInterceptor(LogcatInterceptor())

        MainScope().launch(Dispatchers.Default) {
            repeat(8000){
                strs.forEach {
                    EasyLog.v(it)
                }
            }
            EasyLog.v("work done")
        }

        /**
         * foreach case : return like break, return@forEach like continue
         */
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 2) return
            Log.v("ttaylor", "onCreate() foreach =$it")
        }
    }

    fun String.format(vararg args: Any?) =
        if (args.isNullOrEmpty()) this else String.format(this, *args)

}