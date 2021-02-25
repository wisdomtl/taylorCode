package test.taylor.com.taylorcode.ui.custom_view.clip_path

import android.graphics.*
import androidx.core.graphics.applyCanvas
import test.taylor.com.taylorcode.kotlin.dp


/**
 * crop [Bitmap] to round [Bitmap]. if [radius] is big enough, it will become a circle
 */
fun Bitmap.crop(radius: Float): Bitmap =
    Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888).applyCanvas {
        drawRoundRect(
            RectF(0f, 0f, this@crop.width.toFloat(), this@crop.height.toFloat()),
            radius.dp,
            radius.dp,
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                shader = BitmapShader(this@crop, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            }
        )
        setBitmap(null)
    }