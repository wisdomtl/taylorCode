package test.taylor.com.taylorcode.ui.recyclerview

import androidx.recyclerview.widget.RecyclerView

interface AdapterProxys {

    fun size():Int

    fun <T, VH : RecyclerView.ViewHolder> get(index: Int): AdapterProxy<T, VH>

    fun <T, VH : RecyclerView.ViewHolder> add(proxy: AdapterProxy<T,VH>)

    fun <T, VH : RecyclerView.ViewHolder> remove(proxy: AdapterProxy<T,VH>)

    fun indexOf(cls:Class<*>):Int
}