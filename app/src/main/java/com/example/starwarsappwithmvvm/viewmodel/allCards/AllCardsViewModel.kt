package com.example.starwarsappwithmvvm.viewmodel.allCards

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import com.example.starwarsappwithmvvm.listeners.OnFavoriteClickListener
import com.example.starwarsappwithmvvm.model.api.OnDataReadyCallback
import com.example.starwarsappwithmvvm.model.entity.FullInfoCard
import com.example.starwarsappwithmvvm.model.repository.ApiRepository
import com.example.starwarsappwithmvvm.model.repository.DataBaseRepository

class AllCardsViewModel : ViewModel() {


    var listCard = ArrayList<FullInfoCard>()
    var cardsList = MutableLiveData<ArrayList<FullInfoCard>>()
    var filterList = MutableLiveData<ArrayList<FullInfoCard>>()
    var isLoading = false
    var isFilter = false
    var page = 1



    fun getCardList(): LiveData<ArrayList<FullInfoCard>> {
        Log.e("CardsListValue",cardsList.value.toString()+"F")
        if (cardsList.value == null) {
            ApiRepository.newInstace().loadData(page,object : OnDataReadyCallback {
                override fun onDataReady(list: ArrayList<FullInfoCard>) {
                    listCard = list
                    cardsList.value = listCard
                    page++

                }
            })
        }
        return cardsList
    }

    fun getListFilter(): LiveData<ArrayList<FullInfoCard>> {
        if (filterList.value == null) filterList = ApiRepository.newInstace().getListFilter()
        return filterList
    }


    fun getFavoriteListener(): OnFavoriteClickListener {
        return object : OnFavoriteClickListener {
            override fun onFavoriteClickListener(
                position: Int,
                btnFavorite: ImageButton
            ) {
                val isExistCard = DataBaseRepository.newInstance().isExistCard(cardsList.value!![position])

                if (isExistCard) {
                    DataBaseRepository.newInstance().deleteCard(cardsList.value!![position])
                    cardsList.value!![position].isFavorite = false
                } else {
                    DataBaseRepository.newInstance().insertCard(cardsList.value!![position])
                    cardsList.value!![position].isFavorite = true

                }
            }
        }

    }


    fun getScrollListener(recyclerView: RecyclerView, layoutManager: LinearLayoutManager): RecyclerView {
        recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun isLoading(): Boolean = isLoading
            override fun loadMoreItems() {
                isLoading = true
                if (!isFilter) {
                    ApiRepository.newInstace().loadData(page,object : OnDataReadyCallback {
                        override fun onDataReady(list: ArrayList<FullInfoCard>) {
                            listCard.addAll(list)
                            cardsList.value = listCard
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
                isLoading = false
                isFilter = true
            } else {
                isLoading = false

            }
        }
        return btnSearch
    }


}