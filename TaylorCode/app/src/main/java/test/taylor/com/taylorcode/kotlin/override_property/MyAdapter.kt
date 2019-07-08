package test.taylor.com.taylorcode.kotlin.override_property

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import test.taylor.com.taylorcode.R

abstract class MyAdapter(private val myBean: List<MyBean>?) : RecyclerView.Adapter<MyViewHolder>() {

    abstract val color: ColorBean?

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.my_viewholder, parent, false))
    }

    override fun getItemCount(): Int {
        return myBean?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myBean?.get(position)?.let { holder.bind(it,color) }
    }
}