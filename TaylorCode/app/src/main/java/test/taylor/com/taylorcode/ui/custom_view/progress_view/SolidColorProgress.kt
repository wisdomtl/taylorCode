package test.taylor.com.taylorcode.ui.custom_view.progress_view

import android.graphics.*

class SolidColorProgress(var solidColor: String) : Progress {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor(solidColor)
        style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas?, progressBar: ProgressBar) {
        progressBar.run {
            canvas?.drawRoundRect(progressRectF, progressRx, progressRy, paint)
        }
    }
}