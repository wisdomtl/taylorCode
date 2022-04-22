package test.taylor.com.taylorcode.annotations

import java.lang.annotation.RetentionPolicy

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindView(val value: Int)
