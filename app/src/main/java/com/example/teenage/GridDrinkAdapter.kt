package com.example.teenage

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GridDrinkAdapter(val listDrink: ArrayList<Drink>) :
    RecyclerView.Adapter<GridDrinkAdapter.GridViewHolder>() {
    var mSelectedDrink = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GridDrinkAdapter.GridViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid_drink, parent, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridDrinkAdapter.GridViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(listDrink[position].picture)
            .apply(RequestOptions().override(350, 550)).into(holder.imgPhoto)
        holder.textView.text = listDrink[position].name
        holder.bind(listDrink.get(position))
    }

    override fun getItemCount(): Int {
        return listDrink.size
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        var textView: TextView = itemView.findViewById(R.id.tv_drink)


        fun bind(drink: Drink) {
            if (mSelectedDrink == -1) {
                textView.setTextColor(Color.parseColor("#000000"))
            } else {
                if (mSelectedDrink == adapterPosition) {
                    textView.setTextColor(Color.parseColor("#14279B"))
                } else {
                    textView.setTextColor(Color.parseColor("#000000"))
                }
            }

            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    textView.setTextColor(Color.parseColor("#14279B"))
                    if (mSelectedDrink != adapterPosition) {
                        notifyItemChanged(mSelectedDrink)
                        mSelectedDrink = adapterPosition
                    }
                    Toast.makeText(itemView.context, textView.text.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }

    fun getSelected(): Drink? {
        if (mSelectedDrink != -1) {
            return listDrink.get(mSelectedDrink)
        }
        return null
    }
}