package test.taylor.com.taylorcode.type_parameter

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.ParameterizedType

class TypeParameterActivity:AppCompatActivity() {
    private val typeb = TypeB<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // case: a way to read type parameter info(member)
        typeb.javaClass.kotlin.typeParameters.forEach {
            Log.e("ttaylor","tag=kt type param, name=${it.name} ,,variance = ${it.variance}")
        }
        // case: read type parameter of class
        (typeb.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.forEach {
            val chars = String().javaClass.toString()
            Log.v("ttaylor","tag=type, java class = ${it.toString()}  ")
        }
    }
}
