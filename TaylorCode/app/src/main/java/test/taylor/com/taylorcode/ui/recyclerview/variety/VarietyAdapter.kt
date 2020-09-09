package test.taylor.com.taylorcode.ui.recyclerview.variety

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter.AdapterProxy

/**
 * A special [RecyclerView.Adapter] which could show variety types of item without rewrite [onCreateViewHolder], [onBindViewHolder] and [getItemViewType].
 * New type of item is added dynamically by [addProxy].
 *
 * the typical usage is like the following:
 *
 * val varietyAdapter = VarietyAdapter().apply {
 *      addProxy(TextAdapterProxy()) // add a new type of item dynamically
 *      addProxy(ImageAdapterProxy()) // add anther new type of item dynamically
 * }
 *
 * // two different types of data
 * val text = TextBean("item 1") // a string data
 * val image = ImageBean("https://xxx.png") // a image url data
 *
 * // combine different type of data into one list
 * val data = mutableListOf<Any>()
 * for (i in 1..10){
 *      data.add(text)
 *      data.add(image)
 * }
 *
 * // bind data to adapter
 * varietyAdapter.datas = data
 *
 * // bind adapter to RecyclerView
 * recyclerView.adapter = varietyAdapter
 * recyclerView.layoutManager = LinearLayoutManager(context)
 * varietyAdapter.notifyDataSetChanged()
 */
class VarietyAdapter(
    /**
     * the list of [AdapterProxy]
     */
    var adapterProxys: AdapterProxys = MutableAdapterProxys(),
    /**
     * the data of this adapter
     */
    var datas: MutableList<Any> = mutableListOf()
) : RecyclerView.Adapter<ViewHolder>() {

    /**
     * add a new type of item for RecyclerView
     */
    inline fun <reified T, VH : ViewHolder> addProxy(proxy: AdapterProxy<T, VH>) {
        adapterProxys.add(proxy.apply { type = T::class.java })
    }

    /**
     * remove a type of item for RecyclerView
     */
    inline fun <T, VH : ViewHolder> removeProxy(proxy: AdapterProxy<T, VH>) {
        adapterProxys.remove(proxy)
    }

    /**
     * a way for [ViewHolder] to communicate with [RecyclerView.Adapter]
     */
    var action: ((Any?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return adapterProxys.get<Any, ViewHolder>(viewType).onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        adapterProxys.get<Any, ViewHolder>(getItemViewType(position)).onBindViewHolder(holder, datas[position], position, action)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        adapterProxys.get<Any, ViewHolder>(getItemViewType(position)).onBindViewHolder(holder, datas[position], position, action, payloads)
    }

    override fun getItemCount(): Int = datas.size

    override fun getItemViewType(position: Int): Int {
        return getProxyIndex(datas[position])
    }

    private fun getProxyIndex(data: Any): Int = adapterProxys.indexOf(data.javaClass)

    /**
     * the proxy of [RecyclerView.Adapter], which has the similar function to it.
     * the business layer implements [AdapterProxy] to define how does the item look like
     */
    abstract class AdapterProxy<T, VH : RecyclerView.ViewHolder> {
        /**
         * the type of data in [RecyclerView.Adapter]
         */
        var type: Class<T>? = null

        abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

        abstract fun onBindViewHolder(holder: VH, data: T, index: Int, action: ((Any?) -> Unit)? = null)

        fun onBindViewHolder(holder: VH, data: T, index: Int, action: ((Any?) -> Unit)? = null, payloads: MutableList<Any>) {
            onBindViewHolder(holder, data, index, action)
        }
    }

    /**
     * the abstraction of [AdapterProxy] list
     */
    interface AdapterProxys {

        fun size(): Int

        fun <T, VH : RecyclerView.ViewHolder> get(index: Int): AdapterProxy<T, VH>

        fun <T, VH : RecyclerView.ViewHolder> add(proxy: AdapterProxy<T, VH>)

        fun <T, VH : RecyclerView.ViewHolder> remove(proxy: AdapterProxy<T, VH>)

        fun indexOf(cls: Class<*>): Int
    }
}