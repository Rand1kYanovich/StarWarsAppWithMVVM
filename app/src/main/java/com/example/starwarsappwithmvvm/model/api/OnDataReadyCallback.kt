package com.example.starwarsappwithmvvm.model.api

import com.example.starwarsappwithmvvm.model.entity.FullInfoCard

interface OnDataReadyCallback {
    fun onDataReady(list: ArrayList<FullInfoCard>)
}