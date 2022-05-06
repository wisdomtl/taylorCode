package test.taylor.com.taylorcode.photo.okhttp.model_loader.track

import test.taylor.com.taylorcode.photo.okhttp.model_loader.track.TrackEventListener.Companion.KEY_METHOD
import test.taylor.com.taylorcode.photo.okhttp.model_loader.track.TrackEventListener.Companion.KEY_REQUEST_URL
import test.taylor.com.taylorcode.photo.okhttp.model_loader.track.TrackEventListener.Companion.KEY_RESPONSE_PROTOCOL
import test.taylor.com.taylorcode.photo.okhttp.model_loader.track.TrackEventListener.Companion.KEY_STATUS_CODE
import okhttp3.Interceptor
import okhttp3.Response
import test.taylor.com.taylorcode.photo.okhttp.model_loader.track.TrackEventListener.Companion.KEY_CALL_ID

/**
 * An [Interceptor] to get network tracking data when response is returned
 */
class TrackInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val callId = chain.request().tag() as? Long

        callId?.let {
//            TrackEventListener.put(it, KEY_STATUS_CODE, response.code)
            TrackEventListener.put(it, KEY_RESPONSE_PROTOCOL, response.protocol)
//            TrackEventListener.put(it, KEY_REQUEST_URL, response.request.url)
//            TrackEventListener.put(it, KEY_METHOD, response.request.method)
        }
        return response
    }
}