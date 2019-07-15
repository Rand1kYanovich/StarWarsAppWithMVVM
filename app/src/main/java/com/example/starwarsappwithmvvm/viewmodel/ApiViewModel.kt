package com.example.starwarsappwithmvvm.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.ImageButton
import com.example.starwarsappwithmvvm.listeners.OnFavoriteClickListener
import com.example.starwarsappwithmvvm.listeners.PaginationScrollListener
import com.example.starwarsappwithmvvm.model.api.OnDataReadyCallback
import com.example.starwarsappwithmvvm.model.entity.FullInfoCard
import com.example.starwarsappwithmvvm.model.repository.ApiRepository
import com.example.starwarsappwithmvvm.model.repository.DataBaseRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers

class ApiViewModel : ViewModel() {


    var listCard = ArrayList<FullInfoCard>()
    var mutableList = MutableLiveData<ArrayList<FullInfoCard>>()
    var cardsList = MutableLiveData<ArrayList<FullInfoCard>>()
    var isLoading = false
    var isFilter = false
    var page = 1


    fun getFirstData(): LiveData<ArrayList<FullInfoCard>> {
        if (cardsList.value == null) {
            ApiRepository.newInstance().loadData(page, object : OnDataReadyCallback {
                override fun onDataReady(list: ArrayList<FullInfoCard>) {
                    listCard = list
                    cardsList.value = listCard
                    mutableList.value = listCard
                    page++

                }
            })
        }
        return mutableList
    }

    fun getFavoriteListener(): OnFavoriteClickListener {
        return object : OnFavoriteClickListener {
            override fun onFavoriteClickListener(
                position: Int,
                btnFavorite: ImageButton,
                list:ArrayList<FullInfoCard>
            ) {


                DataBaseRepository.newInstance().favoriteDao.getById(list[position].name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : DisposableMaybeObserver<FullInfoCard>() {
                        override fun onError(e: Throwable?) {}

                        override fun onSuccess(t: FullInfoCard?) {
                            Completable.fromAction {
                                DataBaseRepository.newInstance().favoriteDao.delete(list[position])
                                list[position].isFavorite = false
                            }
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe()

                        }

                        override fun onComplete() {
                            Completable.fromAction {
                                list[position].isFavorite = true
                                DataBaseRepository.newInstance().favoriteDao.insert(list[position])
                            }.observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe()


                        }
                    })
            }
        }
    }


    fun getScrollListener(recyclerView: RecyclerView, layoutManager: LinearLayoutManager): RecyclerView {
        recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun isLoading(): Boolean = isLoading
            override fun loadMoreItems() {
                isLoading = true
                if (!isFilter) {
                    ApiRepository.newInstance().loadData(page, object : OnDataReadyCallback {
                        override fun onDataReady(list: ArrayList<FullInfoCard>) {
                            listCard.addAll(list)
                            cardsList.value = listCard
                            mutableList.value = listCard
                            isLoading = false
                            page++
                        }
                    })

                } else isLoading = false
            }
        }
        )
        return recyclerView
    }


    fun addSearchListener(btnSearch: ImageButton, etSearch: EditText): ImageButton {
        btnSearch.setOnClickListener {
            if (!etSearch.text.equals("")) {
                isFilter = true
                ApiRepository.newInstance().loadDataWithFilter(etSearch.text.toString(), object : OnDataReadyCallback {
                    override fun onDataReady(list: ArrayList<FullInfoCard>) {
                        isLoading = false
                        mutableList.value = list
                    }
                })
            } else {
                isFilter = false
                isLoading = false
                mutableList.value = cardsList.value

            }
        }
        return btnSearch
    }


}
