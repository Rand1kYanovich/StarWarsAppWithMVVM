package com.example.starwarsappwithmvvm.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.media.Image
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import com.example.starwarsappwithmvvm.R
import com.example.starwarsappwithmvvm.listeners.OnFavoriteClickListener
import com.example.starwarsappwithmvvm.model.api.OnDataReadyCallback
import com.example.starwarsappwithmvvm.model.entity.FullInfoCard
import com.example.starwarsappwithmvvm.model.repository.ApiRepository
import com.example.starwarsappwithmvvm.model.repository.DataBaseRepository
import com.example.starwarsappwithmvvm.viewmodel.allCards.PaginationScrollListener

class ApiViewModel : ViewModel() {


    var listCard = ArrayList<FullInfoCard>()
    var mutableList = MutableLiveData<ArrayList<FullInfoCard>>()
    var cardsList = MutableLiveData<ArrayList<FullInfoCard>>()
    var filterList = MutableLiveData<ArrayList<FullInfoCard>>()
    var isLoading = false
    var isFilter = false
    var page = 1



    fun getFirstData(): LiveData<ArrayList<FullInfoCard>> {
        Log.e("CardsListValue",cardsList.value.toString()+"F")
        if (cardsList.value == null) {
            ApiRepository.newInstace().loadData(page,object : OnDataReadyCallback {
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
                btnFavorite: ImageButton
            ) {
                val isExistCard = DataBaseRepository.newInstance().isExistCard(cardsList.value!![position])

                if (isExistCard) {
                    DataBaseRepository.newInstance().deleteCard(cardsList.value!![position])
                    cardsList.value!![position].isFavorite = false
                } else {
                    cardsList.value!![position].isFavorite = true
                    DataBaseRepository.newInstance().insertCard(cardsList.value!![position])

                }
            }
        }

    }

   fun getClickListener(btnFavorite:ImageButton,fullCardObject:FullInfoCard):ImageButton{
        btnFavorite.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                val isExistCard = DataBaseRepository.newInstance().isExistCard(fullCardObject)

                if (isExistCard) {
                    DataBaseRepository.newInstance().deleteCard(fullCardObject)
                    fullCardObject.isFavorite = false
                } else {
                    fullCardObject.isFavorite = true
                    DataBaseRepository.newInstance().insertCard(fullCardObject)

                }
            }

        }
        )
       return btnFavorite
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
                ApiRepository.newInstace().loadDataWithFilter(etSearch.text.toString(),object: OnDataReadyCallback {
                    override fun onDataReady(list: ArrayList<FullInfoCard>) {
                        isLoading  = false
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
