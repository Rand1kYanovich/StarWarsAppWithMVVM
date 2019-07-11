package com.example.starwarsappwithmvvm.model.api

import android.arch.lifecycle.MutableLiveData
import com.example.starwarsappwithmvvm.model.entity.FullInfoCard

interface OnDataReadyCallback {
    fun onDataReady(list:ArrayList<FullInfoCard>)
}