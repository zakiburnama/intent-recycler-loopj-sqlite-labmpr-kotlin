package com.example.intent_dan_mengirim_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AnimekuAdapter(private val listAnimeku: ArrayList<Animeku>) : RecyclerView.Adapter<AnimekuAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, description, photo) = listAnimeku[position]
        holder.imgAnime.setImageResource(photo)
        holder.tvNameAnime.text = name
        holder.tvDescriptionAnime.text = description
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listAnimeku[holder.adapterPosition])
        }

    }

    override fun getItemCount(): Int = listAnimeku.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAnime: ImageView = itemView.findViewById(R.id.img_anime)
        var tvNameAnime: TextView = itemView.findViewById(R.id.tv_anime_id)
        var tvDescriptionAnime: TextView = itemView.findViewById(R.id.tv_anime_name)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Animeku)
    }
}