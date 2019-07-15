package com.example.starwarsappwithmvvm

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.starwarsappwithmvvm.listeners.OnCardClickListener
import com.example.starwarsappwithmvvm.listeners.OnFavoriteClickListener
import com.example.starwarsappwithmvvm.model.entity.FullInfoCard

class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvName: TextView? = null
    var clCard: ConstraintLayout? = null
    var btnFavorite: ImageButton? = null

    init {
        clCard = itemView.findViewById(R.id.clCard)
        tvName = itemView.findViewById(R.id.tvName)
        btnFavorite = itemView.findViewById(R.id.btnFavorite)

    }

    fun bind(
        position: Int,
        clickListener: OnCardClickListener,
        cardsList: ArrayList<FullInfoCard>,
        favoriteClickListener: OnFavoriteClickListener,
        btnFavorite: ImageButton
    ) {
        itemView.setOnClickListener { v -> clickListener.onCardClickListener(v!!, position, cardsList) }
        this.btnFavorite!!.setOnClickListener {
            favoriteClickListener.onFavoriteClickListener(position, btnFavorite,cardsList)
        }
    }

}