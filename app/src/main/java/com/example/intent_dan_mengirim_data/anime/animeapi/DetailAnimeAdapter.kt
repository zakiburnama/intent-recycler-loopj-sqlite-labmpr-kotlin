package com.example.intent_dan_mengirim_data.anime.animeapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intent_dan_mengirim_data.R

//class DetailAnimeAdapter {
//}

class DetailAnimeAdapter(private val listAnime: ArrayList<String>) : RecyclerView
.Adapter<DetailAnimeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_anime_detail, viewGroup, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvItemFact.text = listAnime[position]
    }
    override fun getItemCount(): Int {
        return listAnime.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItemFact: TextView = view.findViewById(R.id.tv_anime_fact)
    }
}