package com.example.starwarsappwithmvvm.model.api


import com.example.starwarsappwithmvvm.model.entity.ResponseResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JSONPlaceHolderApi {

    @GET("people/")
    fun getCards(@Query("page") page: String, @Query("search") search: String): Call<ResponseResult>

}