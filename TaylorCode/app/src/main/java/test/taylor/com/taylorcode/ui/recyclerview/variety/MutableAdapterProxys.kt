package test.taylor.com.taylorcode.ui.recyclerview.variety

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter.AdapterProxy

class MutableAdapterProxys(
    var proxys: MutableList<AdapterProxy<*, *>> = mutableListOf()
) : VarietyAdapter.AdapterProxys {
    override fun size() = proxys.size

    @Suppress("UNCHECKED_CAST")
    override fun <T, VH : ViewHolder> get(index: Int): AdapterProxy<T, VH> = proxys[index] as AdapterProxy<T, VH>

    override fun <T, VH : ViewHolder> add(proxy: AdapterProxy<T, VH>) {
        proxys.add(proxy)
    }

    override fun <T, VH : ViewHolder> remove(proxy: AdapterProxy<T, VH>) {
        proxys.remove(proxy)
    }

    override fun indexOf(cls: Class<*>): Int {
       return proxys.indexOfFirst { it.type == cls }
    }
}