package test.taylor.com.taylorcode.hook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import javassist.ClassPool
import javassist.CtClass
import javassist.CtField
import javassist.Modifier
import test.taylor.com.taylorcode.R


class JavassistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2)
//         ClassPool.getDefault().makeClass("test.taylor.com.taylorcode.hook.MakeClass").writeFile()
        val pool = ClassPool.getDefault()
        val cc = pool["java.lang.String"]
        val f = CtField(CtClass.intType, "hiddenValue", cc)
        f.modifiers = Modifier.PUBLIC
        cc.addField(f)
        cc.writeFile(".")
    }

}