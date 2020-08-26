package test.taylor.com.taylorcode.ui.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class AdapterProxy<T, VH : RecyclerView.ViewHolder> {

    /**
     * the type of data in [RecyclerView.Adapter]
     */
    var type: Class<T>? = null

    abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder

    abstract fun onBindViewHolder(holder: VH, data: T)

    fun onBindViewHolder(holder: VH, data: T, payloads: List<Any>) {
        onBindViewHolder(holder, data)
    }
}