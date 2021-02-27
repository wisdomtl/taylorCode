package test.taylor.com.taylorcode.ui.performance.origin_performance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.formatNums
import test.taylor.com.taylorcode.ui.performance.Rank
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class PoorRankProxy : VarietyAdapter2.Proxy<Rank, PoorFansRankViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fans_rank_layout2, parent, false)
        return PoorFansRankViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PoorFansRankViewHolder, data: Rank, index: Int, action: ((Any?) -> Unit)?) {
        holder.tvCount?.text = data.count.formatNums()
        holder.ivAvatar?.let {
            Glide.with(holder.ivAvatar.context).load(data.avatarUrl).into(it)
        }
        holder.ivLevel?.let {
            Glide.with(holder.ivLevel.context).load(data.levelUrl).into(it)
        }
        holder.tvRank?.text = data.rank.toString()
        holder.tvName?.text = data.name
        holder.tvLevel?.text = data.level.toString()
    }
}

class PoorFansRankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName = itemView.findViewById<TextView>(R.id.tvAvatar)
    val tvRank = itemView.findViewById<TextView>(R.id.ivRank)
    val ivAvatar = itemView.findViewById<ImageView>(R.id.ivAvatar)
    val ivLevel = itemView.findViewById<ImageView>(R.id.ivLevel)
    val tvCount = itemView.findViewById<TextView>(R.id.tvCount)
    val tvLevel = itemView.findViewById<TextView>(R.id.tvLevel)
    val tvTag = itemView.findViewById<TextView>(R.id.tvTag)
}