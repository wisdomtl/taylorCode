package test.taylor.com.taylorcode.photo.okhttp.model_loader

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import test.taylor.com.taylorcode.photo.okhttp.model_loader.track.TrackCallFactory
import test.taylor.com.taylorcode.photo.okhttp.model_loader.track.TrackEventListenerFactory
import test.taylor.com.taylorcode.photo.okhttp.model_loader.track.TrackInterceptor

val okHttpClient by lazy {
    val dispatcher = Dispatcher().apply { maxRequestsPerHost = 5 }
    val client = OkHttpClient.Builder()
        .dispatcher(dispatcher)
        .addInterceptor(TrackInterceptor())
        .eventListenerFactory(TrackEventListenerFactory)
        .build()
    TrackCallFactory.getInstance(client)
}
val okHttpClientBuilder by lazy {
    val dispatcher = Dispatcher().apply { maxRequestsPerHost = 1 }
    OkHttpClient.Builder()
        .dispatcher(dispatcher)
        .addInterceptor(TrackInterceptor())
        .eventListenerFactory(TrackEventListenerFactory)
}