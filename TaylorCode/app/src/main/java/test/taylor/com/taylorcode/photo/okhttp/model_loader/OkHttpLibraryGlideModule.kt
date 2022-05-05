package test.taylor.com.taylorcode.photo.okhttp.model_loader

import android.content.Context
import com.bumptech.glide.module.LibraryGlideModule
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import java.io.InputStream

/**
 * Registers OkHttp related classes via Glide's annotation processor.
 *
 *
 * For Applications that depend on this library and include an [AppGlideModule] and Glide's
 * annotation processor, this class will be automatically included.
 */
@GlideModule
class OkHttpLibraryGlideModule : LibraryGlideModule() {
    override fun registerComponents(
        context: Context, glide: Glide, registry: Registry
    ) {
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())
    }
}