package test.taylor.com.taylorcode.ui.performance.poor_performance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.ui.performance.Header
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class PoorHeaderProxy:VarietyAdapter2.Proxy<Header, PoorHeaderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fans_rank_header_layout,parent,false)
        return PoorHeaderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PoorHeaderViewHolder, data: Header, index: Int, action: ((Any?) -> Unit)?) {
        holder.tvCount?.text = data.count
        holder.tvName?.text = data.name
        holder.tvRank?.text = data.rank
        holder.tvTitle?.text = data.title
    }
}

class PoorHeaderViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    val tvCount = itemView.findViewById<TextView>(R.id.tvCount)
    val tvName = itemView.findViewById<TextView>(R.id.tvName)
    val tvRank = itemView.findViewById<TextView>(R.id.tvRank)
    val tvTitle = itemView.findViewById<TextView>(R.id.tvtitle)
}