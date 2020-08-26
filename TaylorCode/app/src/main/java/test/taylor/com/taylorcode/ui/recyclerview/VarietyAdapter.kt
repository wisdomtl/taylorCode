package test.taylor.com.taylorcode.ui.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class VarietyAdapter(
    var adapterProxys: AdapterProxys = MutableAdapterProxys(),
    var datas: List<Any> = emptyList()
) : RecyclerView.Adapter<ViewHolder>() {

    inline fun <reified T, VH : ViewHolder> addProxy(proxy: AdapterProxy<T, VH>) {
        adapterProxys.add(proxy.apply { type = T::class.java })
    }

    inline fun <T, VH : ViewHolder> removeProxy(proxy: AdapterProxy<T, VH>) {
        adapterProxys.remove(proxy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return adapterProxys.get<Any, ViewHolder>(viewType).onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        adapterProxys.get<Any, ViewHolder>(getItemViewType(position)).onBindViewHolder(holder, datas[position])
    }

    override fun getItemCount(): Int = datas.size

    override fun getItemViewType(position: Int): Int {
        return getProxyIndex(datas[position])
    }

    private fun getProxyIndex(data: Any): Int = adapterProxys.indexOf(data.javaClass)


}