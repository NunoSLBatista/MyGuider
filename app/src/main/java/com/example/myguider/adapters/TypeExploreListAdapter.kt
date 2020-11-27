package com.example.myguider.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myguider.R
import com.example.myguider.models.TypeExplore
import com.google.android.material.card.MaterialCardView


class TypeExploreListAdapter (private val context: Context, private val typesList: ArrayList<TypeExplore>, val positionSelected: Int, val listener : OnActionListener) : RecyclerView.Adapter<TypeExploreListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeExploreListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return typesList.size
    }

    override fun onBindViewHolder(holder: TypeExploreListAdapter.ViewHolder, position: Int) {

        holder.typeTextView.setText(typesList.get(position).name)

        Glide
            .with(context)
            .load(typesList[position].image)
            .centerCrop()
            .placeholder(R.drawable.map_icon)
            .into(holder.icon);
        holder.cardView.setOnClickListener {
            listener.startActivity(context, typesList.get(position), position)
        }

        holder.typeTextView.setOnClickListener {
            listener.startActivity(context, typesList.get(position), position)
        }

    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val typeTextView = itemView.findViewById(R.id.nameTypeTextField) as TextView
        val icon = itemView.findViewById(R.id.iconTypeImage) as ImageView
        val cardView = itemView.findViewById(R.id.card) as MaterialCardView
    }


    interface OnActionListener {
        fun startActivity(context: Context, typeItem: TypeExplore, position: Int)
    }

}