package com.example.starwarsappwithmvvm.model.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.example.starwarsappwithmvvm.App
import com.example.starwarsappwithmvvm.model.database.AppDatabase
import com.example.starwarsappwithmvvm.model.database.FavoriteDao
import com.example.starwarsappwithmvvm.model.entity.FullInfoCard
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers

class DataBaseRepository {



    var favoriteDao: FavoriteDao
    var favoriteList:MutableLiveData<ArrayList<FullInfoCard>> = MutableLiveData()

    init { favoriteDao = App.getInstance().database.favoriteDao() }


    fun getAllFavorites(){
        favoriteDao.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                var list = ArrayList(it)
                favoriteList.postValue(list)
            }
    }

    fun isExistCard(card:FullInfoCard):Boolean{
        return favoriteDao.getById(card.name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .isDisposed
    }

    fun deleteCard(card:FullInfoCard){
        Completable.fromAction(object:Action{
            override fun run() {
                favoriteDao.delete(card)
            }
        })
    }

    fun insertCard(card:FullInfoCard){
            Completable.fromAction(object:Action{
                override fun run() {
                    favoriteDao.insert(card)
                }
            })
        }


    fun getFavoriteList():LiveData<ArrayList<FullInfoCard>> =favoriteList

    companion object{
        fun newInstance():DataBaseRepository = DataBaseRepository()
    }
}