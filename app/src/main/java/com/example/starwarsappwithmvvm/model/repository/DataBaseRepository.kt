package com.example.starwarsappwithmvvm.model.repository

import com.example.starwarsappwithmvvm.App
import com.example.starwarsappwithmvvm.model.database.FavoriteDao

class DataBaseRepository {


    var favoriteDao: FavoriteDao

    init {
        favoriteDao = App.getInstance().database.favoriteDao()
    }

    fun getDao(): FavoriteDao = favoriteDao

    companion object {
        fun newInstance(): DataBaseRepository = DataBaseRepository()
    }
}