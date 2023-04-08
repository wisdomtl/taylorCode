package test.taylor.com.taylorcode.util

import android.net.TrafficStats
import kotlinx.coroutines.delay

object TrafficSniffer {
    suspend fun getDownloadSpeed(): Long {
        val bytes1 = TrafficStats.getTotalRxBytes()
        val timestamp1 = System.currentTimeMillis()
        delay(3000)
        val bytes2 = TrafficStats.getTotalRxBytes()
        val timestamp2 = System.currentTimeMillis()
        return (bytes2 - bytes1)*8 * 1000 / (timestamp2 - timestamp1)
    }

    const val Mbps_3G = 4 * 1024 * 1024
    const val Mbps_4G = 16 * 1024 * 1024
    const val Mbps_5G = 100 * 1024 * 1024

}