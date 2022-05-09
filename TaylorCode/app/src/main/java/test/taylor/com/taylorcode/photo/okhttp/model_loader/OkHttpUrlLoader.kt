package test.taylor.com.taylorcode.photo.okhttp.model_loader

import android.util.Log
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader.LoadData
import kotlin.jvm.JvmOverloads
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import okhttp3.Call
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import kotlin.jvm.Volatile
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.LoggingEventListener
import test.taylor.com.taylorcode.photo.okhttp.model_loader.track.TrackCallFactory
import test.taylor.com.taylorcode.photo.okhttp.model_loader.track.TrackEventListenerFactory
import test.taylor.com.taylorcode.photo.okhttp.model_loader.track.TrackInterceptor
import java.io.InputStream
import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor

/** A simple model loader for fetching media over http/https using OkHttp.  */
class OkHttpUrlLoader(
    private val client: OkHttpClient,
    private val callFactory: Call.Factory
) : ModelLoader<GlideUrl, InputStream> {
    override fun handles(url: GlideUrl): Boolean {
        return true
    }

    override fun buildLoadData(
        model: GlideUrl, width: Int, height: Int, options: Options
    ): LoadData<InputStream>? {
        return LoadData(model, OkHttpStreamFetcher(client, callFactory, model))
    }

    class Factory @JvmOverloads constructor(
        private val client: OkHttpClient,
        private val callFactory: Call.Factory
    ) : ModelLoaderFactory<GlideUrl, InputStream> {
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<GlideUrl, InputStream> {
            return OkHttpUrlLoader(client, callFactory)
        }

        override fun teardown() {
            // Do nothing, this instance doesn't own the client.
        }
    }
}

class MyLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Log.v("ttaylor", "[glide-performance] $message")
    }
}