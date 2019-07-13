package com.example.starwarsappwithmvvm.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.starwarsappwithmvvm.model.api.OnDataReadyCallback
import com.example.starwarsappwithmvvm.model.entity.FullInfoCard
import com.example.starwarsappwithmvvm.model.repository.DataBaseRepository

class DatabaseViewModel : ViewModel() {

    private var favoriteList = MutableLiveData<ArrayList<FullInfoCard>>()


    fun getFavoriteList(): LiveData<ArrayList<FullInfoCard>> {
        if (favoriteList.value == null) {
            DataBaseRepository.newInstance().getAllFavorites(object : OnDataReadyCallback {
                override fun onDataReady(list: ArrayList<FullInfoCard>) {
                    favoriteList.value = list
                }
            })
        }
        return favoriteList
    }

}