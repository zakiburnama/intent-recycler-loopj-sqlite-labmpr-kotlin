package com.example.intent_dan_mengirim_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class CatAdapter(private val listCat: ArrayList<Cat>) : RecyclerView.Adapter<CatAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_cat, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvItemId.text = listCat[position].owner.toString()
        viewHolder.tvItemName.text = listCat[position].tag

        val img = listCat[position].img

        Picasso.get().load(img).into(viewHolder.avatar)

        viewHolder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listCat[viewHolder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listCat.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItemName: TextView = view.findViewById(R.id.tv_cat_name)
        val tvItemId: TextView = view.findViewById(R.id.tv_cat_id)
        val avatar: ImageView = view.findViewById(R.id.img_cat)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Cat)
    }
}