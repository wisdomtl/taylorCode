package test.taylor.com.taylorcode.kotlin.invoke

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlin.math.pow

class InvokeActivity : AppCompatActivity() {
    private val man = listOf(
            Human(age = 30, annualSalary = 40, nativePlace = "山东", married = false, height = 170, weight = 80, gender = "male"),
            Human(age = 22, annualSalary = 23, nativePlace = "浙江", married = true, height = 189, weight = 90, gender = "male"),
            Human(age = 29, annualSalary = 10, nativePlace = "上海", married = false, height = 181, weight = 70, gender = "male"),
            Human(age = 25, annualSalary = 70, nativePlace = "江苏", married = false, height = 167, weight = 66, gender = "male"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val women = Human(22, 10, "上海", false, 164, 50, "female")
        filterManByLambda(man, women) { man, women -> !man.married && man.age in women.age..30 && man.annualSalary >= 10 && isLocal(women, man) && bmi(man) in 20..25 || !man.married && !isLocal(women, man) && man.age in women.age..40 && man.annualSalary >= 40 }
        filterManByInvoke(man,HandsomeOrSuccessfulPredicate(women))
    }

    private fun filterManByLambda(man: List<Human>, women: Human, predicate: (Human, Human) -> Boolean) {
        man.filter { predicate(it, women) }.forEach {
            Log.v("ttaylor", "man = $it")
        }
    }

    private fun filterManByInvoke(man: List<Human>, predicate: (Human) -> Boolean) {
        man.filter (predicate).forEach {
            Log.e("ttaylor","man = $it")
        }
    }

    private fun isLocal(man1: Human, man2: Human): Boolean {
        return man1.nativePlace == man2.nativePlace
    }

    private fun bmi(man: Human): Int {
        return (man.weight / ((man.height.toDouble() / 100)).pow(2)).toInt()
    }


}

// invoke appointment
class HandsomeOrSuccessfulPredicate(val women: Human) : (Human) -> Boolean {
    override fun invoke(human: Human): Boolean = human.isLocalHandsome(women) || human.isRemoteSuccessful(women)

    private fun Human.bmi(): Int = (weight / ((height.toDouble() / 100)).pow(2)).toInt()

    private fun Human.isLocal(human: Human): Boolean = nativePlace == human.nativePlace

    private fun Human.isLocalHandsome(human: Human): Boolean = (
            !married &&
                    age in human.age..30 &&
                    isLocal(human) &&
                    annualSalary >= 10 &&
                    bmi() in 20..25
            )

    private fun Human.isRemoteSuccessful(human: Human): Boolean = (
            !married &&
                    age in human.age..40 &&
                    !isLocal(human) &&
                    annualSalary >= 40
            )
}