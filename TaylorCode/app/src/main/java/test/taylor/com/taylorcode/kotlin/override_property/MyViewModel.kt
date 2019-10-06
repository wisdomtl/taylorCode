package test.taylor.com.taylorcode.kotlin.override_property

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    internal val beanLiveData = MutableLiveData<List<MyBean>>()

    internal val colorLiveData = MutableLiveData<ColorBean>()

    fun fetchBean() {
        listOf(MyBean("a"), MyBean("b"), MyBean("c"), MyBean("d")).let { beanLiveData.postValue(it) }
    }

    fun fetchColor() {
        ColorBean("#ff00ff").let { colorLiveData.postValue(it) }
    }
}