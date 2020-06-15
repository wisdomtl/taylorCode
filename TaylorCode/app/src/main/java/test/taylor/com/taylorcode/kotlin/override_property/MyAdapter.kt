package test.taylor.com.taylorcode.kotlin.override_property

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.R

abstract class MyAdapter(private val myBean: List<MyBean>?) : RecyclerView.Adapter<MyViewHolder>() {

    abstract val color: ColorBean?

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_viewholder, parent, false);

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return myBean?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myBean?.get(position)?.let { holder.bind(it, color) }
    }
}



