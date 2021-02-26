package test.taylor.com.taylorcode.ui.custom_view.clip_path

import android.graphics.*
import androidx.core.graphics.applyCanvas
import test.taylor.com.taylorcode.kotlin.dp


/**
 * crop [Bitmap] to round [Bitmap]. if [radius] is big enough, it becomes a circle.
 * implement by [Shader]
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

/**
 * crop [Bitmap] to round [Bitmap], you could specify which corner to be round
 * implement by [Path]
 */
fun Bitmap.crop2(corners: Corners): Bitmap =
    Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888).applyCanvas {
        val path = Path().apply {
            addRoundRect(
                RectF(0f, 0f, this@crop2.width.toFloat(), this@crop2.height.toFloat()),
                corners.radii,
                Path.Direction.CCW
            )
        }
        clipPath(path)
        drawBitmap(this@crop2, 0f, 0f, Paint(Paint.ANTI_ALIAS_FLAG))
        setBitmap(null)
    }

class Corners(
    var leftTopRx: Float = 0f,
    var leftTopRy: Float = 0f,
    var leftBottomRx: Float = 0f,
    var LeftBottomRy: Float = 0f,
    var rightTopRx: Float = 0f,
    var rightTopRy: Float = 0f,
    var rightBottomRx: Float = 0f,
    var rightBottomRy: Float = 0f
) {
    val radii: FloatArray
        get() = floatArrayOf(leftTopRx, leftTopRy, rightTopRx, rightTopRy, rightBottomRx, rightBottomRy, leftBottomRx, LeftBottomRy)
}
