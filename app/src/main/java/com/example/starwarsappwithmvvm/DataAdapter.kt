package com.example.starwarsappwithmvvm

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.starwarsappwithmvvm.listeners.OnCardClickListener
import com.example.starwarsappwithmvvm.listeners.OnFavoriteClickListener
import com.example.starwarsappwithmvvm.model.entity.FullInfoCard

class DataAdapter constructor(private var cardsList: ArrayList<FullInfoCard>, private val context: Context) :
    RecyclerView.Adapter<DataViewHolder>() {


    private lateinit var colorArray: Array<String>
    private lateinit var listener: OnCardClickListener
    private lateinit var favoriteListener: OnFavoriteClickListener
    private var colorPosition: Int = 0


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): DataViewHolder {
        val layoutView: View = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)

        return DataViewHolder(layoutView)
    }


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item: FullInfoCard = cardsList[position]
        holder.tvName.text = item.name

        if (item.color.isEmpty()) {
            if (colorPosition >= colorArray.size) colorPosition = 0
            holder.clCard.setBackgroundColor(Color.parseColor(colorArray[colorPosition]))
            item.color = colorArray[colorPosition]
            holder.btnFavorite.setBackgroundColor(Color.parseColor(colorArray[colorPosition]))
            colorPosition++
        } else {
            holder.clCard.setBackgroundColor(Color.parseColor(item.color))
            holder.btnFavorite.setBackgroundColor(Color.parseColor(item.color))
        }

        if (item.isFavorite) holder.btnFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_favorite_true
            )
        )
        else holder.btnFavorite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_false))
        holder.bind(position, listener, cardsList, favoriteListener, holder.btnFavorite,context)
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }


    fun setColorArray(colorArray: Array<String>) {
        this.colorArray = colorArray
    }

    fun setClickListener(listener: OnCardClickListener) {
        this.listener = listener
    }

    fun setFavoriteListener(favoriteListener: OnFavoriteClickListener) {
        this.favoriteListener = favoriteListener
    }


    fun setList(list: ArrayList<FullInfoCard>) {
        cardsList = list
        notifyDataSetChanged()
    }


}