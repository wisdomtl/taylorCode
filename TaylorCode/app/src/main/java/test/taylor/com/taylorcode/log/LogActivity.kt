package test.taylor.com.taylorcode.log

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Debug
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.taylor.easylog.EasyLog
import com.taylor.easylog.OkioLogInterceptor
import test.taylor.com.taylorcode.log.interceptor.FileWriterLogInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okio.*
import okio.ByteString.Companion.encodeUtf8
import java.io.File
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

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


    val str4 =
        "2022-03-09 19:12:47.647 9373-9373/test.taylor.com.taylorcode V/ttaylor: countdown end--------- ret=45000\n" +
                "2022-03-09 19:12:47.758 21090-21305/? I/SocketReaderNewtrue: SocketReader(3456) continue\n" +
                "2022-03-09 19:12:47.759 21090-21305/? D/MSF.C.NetConnTag.true: MsfCoreSocketReaderNew closeConn readError\n" +
                "2022-03-09 19:12:47.759 21090-21305/? D/MSF.C.NetConnTag.true: conn is already closed on readError\n" +
                "2022-03-09 19:12:47.760 21090-21305/? W/System.err: java.net.SocketException: Socket is closed\n" +
                "2022-03-09 19:12:47.760 21090-21305/? W/System.err:     at java.net.Socket.getSoTimeout(Socket.java:1196)\n" +
                "2022-03-09 19:12:47.760 21090-21305/? W/System.err:     at com.tencent.qphone.base.util.MsfSocketInputBuffer.isDataAvailable(P:71)\n" +
                "2022-03-09 19:12:47.760 21090-21305/? I/SocketReaderNewtrue: SocketReader(3456) wait\n" +
                "2022-03-09 19:12:48.499 32254-32254/? W/DetectPable: type=1400 audit(0.0:616394): avc: denied { getattr } for path=\"/proc/fb\" dev=\"proc\" ino=4026531991 scontext=u:r:shell:s0 tcontext=u:object_r:proc:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:48.503 32254-32254/? W/DetectPable: type=1400 audit(0.0:616395): avc: denied { getattr } for path=\"/proc/keys\" dev=\"proc\" ino=4026532181 scontext=u:r:shell:s0 tcontext=u:object_r:proc_keys:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:48.503 32254-32254/? W/DetectPable: type=1400 audit(0.0:616396): avc: denied { getattr } for path=\"/proc/kmsg\" dev=\"proc\" ino=4026532126 scontext=u:r:shell:s0 tcontext=u:object_r:proc_kmsg:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:48.503 32254-32254/? W/DetectPable: type=1400 audit(0.0:616397): avc: denied { getattr } for path=\"/proc/misc\" dev=\"proc\" ino=4026531992 scontext=u:r:shell:s0 tcontext=u:object_r:proc_misc:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:48.503 32254-32254/? W/DetectPable: type=1400 audit(0.0:616398): avc: denied { getattr } for path=\"/proc/iomem\" dev=\"proc\" ino=4026532155 scontext=u:r:shell:s0 tcontext=u:object_r:proc_iomem:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:48.576 21090-21206/? D/MemoryCeilingMonitor: [handleMemoryCalculate]\n" +
                "2022-03-09 19:12:48.576 21090-21206/? D/MemoryCeilingMonitor: [calculate] tot: 7300200, max: 402653184, free: 3154848, heap: 4145352, per: 0.010295093059539795\n" +
                "2022-03-09 19:12:49.571 32254-32254/? W/DetectPable: type=1400 audit(0.0:616458): avc: denied { getattr } for path=\"/proc/fb\" dev=\"proc\" ino=4026531991 scontext=u:r:shell:s0 tcontext=u:object_r:proc:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:49.571 32254-32254/? W/DetectPable: type=1400 audit(0.0:616459): avc: denied { getattr } for path=\"/proc/keys\" dev=\"proc\" ino=4026532181 scontext=u:r:shell:s0 tcontext=u:object_r:proc_keys:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:49.571 32254-32254/? W/DetectPable: type=1400 audit(0.0:616460): avc: denied { getattr } for path=\"/proc/kmsg\" dev=\"proc\" ino=4026532126 scontext=u:r:shell:s0 tcontext=u:object_r:proc_kmsg:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:49.571 32254-32254/? W/DetectPable: type=1400 audit(0.0:616461): avc: denied { getattr } for path=\"/proc/misc\" dev=\"proc\" ino=4026531992 scontext=u:r:shell:s0 tcontext=u:object_r:proc_misc:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:49.571 32254-32254/? W/DetectPable: type=1400 audit(0.0:616462): avc: denied { getattr } for path=\"/proc/iomem\" dev=\"proc\" ino=4026532155 scontext=u:r:shell:s0 tcontext=u:object_r:proc_iomem:s0 tclass=file permissive=0\n" +
                "    \n" +
                "    --------- beginning of system\n" +
                "2022-03-09 19:12:49.618 2218-2218/? D/OppoBaseBatteryService: send broadcast : oppo.intent.action.BATTERY_DATA_UPDATE\n" +
                "2022-03-09 19:12:49.619 2218-2218/? W/ContextImpl: Calling a method in the system process without a qualified user: android.app.ContextImpl.sendBroadcast:1087 com.android.server.OppoBaseBatteryService\$2.run:192 android.os.Handler.handleCallback:883 android.os.Handler.dispatchMessage:100 android.os.Looper.loop:227 \n" +
                "2022-03-09 19:12:49.625 2218-2218/? D/OppoPowerMonitor: Receive broadcast android.intent.action.BATTERY_CHANGED\n" +
                "2022-03-09 19:12:49.633 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-09 19:12:49.636 3236-3236/? D/BatteryStatusService: real-ui is abnormal...\n" +
                "2022-03-09 19:12:50.635 32254-32254/? W/DetectPable: type=1400 audit(0.0:616522): avc: denied { getattr } for path=\"/proc/fb\" dev=\"proc\" ino=4026531991 scontext=u:r:shell:s0 tcontext=u:object_r:proc:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:50.635 32254-32254/? W/DetectPable: type=1400 audit(0.0:616523): avc: denied { getattr } for path=\"/proc/keys\" dev=\"proc\" ino=4026532181 scontext=u:r:shell:s0 tcontext=u:object_r:proc_keys:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:50.635 32254-32254/? W/DetectPable: type=1400 audit(0.0:616524): avc: denied { getattr } for path=\"/proc/kmsg\" dev=\"proc\" ino=4026532126 scontext=u:r:shell:s0 tcontext=u:object_r:proc_kmsg:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:50.635 32254-32254/? W/DetectPable: type=1400 audit(0.0:616525): avc: denied { getattr } for path=\"/proc/misc\" dev=\"proc\" ino=4026531992 scontext=u:r:shell:s0 tcontext=u:object_r:proc_misc:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:50.635 32254-32254/? W/DetectPable: type=1400 audit(0.0:616526): avc: denied { getattr } for path=\"/proc/iomem\" dev=\"proc\" ino=4026532155 scontext=u:r:shell:s0 tcontext=u:object_r:proc_iomem:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:51.123 867-867/? D/ThermalHAL-845: Entering get_temperatures\n" +
                "2022-03-09 19:12:51.123 867-867/? D/ThermalHAL-845: Entering get_temperatures\n" +
                "2022-03-09 19:12:51.123 867-867/? D/ThermalHAL-UTIL: Entering get_temperature_for_all\n" +
                "2022-03-09 19:12:51.123 867-867/? D/ThermalHAL-UTIL: Entering read_temperature\n" +
                "2022-03-09 19:12:51.125 867-867/? I/chatty: uid=1000(system) thermal@1.0-ser identical 9 lines\n" +
                "2022-03-09 19:12:51.125 867-867/? D/ThermalHAL-UTIL: Entering read_temperature\n" +
                "2022-03-09 19:12:51.127 867-867/? D/ThermalHAL-845: Entering get_temperatures\n" +
                "2022-03-09 19:12:51.127 867-867/? D/ThermalHAL-845: Entering get_temperatures\n" +
                "2022-03-09 19:12:51.127 867-867/? D/ThermalHAL-UTIL: Entering get_temperature_for_all\n" +
                "2022-03-09 19:12:51.127 867-867/? D/ThermalHAL-UTIL: Entering read_temperature\n" +
                "2022-03-09 19:12:51.128 867-867/? I/chatty: uid=1000(system) thermal@1.0-ser identical 9 lines\n" +
                "2022-03-09 19:12:51.128 867-867/? D/ThermalHAL-UTIL: Entering read_temperature\n" +
                "2022-03-09 19:12:51.695 32254-32254/? W/DetectPable: type=1400 audit(0.0:616586): avc: denied { getattr } for path=\"/proc/fb\" dev=\"proc\" ino=4026531991 scontext=u:r:shell:s0 tcontext=u:object_r:proc:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:51.695 32254-32254/? W/DetectPable: type=1400 audit(0.0:616587): avc: denied { getattr } for path=\"/proc/keys\" dev=\"proc\" ino=4026532181 scontext=u:r:shell:s0 tcontext=u:object_r:proc_keys:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:51.695 32254-32254/? W/DetectPable: type=1400 audit(0.0:616588): avc: denied { getattr } for path=\"/proc/kmsg\" dev=\"proc\" ino=4026532126 scontext=u:r:shell:s0 tcontext=u:object_r:proc_kmsg:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:52.180 1752-1752/? E/TLOC: [TLOCChannel]\"Can't receive message due to timeout\"\n" +
                "2022-03-09 19:12:52.180 1752-1752/? V/LocSvc_api_v02: validateRequest:1254]: reqId = 59\n" +
                "2022-03-09 19:12:52.180 1752-1752/? V/LocSvc_api_v02: validateRequest:1853]: reqId=59, len = 0\n" +
                "2022-03-09 19:12:52.180 1752-1752/? V/LocSvc_api_v02: locClientSendReq:2319] sending reqId= 59, len = 0\n" +
                "2022-03-09 19:12:52.180 1752-1752/? I/LocSvc_api_v02: ---> locClientSendReq line 2325 QMI_LOC_GET_ENGINE_LOCK_REQ_V02\n" +
                "2022-03-09 19:12:52.183 1752-1752/? V/LocSvc_api_v02: locClientSendReq:2336] qmi_client_send_msg_sync returned 0\n" +
                "2022-03-09 19:12:52.184 1752-1752/? V/LocSvc_api_v02: convertQmiResponseToLocStatus:938]: result = 0, error = 0, status = 0\n" +
                "2022-03-09 19:12:52.184 1752-1752/? I/TLOC: [ModemComm  ]\"Request succeeded for gps engine lock\"\n" +
                "2022-03-09 19:12:52.184 1752-1752/? D/TLOC: [TLOCChannel] \"Going to receive message for TLOCD\"\n" +
                "2022-03-09 19:12:52.184 1752-1752/? D/TLOC: [TLOCChannel] \"wait for message for user TLOCD\"\n" +
                "2022-03-09 19:12:52.184 1752-1770/? V/LocSvc_api_v02: locClientIndCb:1052]: Indication: msg_id=59 buf_len=14 pCallbackData = 0x7025240000\n" +
                "2022-03-09 19:12:52.185 1752-1770/? V/LocSvc_api_v02: locClientGetSizeByRespIndId:2521]: resp ind Id 59 size = 32\n" +
                "2022-03-09 19:12:52.185 1752-1770/? V/LocSvc_api_v02: locClientGetSizeAndTypeByIndId:835]: indId 59 is a resp size = 32\n" +
                "2022-03-09 19:12:52.185 1752-1770/? I/TLOC: [Modem      ]\"Sending location turned on status to the daemon\"\n" +
                "2022-03-09 19:12:52.185 1752-1770/? D/TLOC: [TLOCChannel] \"TLOCChannel is already initialized\"\n" +
                "2022-03-09 19:12:52.185 1752-1770/? D/TLOC: [TLOCChannel] \"Going to send message to TLOCD\"\n" +
                "2022-03-09 19:12:52.185 1752-1770/? D/TLOC: [TLOCChannel]\" --- message(id:14, size:0) ---> TLOCD\"\n" +
                "2022-03-09 19:12:52.185 1752-1752/? D/TLOC: [TLOCChannel]\" TLOCD <--- message(id:14, size:0) ---\"\n" +
                "2022-03-09 19:12:52.185 1752-1752/? I/TLOC: [TLOCDaemon ]\"timeout passed - start periodic warm-up\"\n" +
                "2022-03-09 19:12:52.185 1752-1752/? V/LocSvc_api_v02: validateRequest:1254]: reqId = 34\n" +
                "2022-03-09 19:12:52.185 1752-1752/? V/LocSvc_api_v02: validateRequest:1853]: reqId=34, len = 140\n" +
                "2022-03-09 19:12:52.185 1752-1752/? V/LocSvc_api_v02: locClientSendReq:2319] sending reqId= 34, len = 140\n" +
                "2022-03-09 19:12:52.185 1752-1752/? I/LocSvc_api_v02: ---> locClientSendReq line 2325 QMI_LOC_START_REQ_V02\n" +
                "2022-03-09 19:12:52.187 1752-1752/? V/LocSvc_api_v02: locClientSendReq:2336] qmi_client_send_msg_sync returned 0\n" +
                "2022-03-09 19:12:52.187 1752-1752/? V/LocSvc_api_v02: convertQmiResponseToLocStatus:938]: result = 0, error = 0, status = 0\n" +
                "2022-03-09 19:12:52.187 1752-1752/? I/TLOC: [ModemComm  ]\"Start a session successfully\"\n" +
                "2022-03-09 19:12:52.187 1752-1752/? D/TLOC: [TLOCDaemon ]\"The command finished successfully\"\n" +
                "2022-03-09 19:12:52.187 1752-1752/? D/TLOC: [TLOCDaemon ]\"waiting for command\"\n" +
                "2022-03-09 19:12:52.187 1752-1752/? D/TLOC: [TLOCChannel] \"Going to receive message for TLOCD\"\n" +
                "2022-03-09 19:12:52.187 1752-1752/? D/TLOC: [TLOCChannel] \"wait for message for user TLOCD\"\n" +
                "2022-03-09 19:12:52.189 859-2235/? V/LocSvc_api_v02: locClientIndCb:1052]: Indication: msg_id=45 buf_len=16 pCallbackData = 0x7345420c40\n" +
                "2022-03-09 19:12:52.189 859-2235/? V/LocSvc_api_v02: locClientGetSizeByEventIndId:2561]: event ind Id 45 size = 12\n" +
                "2022-03-09 19:12:52.189 859-2235/? V/LocSvc_api_v02: locClientGetSizeAndTypeByIndId:825]: indId 45 is an event size = 12\n" +
                "2022-03-09 19:12:52.189 859-2235/? I/LocSvc_ApiV02: <--- globalEventCb line 170 QMI_LOC_EVENT_WIFI_REQ_IND_V02\n" +
                "2022-03-09 19:12:52.189 859-2235/? V/LocSvc_ApiV02: globalEventCb:175] client = 0x7345420c40, event id = 0x2D, client cookie ptr = 0x7346a41180\n" +
                "2022-03-09 19:12:52.189 859-2235/? V/LocSvc_LBSApiV02: eventCb:58] client = 0x7345420c40, event id = 45, event name = QMI_LOC_EVENT_WIFI_REQ_IND_V02 payload = 0x733dc68a80\n" +
                "2022-03-09 19:12:52.189 859-2235/? D/LocSvc_ApiV02: eventCb:6001] event id = 0x2D\n" +
                "2022-03-09 19:12:52.189 859-2235/? D/LocSvc_ApiV02: eventCb:6098] WIFI Req Ind\n" +
                "2022-03-09 19:12:52.189 859-2235/? V/LocSvc_ApiV02: requestOdcpi:5267] ODCPI Request: requestType 0\n" +
                "2022-03-09 19:12:52.190 859-2235/? D/LocSvc_LocAdapterBase: requestOdcpiEvent: default implementation invoked\n" +
                "2022-03-09 19:12:52.190 859-969/? D/LocSvc_GnssAdapter: requestOdcpi:3974] request: type 0, tbf 1000, isEmergency 0 requestActive: 0 timerActive: 0\n" +
                "2022-03-09 19:12:52.190 859-969/? I/LocSvc_GnssInterface: ===> odcpiRequestCb line 529 \n" +
                "2022-03-09 19:12:52.190 859-969/? D/LocSvc_GnssInterface: odcpiRequestCb:535] gnssRequestLocationCb_2_0 isUserEmergency = 0\n" +
                "2022-03-09 19:12:52.196 2218-2630/? I/GnssLocationProvider: GNSS HAL Requesting location updates from network provider for 10000 millis.\n" +
                "2022-03-09 19:12:52.200 2218-2630/? D/LocationManagerService: request 3af0e86 network Request[POWER_LOW network requested=+1s0ms fastest=+1s0ms] from android(1000 foreground [whitelisted])\n" +
                "2022-03-09 19:12:52.202 4522-27245/? I/TencentNLP: TencentLocationProviderImpl -> onSetRequest -> geolocation.geolocation.nlp receive location request,req:ProviderRequest[ON interval=+1s0ms] ws:WorkSource{1000 android}\n" +
                "2022-03-09 19:12:52.203 4522-4522/? I/TencentNLP: TencentLocationProviderImpl -> requestLocationUpdatesBySdk -> time: 1646824371234\n" +
                "2022-03-09 19:12:52.227 2218-11718/? W/DevicePolicyManager: Package com.tencent.android.location (uid=10652, pid=4522) cannot access Device IDs\n" +
                "2022-03-09 19:12:52.228 3170-3708/? W/TelephonyPermissions: reportAccessDeniedToReadIdentifiers:com.tencent.android.location:getSubscriberId:isPreinstalled=true:isPrivApp=false\n" +
                "2022-03-09 19:12:52.229 4522-6891/? E/TencentNLP: java.lang.SecurityException: getSubscriberId: The user 10652 does not meet the requirements to access device identifiers.\n" +
                "2022-03-09 19:12:52.304 4522-9439/? W/SSLCertificateSocketFactory: Bypassing SSL security checks at caller's request\n" +
                "2022-03-09 19:12:52.360 4522-6891/? I/TencentNLP: TencentLocationProviderImpl -> locationSame -> report location to system\n" +
                "2022-03-09 19:12:52.362 4522-6891/? I/TencentNLP: TencentLocationProviderImpl -> onLocationChanged -> report location to system\n" +
                "2022-03-09 19:12:52.373 859-859/? I/LocSvc_GnssInterface: ===> injectLocation line 319 \n" +
                "2022-03-09 19:12:52.373 859-859/? D/LocSvc_GnssAdapter: injectLocationCommand]: latitude  31.1661 longitude 121.3865 accuracy 220.0000\n" +
                "2022-03-09 19:12:52.375 859-971/? V/LocSvc_ApiV02: operator():1005] Lat=31.166104, Lon=121.386472, Acc=1000.00 rawAcc=220.00 horConfidence=68rawHorConfidence=68 onDemandCpi=0\n" +
                "2022-03-09 19:12:52.375 859-971/? V/LocSvc_api_v02: validateRequest:1254]: reqId = 57\n" +
                "2022-03-09 19:12:52.375 859-971/? V/LocSvc_api_v02: validateRequest:1853]: reqId=57, len = 568\n" +
                "2022-03-09 19:12:52.375 859-971/? V/LocSvc_api_v02: locClientSendReq:2319] sending reqId= 57, len = 568\n" +
                "2022-03-09 19:12:52.375 859-971/? I/LocSvc_api_v02: ---> locClientSendReq line 2325 QMI_LOC_INJECT_POSITION_REQ_V02\n" +
                "2022-03-09 19:12:52.376 859-859/? I/LocSvc_GnssInterface: ===> injectLocation line 319 \n" +
                "2022-03-09 19:12:52.376 859-859/? D/LocSvc_GnssAdapter: injectLocationCommand]: latitude  31.1661 longitude 121.3865 accuracy 220.0000\n" +
                "2022-03-09 19:12:52.377 859-2235/? V/LocSvc_api_v02: locClientIndCb:1052]: Indication: msg_id=57 buf_len=7 pCallbackData = 0x7345420c40\n" +
                "2022-03-09 19:12:52.377 859-2235/? V/LocSvc_api_v02: locClientGetSizeByRespIndId:2521]: resp ind Id 57 size = 4\n" +
                "2022-03-09 19:12:52.377 859-2235/? V/LocSvc_api_v02: locClientGetSizeAndTypeByIndId:835]: indId 57 is a resp size = 4\n" +
                "2022-03-09 19:12:52.377 859-2235/? I/LocSvc_ApiV02: <--- globalRespCb line 196 QMI_LOC_INJECT_POSITION_REQ_V02\n" +
                "2022-03-09 19:12:52.377 859-2235/? V/LocSvc_ApiV02: globalRespCb:201] client = 0x7345420c40, resp id = 57, client cookie ptr = 0x7346a41180\n" +
                "2022-03-09 19:12:52.377 859-971/? V/LocSvc_api_v02: locClientSendReq:2336] qmi_client_send_msg_sync returned 0\n" +
                "2022-03-09 19:12:52.377 859-971/? V/LocSvc_api_v02: convertQmiResponseToLocStatus:938]: result = 0, error = 0, status = 0\n" +
                "2022-03-09 19:12:52.377 859-971/? D/LocSvc_api_v02: loc_free_slot:300]: freeing slot 0\n" +
                "2022-03-09 19:12:52.377 859-971/? V/LocSvc_ApiV02: operator():1005] Lat=31.166104, Lon=121.386472, Acc=1000.00 rawAcc=220.00 horConfidence=68rawHorConfidence=68 onDemandCpi=0\n" +
                "2022-03-09 19:12:52.377 859-971/? V/LocSvc_api_v02: validateRequest:1254]: reqId = 57\n" +
                "2022-03-09 19:12:52.377 859-971/? V/LocSvc_api_v02: validateRequest:1853]: reqId=57, len = 568\n" +
                "2022-03-09 19:12:52.377 859-971/? V/LocSvc_api_v02: locClientSendReq:2319] sending reqId= 57, len = 568\n" +
                "2022-03-09 19:12:52.377 859-971/? I/LocSvc_api_v02: ---> locClientSendReq line 2325 QMI_LOC_INJECT_POSITION_REQ_V02\n" +
                "2022-03-09 19:12:52.378 859-2235/? V/LocSvc_api_v02: locClientIndCb:1052]: Indication: msg_id=57 buf_len=7 pCallbackData = 0x7345420c40\n" +
                "2022-03-09 19:12:52.378 859-2235/? V/LocSvc_api_v02: locClientGetSizeByRespIndId:2521]: resp ind Id 57 size = 4\n" +
                "2022-03-09 19:12:52.378 859-2235/? V/LocSvc_api_v02: locClientGetSizeAndTypeByIndId:835]: indId 57 is a resp size = 4\n" +
                "2022-03-09 19:12:52.378 859-971/? V/LocSvc_api_v02: locClientSendReq:2336] qmi_client_send_msg_sync returned 0\n" +
                "2022-03-09 19:12:52.378 859-2235/? I/LocSvc_ApiV02: <--- globalRespCb line 196 QMI_LOC_INJECT_POSITION_REQ_V02\n" +
                "2022-03-09 19:12:52.378 859-2235/? V/LocSvc_ApiV02: globalRespCb:201] client = 0x7345420c40, resp id = 57, client cookie ptr = 0x7346a41180\n" +
                "2022-03-09 19:12:52.378 859-971/? V/LocSvc_api_v02: convertQmiResponseToLocStatus:938]: result = 0, error = 0, status = 0\n" +
                "2022-03-09 19:12:52.378 859-971/? D/LocSvc_api_v02: loc_free_slot:300]: freeing slot 0\n" +
                "2022-03-09 19:12:52.392 19742-19768/? I/PAS41b8df5220104.ScheduleTaskManager: addAlarmTask task size=7\n" +
                "2022-03-09 19:12:52.428 19742-19768/? I/PAS41b8df5220104.UserProfileDao: WeakReference have info\n" +
                "2022-03-09 19:12:52.430 19742-19768/? I/PAS41b8df5220104.PhoneStateInfoCollector: checkDistance, hc'd =7978.452393728141\n" +
                "2022-03-09 19:12:52.431 19742-19768/? I/PAS41b8df5220104.PhoneStateInfoCollector: checkDistance, hc'd =130.82387289579077\n" +
                "2022-03-09 19:12:52.527 19742-19768/? I/PAS41b8df5220104.ColorNetworkUtil: isNetworkConnected, network is available\n" +
                "2022-03-09 19:12:52.528 19742-9440/? I/PAS41b8df5220104.ResourceTrigger: resourceOnChange, resourceId is 10009\n" +
                "2022-03-09 19:12:52.528 19742-9440/? I/PAS41b8df5220104.ResourceTrigger: startAlign\n" +
                "2022-03-09 19:12:52.659 884-937/? E/ANDR-PERF-RESOURCEQS: Failed to reset optimization [3, 0]\n" +
                "2022-03-09 19:12:52.763 32254-32254/? W/DetectPable: type=1400 audit(0.0:616650): avc: denied { getattr } for path=\"/proc/fb\" dev=\"proc\" ino=4026531991 scontext=u:r:shell:s0 tcontext=u:object_r:proc:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:52.763 32254-32254/? W/DetectPable: type=1400 audit(0.0:616651): avc: denied { getattr } for path=\"/proc/keys\" dev=\"proc\" ino=4026532181 scontext=u:r:shell:s0 tcontext=u:object_r:proc_keys:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:52.763 32254-32254/? W/DetectPable: type=1400 audit(0.0:616652): avc: denied { getattr } for path=\"/proc/kmsg\" dev=\"proc\" ino=4026532126 scontext=u:r:shell:s0 tcontext=u:object_r:proc_kmsg:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:52.763 32254-32254/? W/DetectPable: type=1400 audit(0.0:616653): avc: denied { getattr } for path=\"/proc/misc\" dev=\"proc\" ino=4026531992 scontext=u:r:shell:s0 tcontext=u:object_r:proc_misc:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:52.763 32254-32254/? W/DetectPable: type=1400 audit(0.0:616654): avc: denied { getattr } for path=\"/proc/iomem\" dev=\"proc\" ino=4026532155 scontext=u:r:shell:s0 tcontext=u:object_r:proc_iomem:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:53.600 16847-24616/? D/MCS2.0_CIPHER_ALGO: riv native_AES_decrypt flag 0\n" +
                "2022-03-09 19:12:53.712 16847-16859/? W/com.heytap.mcs: Reducing the number of considered missed Gc histogram windows from 556 to 100\n" +
                "2022-03-09 19:12:53.827 32254-32254/? W/DetectPable: type=1400 audit(0.0:616714): avc: denied { getattr } for path=\"/proc/fb\" dev=\"proc\" ino=4026531991 scontext=u:r:shell:s0 tcontext=u:object_r:proc:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:53.827 32254-32254/? W/DetectPable: type=1400 audit(0.0:616715): avc: denied { getattr } for path=\"/proc/keys\" dev=\"proc\" ino=4026532181 scontext=u:r:shell:s0 tcontext=u:object_r:proc_keys:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:53.827 32254-32254/? W/DetectPable: type=1400 audit(0.0:616716): avc: denied { getattr } for path=\"/proc/kmsg\" dev=\"proc\" ino=4026532126 scontext=u:r:shell:s0 tcontext=u:object_r:proc_kmsg:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:53.827 32254-32254/? W/DetectPable: type=1400 audit(0.0:616717): avc: denied { getattr } for path=\"/proc/misc\" dev=\"proc\" ino=4026531992 scontext=u:r:shell:s0 tcontext=u:object_r:proc_misc:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:53.827 32254-32254/? W/DetectPable: type=1400 audit(0.0:616718): avc: denied { getattr } for path=\"/proc/iomem\" dev=\"proc\" ino=4026532155 scontext=u:r:shell:s0 tcontext=u:object_r:proc_iomem:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:53.839 2218-11702/? E/OppoNotificationManager: Notification--call isOpush error: Attempt to invoke virtual method 'java.lang.Class java.lang.Object.getClass()' on a null object reference--Notification(channel=weibo_news_push_channel pri=0 contentView=null vibrate=null sound=null tick defaults=0x0 flags=0x18 color=0x00000000 vis=PUBLIC)\n" +
                "2022-03-09 19:12:53.974 2218-2218/? D/AS.AudioService: getStreamVolume: mIsMuted == true\n" +
                "2022-03-09 19:12:53.974 2218-2218/? D/AudioManager: getStreamVolume packageName=android, index=0, streamType=5\n" +
                "2022-03-09 19:12:54.026 17021-17103/? W/DCS: Record event failed. DCS reject event: [appId=20082,logTag=20082,eventId=notice_add_mcs_notification] . Pls check server config.\n" +
                "2022-03-09 19:12:54.090 17421-17421/? W/HeadsUpManagerPhone: updateRegionForNotch cutout is null return\n" +
                "2022-03-09 19:12:54.506 2218-9443/? W/AppOps: Bad call: specified package android under uid 10780 but it is really 1000\n" +
                "    java.lang.RuntimeException: here\n" +
                "        at com.android.server.appop.AppOpsService.getOpsRawLocked(AppOpsService.java:2647)\n" +
                "        at com.android.server.appop.AppOpsService.startOperation(AppOpsService.java:2247)\n" +
                "        at android.app.AppOpsManager.startOpNoThrow(AppOpsManager.java:5431)\n" +
                "        at android.app.AppOpsManager.startOpNoThrow(AppOpsManager.java:5412)\n" +
                "        at com.android.server.VibratorService.getAppOpMode(VibratorService.java:1073)\n" +
                "        at com.android.server.VibratorService.startVibrationLocked(VibratorService.java:857)\n" +
                "        at com.android.server.VibratorService.vibrate(VibratorService.java:731)\n" +
                "        at android.os.SystemVibrator.vibrate(SystemVibrator.java:98)\n" +
                "        at java.lang.Thread.run(Thread.java:919)\n" +
                "2022-03-09 19:12:54.506 2218-9443/? W/VibratorService: Would be an error: vibrate from uid 10780\n" +
                "2022-03-09 19:12:54.859 32254-32254/? W/DetectPable: type=1400 audit(0.0:616778): avc: denied { getattr } for path=\"/proc/fb\" dev=\"proc\" ino=4026531991 scontext=u:r:shell:s0 tcontext=u:object_r:proc:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:54.859 32254-32254/? W/DetectPable: type=1400 audit(0.0:616779): avc: denied { getattr } for path=\"/proc/keys\" dev=\"proc\" ino=4026532181 scontext=u:r:shell:s0 tcontext=u:object_r:proc_keys:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:54.859 32254-32254/? W/DetectPable: type=1400 audit(0.0:616780): avc: denied { getattr } for path=\"/proc/kmsg\" dev=\"proc\" ino=4026532126 scontext=u:r:shell:s0 tcontext=u:object_r:proc_kmsg:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:54.859 32254-32254/? W/DetectPable: type=1400 audit(0.0:616781): avc: denied { getattr } for path=\"/proc/misc\" dev=\"proc\" ino=4026531992 scontext=u:r:shell:s0 tcontext=u:object_r:proc_misc:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:54.859 32254-32254/? W/DetectPable: type=1400 audit(0.0:616782): avc: denied { getattr } for path=\"/proc/iomem\" dev=\"proc\" ino=4026532155 scontext=u:r:shell:s0 tcontext=u:object_r:proc_iomem:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:55.602 2218-2218/? D/OppoPowerMonitor: Receive broadcast android.intent.action.BATTERY_CHANGED\n" +
                "2022-03-09 19:12:55.609 3236-3236/? E/BatteryStatusService: readIntFromFile io exception:/sys/class/power_supply/battery/chip_soc: open failed: ENOENT (No such file or directory)\n" +
                "2022-03-09 19:12:55.611 3236-3236/? D/BatteryStatusService: real-ui is abnormal...\n" +
                "2022-03-09 19:12:55.919 32254-32254/? W/DetectPable: type=1400 audit(0.0:616842): avc: denied { getattr } for path=\"/proc/fb\" dev=\"proc\" ino=4026531991 scontext=u:r:shell:s0 tcontext=u:object_r:proc:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:56.045 867-867/? D/ThermalHAL-845: Entering get_temperatures\n" +
                "2022-03-09 19:12:56.045 867-867/? D/ThermalHAL-845: Entering get_temperatures\n" +
                "2022-03-09 19:12:56.045 867-867/? D/ThermalHAL-UTIL: Entering get_temperature_for_all\n" +
                "2022-03-09 19:12:56.045 867-867/? D/ThermalHAL-UTIL: Entering read_temperature\n" +
                "2022-03-09 19:12:56.047 867-867/? I/chatty: uid=1000(system) thermal@1.0-ser identical 9 lines\n" +
                "2022-03-09 19:12:56.047 867-867/? D/ThermalHAL-UTIL: Entering read_temperature\n" +
                "2022-03-09 19:12:56.049 867-867/? D/ThermalHAL-845: Entering get_temperatures\n" +
                "2022-03-09 19:12:56.049 867-867/? D/ThermalHAL-845: Entering get_temperatures\n" +
                "2022-03-09 19:12:56.050 867-867/? D/ThermalHAL-UTIL: Entering get_temperature_for_all\n" +
                "2022-03-09 19:12:56.050 867-867/? D/ThermalHAL-UTIL: Entering read_temperature\n" +
                "2022-03-09 19:12:56.051 867-867/? I/chatty: uid=1000(system) thermal@1.0-ser identical 9 lines\n" +
                "2022-03-09 19:12:56.051 867-867/? D/ThermalHAL-UTIL: Entering read_temperature\n" +
                "2022-03-09 19:12:56.053 867-867/? D/ThermalHAL-845: Entering get_temperatures\n" +
                "2022-03-09 19:12:56.053 867-867/? D/ThermalHAL-845: Entering get_temperatures\n" +
                "2022-03-09 19:12:56.053 867-867/? D/ThermalHAL-UTIL: Entering get_temperature_for_all\n" +
                "2022-03-09 19:12:56.053 867-867/? D/ThermalHAL-UTIL: Entering read_temperature\n" +
                "2022-03-09 19:12:56.054 867-867/? I/chatty: uid=1000(system) thermal@1.0-ser identical 9 lines\n" +
                "2022-03-09 19:12:56.054 867-867/? D/ThermalHAL-UTIL: Entering read_temperature\n" +
                "2022-03-09 19:12:56.979 32254-32254/? W/DetectPable: type=1400 audit(0.0:616906): avc: denied { getattr } for path=\"/proc/fb\" dev=\"proc\" ino=4026531991 scontext=u:r:shell:s0 tcontext=u:object_r:proc:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:56.979 32254-32254/? W/DetectPable: type=1400 audit(0.0:616907): avc: denied { getattr } for path=\"/proc/keys\" dev=\"proc\" ino=4026532181 scontext=u:r:shell:s0 tcontext=u:object_r:proc_keys:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:56.979 32254-32254/? W/DetectPable: type=1400 audit(0.0:616908): avc: denied { getattr } for path=\"/proc/kmsg\" dev=\"proc\" ino=4026532126 scontext=u:r:shell:s0 tcontext=u:object_r:proc_kmsg:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:56.979 32254-32254/? W/DetectPable: type=1400 audit(0.0:616909): avc: denied { getattr } for path=\"/proc/misc\" dev=\"proc\" ino=4026531992 scontext=u:r:shell:s0 tcontext=u:object_r:proc_misc:s0 tclass=file permissive=0\n" +
                "2022-03-09 19:12:56.979 32254-32254/? W/DetectPable: type=1400 audit(0.0:616910): avc: denied { getattr } for path=\"/proc/iomem\" dev=\"proc\" ino=4026532155 scontext=u:r:shell:s0 tcontext=u:object_r:proc_iomem:s0 tclass=file permissive=0"

    var str3 = "tangtang dkfdlkfjlds "

    val str5 =
        "                \"        at android.app.ActivityThread.installProvider(ActivityThread.java:7446)\\n\" +\n" +
                "                \"        at android.app.ActivityThread.installContentProviders(ActivityThread.java:6952)\\n\" +\n" +
                "                \"        at android.app.ActivityThread.handleBindApplication(ActivityThread.java:6847)\\n\" +\n" +
                "                \"        at android.app.ActivityThread.access\\\$1800(ActivityThread.java:243)\\n\" +"
    val strs = listOf(str1, str2, str3, str4, str5)

    private lateinit var sink :BufferedSink

    private val mainScope = MainScope()

    private val writer by lazy {
        File(this.filesDir.absolutePath).writer().buffered()
    }

    private fun getFileName() = "${this.filesDir.absolutePath}${File.separator}${getToday()}.log"
    @SuppressLint("SimpleDateFormat")
    private fun getToday(): String =
        SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        sink = File(getFileName()).sink(true).buffer()
//        EasyLog.addInterceptor(FileWriterLogInterceptor.getInstance(this.filesDir.absolutePath))
        EasyLog.addInterceptor(OkioLogInterceptor.getInstance(this.filesDir.absolutePath))


        MainScope().launch(Dispatchers.Default) {
            repeat(10_000) {
                EasyLog.v(str4 + "$it","ttaylor00")
                EasyLog.v(str3 + "$it","ttaylor00")
            }
            EasyLog.v("work done")
            Log.v("ttaylor1", "onCreate() work done ")
        }

        /**
         * foreach case : return like break, return@forEach like continue
         */
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 2) return
            Log.v("ttaylor", "onCreate() foreach =$it")
        }
    }
}