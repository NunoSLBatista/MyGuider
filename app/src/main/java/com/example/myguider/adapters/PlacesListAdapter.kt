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
import com.example.myguider.models.Place
import com.example.myguider.models.TypeExplore
import com.google.android.material.card.MaterialCardView


class PlacesListAdapter (private val context: Context, private var placesList: ArrayList<Place>, val positionSelected: Int, val listener : OnActionListener) : RecyclerView.Adapter<PlacesListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.destination_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return placesList.size
    }

    override fun onBindViewHolder(holder: PlacesListAdapter.ViewHolder, position: Int) {

        holder.titleTextView.setText(placesList.get(position).name)
        holder.descriptionTextView.setText(placesList.get(position).description)
        holder.priceTextView.setText(placesList.get(position).price)

        Glide
            .with(context)
            .load(placesList[position].image)
            .centerCrop()
            .placeholder(R.drawable.map_icon)
            .into(holder.imageView);

        holder.cardView.setOnClickListener {
            listener.startActivity(context, placesList.get(position), position)
        }

        holder.moreButton.setOnClickListener {
            listener.startActivity(context, placesList.get(position), position)
        }

    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView = itemView.findViewById(R.id.title) as TextView
        val descriptionTextView = itemView.findViewById(R.id.description) as TextView
        val imageView = itemView.findViewById(R.id.imageView) as ImageView
        val priceTextView = itemView.findViewById(R.id.price) as TextView
        val moreButton = itemView.findViewById(R.id.more) as TextView
        val cardView = itemView.findViewById(R.id.card) as MaterialCardView
    }


    interface OnActionListener {
        fun startActivity(context: Context, place: Place, position: Int)
    }

}