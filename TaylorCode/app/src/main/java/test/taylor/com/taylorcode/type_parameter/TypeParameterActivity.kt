package test.taylor.com.taylorcode.type_parameter

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class TypeParameterActivity:AppCompatActivity() {
    private val typeb = TypeB<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        typeb.javaClass.typeParameters.forEach {
            it.bounds.forEach {
                Log.v("ttaylor","tag=bounds,  type=${it.javaClass}, ")
            }
            Log.v("ttaylor","tag=generic, ${it.genericDeclaration}")
            Log.v("ttaylor","tag=typename, ${it.name}  javaclass=${it.javaClass}")
        }
    }
}