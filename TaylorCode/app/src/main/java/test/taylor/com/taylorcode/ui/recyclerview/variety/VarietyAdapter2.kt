package test.taylor.com.taylorcode.ui.recyclerview.variety

import android.view.ViewGroup
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import java.lang.reflect.ParameterizedType
import kotlin.math.max

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
 * varietyAdapter.dataList = data
 *
 * // bind adapter to RecyclerView
 * recyclerView.adapter = varietyAdapter
 * recyclerView.layoutManager = LinearLayoutManager(context)
 */
class VarietyAdapter2(
    /**
     * the list of [Proxy]
     */
    private var proxyList: MutableList<Proxy<*, *>> = mutableListOf(),
    /**
     * the dispatcher used by [dataDiffer]
     */
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RecyclerView.Adapter<ViewHolder>() {

    private val dataDiffer = AsyncListDiffer(AdapterListUpdateCallback(this), dispatcher)

    /**
     * the data of this adapter
     */
    var dataList: List<Any>
        set(value) {
            dataDiffer.submitList(value)
        }
        get() = dataDiffer.oldList

    /**
     * preload threshold. [onPreload] will be called if there is [preloadItemCount] items remain in the list
     */
    var preloadItemCount = 0

    /**
     * an lambda will be invoked in [onBindViewHolder] when preload threshold is satisfied
     * implement this lambda with actual data loading action
     */
    var onPreload: (() -> Unit)? = null

    /**
     * scroll state of [RecyclerView] which this adapter attached to
     */
    private var scrollState = SCROLL_STATE_IDLE

    /**
     * add a new type of item for RecyclerView
     */
    fun <T, VH : ViewHolder> addProxy(proxy: Proxy<T, VH>) {
        proxyList.add(proxy)
    }

    /**
     * remove a type of item for RecyclerView
     */
    fun <T, VH : ViewHolder> removeProxy(proxy: Proxy<T, VH>) {
        proxyList.remove(proxy)
    }

    /**
     * a way for [ViewHolder] to communicate with [RecyclerView.Adapter]
     */
    var action: ((Any?) -> Unit)? = null

    var onAttachedToRecyclerView: ((recyclerView: RecyclerView) -> Unit)? = null
    var onDetachedFromRecyclerView: ((recyclerView: RecyclerView) -> Unit)? = null
    var onFailedToRecycleView: ((holder: ViewHolder) -> Boolean)? = null
    var onViewAttachedToWindow: ((holder: ViewHolder) -> Unit)? = null
    var onViewDetachedFromWindow: ((holder: ViewHolder) -> Unit)? = null
    var onViewRecycled: ((holder: ViewHolder) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return proxyList[viewType].onCreateViewHolder(parent, viewType)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (proxyList[getItemViewType(position)] as Proxy<Any, ViewHolder>).onBindViewHolder(holder, dataList[position], position, action)
        checkPreload(position)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        (proxyList[getItemViewType(position)] as Proxy<Any, ViewHolder>).onBindViewHolder(holder, dataList[position], position, action, payloads)
        checkPreload(position)
    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        return getProxyIndex(dataList[position])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        /**
         * keep scroll state in [scrollState] which is used in preloading
         */
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                scrollState = newState
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        onAttachedToRecyclerView?.invoke(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        dataDiffer.cancel()
        onDetachedFromRecyclerView?.invoke(recyclerView)
    }

    override fun onFailedToRecycleView(holder: ViewHolder): Boolean {
        return onFailedToRecycleView?.invoke(holder) ?: return super.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        onViewAttachedToWindow?.invoke(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        onViewDetachedFromWindow?.invoke(holder)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        onViewRecycled?.invoke(holder)
    }

    /**
     * check if preload threshold is satisfied
     */
    private fun checkPreload(position: Int) {
        if (onPreload != null
            && position == max(itemCount - 1 - preloadItemCount, 0)// reach the preload threshold position
            && scrollState != SCROLL_STATE_IDLE // the list is scrolling
        ) {
            onPreload?.invoke()
        }
    }

    /**
     * find the index of [Proxy] according to the [data] in the [proxyList]
     */
    private fun getProxyIndex(data: Any): Int = proxyList.indexOfFirst {
        val firstTypeParamClassName = (it.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0].toString()
        val proxyClassName = it.javaClass.toString()
        firstTypeParamClassName == data.javaClass.toString() // primary condition:if the first type parameter of AdapterProxy is the same as the data, it means the accordingly AdapterProxy found
                && (data as? DataProxyMap)?.toProxy() ?: proxyClassName == proxyClassName // secondary condition: match data to proxy mapping relation defined by the data
    }

    /**
     * the proxy of [RecyclerView.Adapter], which has the similar function to it.
     * the business layer implements [Proxy] to define how does the item look like
     */
    abstract class Proxy<T, VH : ViewHolder> {

        abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder

        abstract fun onBindViewHolder(holder: VH, data: T, index: Int, action: ((Any?) -> Unit)? = null)

        open fun onBindViewHolder(holder: VH, data: T, index: Int, action: ((Any?) -> Unit)? = null, payloads: MutableList<Any>) {
            onBindViewHolder(holder, data, index, action)
        }
    }

    /**
     * If one type of data maps multiple proxy, data class should implements this interface to define the mapping relation
     */
    interface DataProxyMap {
        /**
         * @return the class name of proxy mapped
         */
        fun toProxy(): String
    }
}