package com.example.starwarsappwithmvvm.model.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.starwarsappwithmvvm.model.api.NetworkService
import com.example.starwarsappwithmvvm.model.api.OnDataReadyCallback
import com.example.starwarsappwithmvvm.model.entity.FullInfoCard
import com.example.starwarsappwithmvvm.model.entity.ResponseResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiRepository{


    var cardsList:MutableLiveData<ArrayList<FullInfoCard>> =MutableLiveData()
    var list = ArrayList<FullInfoCard>()
    var filterList:MutableLiveData<ArrayList<FullInfoCard>> =MutableLiveData()


    fun loadData(page:Int,onDataReadyCallback:OnDataReadyCallback) {
        NetworkService.getInstance()
            .getJSONApi()
            .getCards(page.toString(), "")
            .enqueue(object : Callback<ResponseResult> {
                override fun onFailure(call: Call<ResponseResult>, t: Throwable) {}

                override fun onResponse(call: Call<ResponseResult>, response: Response<ResponseResult>) {
                    if (response.isSuccessful) {
                        val infoPageAndResult: ResponseResult = response.body()!!
                            addCard(ArrayList(infoPageAndResult.results))

                            Log.e("RepoPage",page.toString())
                            onDataReadyCallback.onDataReady(getListCards().value!!)

                    }
                }
            })
    }


    fun loadDataWithFilter(filter: String) {
        NetworkService.getInstance()
            .getJSONApi()
            .getCards("", filter)
            .enqueue(object : Callback<ResponseResult> {
                override fun onFailure(call: Call<ResponseResult>, t: Throwable) {}

                override fun onResponse(call: Call<ResponseResult>, response: Response<ResponseResult>) {
                    if (response.isSuccessful) {
                        val infoPageAndResult: ResponseResult = response.body()!!
                        addFilterList(ArrayList(infoPageAndResult.results))

                    }
                }
            })
    }

    private fun addCard(list:ArrayList<FullInfoCard>){
        this.list.addAll(list)
        cardsList.value = this.list
    }

    private fun addFilterList(list:ArrayList<FullInfoCard>){
        filterList.value!!.addAll(list)
    }

    fun getListCards(): MutableLiveData<ArrayList<FullInfoCard>> = cardsList
    fun getListFilter(): MutableLiveData<ArrayList<FullInfoCard>> = filterList

    companion object {
        fun newInstace(): ApiRepository {
            return ApiRepository()
        }
    }
}