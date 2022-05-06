package test.taylor.com.taylorcode.photo.okhttp.model_loader.track

import okhttp3.Call
import okhttp3.Request
import java.util.concurrent.atomic.AtomicLong

/**
 * A wrapper for [okhttp3.OkHttpClient] to make every single [Call] have an unique id,
 * which will be used in network tracking data
 */
class TrackCallFactory private constructor(private val factory: Call.Factory) : Call.Factory {

    private val callId = AtomicLong(1L)

    companion object {
        @Volatile
        private var INSTANCE: TrackCallFactory? = null
        fun getInstance(factory: Call.Factory) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TrackCallFactory(factory).apply { INSTANCE = this }
            }
    }

    override fun newCall(request: Request): Call {
        val id = callId.getAndIncrement()
        /**
         * keep unique id in tag, it will be read in [TrackEventListenerFactory]
         */
        val newRequest = request.newBuilder().tag(id).build()
        return factory.newCall(newRequest)
    }
}