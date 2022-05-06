package test.taylor.com.taylorcode.photo.okhttp.model_loader.track

import android.os.Build
import okhttp3.Call
import okhttp3.EventListener
import okhttp3.Handshake
import okhttp3.Protocol
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * An [EventListener] for a single [Call], which identify self by a [callId]
 */
class TrackEventListener(private val callId: Long?) : EventListener() {

    private var callStartMillis: Long? = null
    private var dnsStartMillis: Long? = null
    private var tcpConnectStartMillis: Long? = null
    private var tlsConnectStartMillis: Long? = null
    private var callDuration = 0L
    private var dnsDuration = 0L
    private var tcpDuration = 0L
    private var tlsDuration = 0L

    companion object {
        /**
         * the key of tracking data (https://q9jvw0u5f5.feishu.cn/wiki/wikcnoBn98DJDuXGz10f3rePnGc)
         */
        const val KEY_IP = "ip"
        const val KEY_NETWORK = "network"
        const val KEY_LOCATION_DESC = "location_desc"
        const val KEY_UKI_ID = "ukiid"
        const val KEY_LONGITUDE = "longitude"
        const val KEY_LATITUDE = "latitude"
        const val KEY_REQUEST_TOTAL_TIME = "req_total_time"
        const val KEY_REQUEST_URL = "req_url"
        const val KEY_REQUEST_PROTOCOL = "request_protocol"
        const val KEY_RESPONSE_PROTOCOL = "response_protocol"
        const val KEY_CALL_ID = "call_id"
        const val KEY_NEW_CONNECTION = "new_connection"
        const val KEY_DNS_TIME = "dns_time"
        const val KEY_TCP_TIME = "tcp_time"
        const val KEY_SSL_TIME = "ssl_time"
        const val KEY_METHOD = "method"
        const val KEY_STATUS_CODE = "status_code"

        var networkTrackCallback: NetworkTrackCallback? = null

        /**
         * A tracking data container for all [Call]s
         * first value of Triple is call id,
         * second value of Triple is key,
         * third value of Triple is the value
         * one single call always has multiple items in [trackers] with the same [callId]
         * [trackers] is shared between different [Call]s
         */
        private val trackers = ConcurrentLinkedQueue<Triple<Long, String, Any>>()

        fun put(callId: Long, key: String, value: Any) {
            trackers.add(Triple(callId, key, value))
        }

        /**
         * turn all the tracking data which has the same callId into a map
         */
        fun get(callId: Long): Map<String, Any> =
            trackers.filter { it.first == callId }
                .map { it.second to it.third }
                .let { mapOf(*it.toTypedArray()) }

        /**
         * remove the tracking data when call is end
         */
        fun removeAll(callId: Long) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                trackers.removeIf { it.first == callId }
            } else {
                synchronized(trackers) {
                    trackers.removeAll { it.first == callId }
                }
            }
        }

    }

    override fun callStart(call: Call) {
        callId?.let {
            callStartMillis = System.currentTimeMillis()
            put(it, KEY_CALL_ID, it)
            put(it, KEY_NEW_CONNECTION, false)
        }
    }

    override fun callEnd(call: Call) {
        callId?.let {
            callStartMillis = callStartMillis ?: System.currentTimeMillis()
            callDuration = System.currentTimeMillis() - callStartMillis!!
            put(callId, KEY_REQUEST_TOTAL_TIME, callDuration)
            networkTrackCallback?.onCallEnd(get(callId))
            removeAll(callId)
        }
    }

    override fun callFailed(call: Call, ioe: IOException) {
        callId?.let {
            callStartMillis = callStartMillis ?: System.currentTimeMillis()
            callDuration = System.currentTimeMillis() - callStartMillis!!
            put(callId, KEY_REQUEST_TOTAL_TIME, callDuration)
            networkTrackCallback?.onCallEnd(get(callId))
            removeAll(callId)
        }
    }

    override fun dnsStart(call: Call, domainName: String) {
        callId?.let {
            dnsStartMillis = System.currentTimeMillis()
            put(callId, KEY_DNS_TIME, 0)
        }
    }

    override fun dnsEnd(call: Call, domainName: String, inetAddressList: List<InetAddress>) {
        callId?.let {
            dnsStartMillis = dnsStartMillis ?: System.currentTimeMillis()
            dnsDuration = System.currentTimeMillis() - dnsStartMillis!!
            put(callId, KEY_DNS_TIME, dnsDuration)
        }
    }

    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        callId?.let {
            tcpConnectStartMillis = System.currentTimeMillis()
            put(it, KEY_NEW_CONNECTION, true)
        }
    }

    override fun secureConnectStart(call: Call) {
        callId?.let {
            tlsConnectStartMillis = tlsConnectStartMillis ?: System.currentTimeMillis()
            tcpDuration = System.currentTimeMillis() - tcpConnectStartMillis!!
            put(callId, KEY_TCP_TIME, tcpDuration)
        }
    }

    override fun secureConnectEnd(call: Call, handshake: Handshake?) {
        callId?.let {
            tlsDuration = System.currentTimeMillis() - tlsConnectStartMillis!!
            put(callId, KEY_SSL_TIME, tlsDuration)
        }
    }

    override fun connectFailed(
        call: Call,
        inetSocketAddress: InetSocketAddress,
        proxy: Proxy,
        protocol: Protocol?,
        ioe: IOException
    ) {
        callId?.let {
            tcpDuration = System.currentTimeMillis() - tcpConnectStartMillis!!
            networkTrackCallback?.onCallEnd(get(callId))
            removeAll(callId)
        }
    }
}

/**
 * A factory describe how to create [EventListener] instance.
 * We create a new [EventListener] for every [Call] to avoid tracking data disorder when call is executing in concurrency
 */
object TrackEventListenerFactory : EventListener.Factory {
    override fun create(call: Call): EventListener {
        val callId = call.request().tag() as? Long
        return TrackEventListener(callId)
    }
}
