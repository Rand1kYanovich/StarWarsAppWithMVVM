package com.example.starwarsappwithmvvm.listeners

import android.view.View
import com.example.starwarsappwithmvvm.model.entity.FullInfoCard

interface OnCardClickListener {

    fun onCardClickListener(view: View, position: Int, cardsList:ArrayList<FullInfoCard>)
}