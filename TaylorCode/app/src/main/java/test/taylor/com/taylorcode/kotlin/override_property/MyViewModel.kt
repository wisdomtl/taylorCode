package test.taylor.com.taylorcode.kotlin.override_property

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    internal val beanLiveData = MutableLiveData<List<MyBean>>()

    internal val colorLiveData = MutableLiveData<ColorBean>()

    fun fetchBean() {
        listOf(MyBean("a"), MyBean("b"), MyBean("c"), MyBean("d")).let { beanLiveData.postValue(it) }
        viewModelScope.launch {

        }
    }

    fun fetchColor() {
        ColorBean("#ff00ff").let { colorLiveData.postValue(it) }
    }
}