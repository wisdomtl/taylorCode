package test.taylor.com.taylorcode.photo.okhttp.model_loader

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader.LoadData
import kotlin.jvm.JvmOverloads
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import okhttp3.OkHttpClient
import kotlin.jvm.Volatile
import okhttp3.logging.HttpLoggingInterceptor
import java.io.InputStream

/** A simple model loader for fetching media over http/https using OkHttp.  */
class OkHttpUrlLoader     // Public API.
    (private val client: okhttp3.Call.Factory) : ModelLoader<GlideUrl, InputStream> {
    override fun handles(url: GlideUrl): Boolean {
        return true
    }

    override fun buildLoadData(
        model: GlideUrl, width: Int, height: Int, options: Options
    ): LoadData<InputStream>? {
        return LoadData(model, OkHttpStreamFetcher(client, model))
    }

    /** The default factory for [OkHttpUrlLoader]s.  */ // Public API.
    class Factory
    /** Constructor for a new Factory that runs requests using a static singleton client.  */ @JvmOverloads constructor(
        private val client: okhttp3.Call.Factory = internalClient!!
    ) : ModelLoaderFactory<GlideUrl, InputStream> {
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<GlideUrl, InputStream> {
            return OkHttpUrlLoader(client)
        }

        override fun teardown() {
            // Do nothing, this instance doesn't own the client.
        }

        companion object {
            @Volatile
            private var internalClient: okhttp3.Call.Factory? = null
                private get() {
                    if (field == null) {
                        synchronized(Factory::class.java) {
                            if (field == null) {
                                field = OkHttpClient.Builder()
                                    .addInterceptor(HttpLoggingInterceptor()).build()
                            }
                        }
                    }
                    return field
                }
        }
        /**
         * Constructor for a new Factory that runs requests using given client.
         *
         * @param client this is typically an instance of `OkHttpClient`.
         */
    }
}