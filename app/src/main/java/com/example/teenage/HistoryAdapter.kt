package com.example.teenage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class HistoryAdapter(val listHistory: ArrayList<HistoryCompleteModel>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.HistoryViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
        val history = listHistory[position]

        Glide.with(holder.itemView.context)
            .load(DrinksData.drinkPictures[history.index_gambar])
            .apply(RequestOptions().override(55, 55))
            .into(holder.imgDrink)
        holder.tvDrinkName.text = history.name_drink
        holder.tvVolume.text = history.volume.toString() + " ml"
        holder.tvTime.text = history.jam
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgDrink: ImageView = itemView.findViewById(R.id.tv_drink_image)
        var tvVolume: TextView = itemView.findViewById(R.id.tv_drink_volume_history)
        var tvTime: TextView = itemView.findViewById(R.id.tv_drink_hour)
        var tvDrinkName: TextView = itemView.findViewById(R.id.tv_drink_name)
    }

}