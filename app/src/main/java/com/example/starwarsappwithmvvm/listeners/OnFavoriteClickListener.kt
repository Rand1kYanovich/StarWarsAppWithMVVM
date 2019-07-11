package com.example.starwarsappwithmvvm.listeners

import android.widget.ImageButton
import com.example.starwarsappwithmvvm.model.entity.FullInfoCard

interface OnFavoriteClickListener {
    fun onFavoriteClickListener(position:Int, btnFavorite: ImageButton)
}