package com.example.starwarsappwithmvvm.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.support.v4.content.ContextCompat
import android.widget.ImageButton
import com.example.starwarsappwithmvvm.R
import com.example.starwarsappwithmvvm.model.database.FavoriteDao
import com.example.starwarsappwithmvvm.model.entity.FullInfoCard
import com.example.starwarsappwithmvvm.model.repository.DataBaseRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers

class DatabaseViewModel : ViewModel() {


    fun getClickListener(btnFavorite: ImageButton, fullCardObject: FullInfoCard, context: Context): ImageButton {
        btnFavorite.setOnClickListener {
            if (fullCardObject.isFavorite) {
                btnFavorite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_false))
            } else {
                btnFavorite.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_true))
            }
            DataBaseRepository.newInstance()
                .getDao()
                .getById(fullCardObject.name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableMaybeObserver<FullInfoCard>() {
                    override fun onError(e: Throwable?) {}

                    override fun onSuccess(t: FullInfoCard?) {
                        Completable.fromAction {
                            DataBaseRepository.newInstance().favoriteDao.delete(fullCardObject)
                            fullCardObject.isFavorite = false
                        }.observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe()
                    }

                    override fun onComplete() {
                        Completable.fromAction {
                            fullCardObject.isFavorite = true
                            DataBaseRepository.newInstance().favoriteDao.insert(fullCardObject)
                        }.observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe()
                    }
                })
        }
        return btnFavorite
    }

    fun getDao(): FavoriteDao = DataBaseRepository.newInstance().getDao()


}