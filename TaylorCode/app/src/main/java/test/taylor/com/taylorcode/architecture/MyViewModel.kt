package test.taylor.com.taylorcode.architecture

import androidx.lifecycle.*
import java.util.concurrent.atomic.AtomicBoolean

class MyViewModel : ViewModel() {

    val selectsListLiveData = MutableLiveData<List<String>>()

    val singleListLiveData = SingleLiveEvent<List<String>>()

    /**
     * case:convert MutableLive data into LiveData
     */
    val sLiveData:LiveData<List<String>> by this::selectsListLiveData

    fun setSelectsList(goods: List<String>) {
        selectsListLiveData.value = goods
        singleListLiveData.value = goods
    }
}

fun <T> LiveData<T>.asTransient(): MutableLiveData<T>  = TransientLiveData(this)

fun <T> liveData(action: MutableLiveData<T>.() -> Unit): MutableLiveData<T> =
    MutableLiveData<T>().apply(action)

// it does not work
open class TransientLiveData<T>(private val liveData: LiveData<T>) : MutableLiveData<T>() {

    private val notConsumed = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, ObserverWrapper(liveData, observer, owner))
    }

    override fun setValue(value: T) {
        notConsumed.set(true)
        super.setValue(value)
    }

    inner class ObserverWrapper<T>(
        private val liveData: LiveData<T>,
        private val observer: Observer<in T>,
        private val owner: LifecycleOwner
    ) : Observer<T> {
        init {
            liveData.observe(owner, this)
        }

        override fun onChanged(t: T) {
            if (notConsumed.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }
}