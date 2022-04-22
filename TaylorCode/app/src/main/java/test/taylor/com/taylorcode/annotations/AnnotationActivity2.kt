package test.taylor.com.taylorcode.annotations

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.R

class AnnotationActivity2 : AppCompatActivity() {

    @BindView(R.id.tvvv)
    private val tv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.factory_view)
        getAnnotation()
        tv?.post {
            tv?.setText("dddddd")
        }
    }

    private fun getAnnotation() {
        val fields = this::class.java.declaredFields

        fields.forEach { field ->
            if (field.annotations != null) {
                if (field.isAnnotationPresent(BindView::class.java)) {
                    field.isAccessible = true
                    val bindView = field.getAnnotation(BindView::class.java)
                    bindView?.let {
                        field.set(this, findViewById(it.value))
                    }
                }
            }
        }
    }
}