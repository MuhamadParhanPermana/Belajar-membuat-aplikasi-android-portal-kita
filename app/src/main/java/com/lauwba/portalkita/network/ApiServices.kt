package com.lauwba.portalkita.network

import com.lauwba.portalkita.model.ResponseDetailBerita
import com.lauwba.portalkita.model.ResponseListBerita
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("webservices/get_latest_news")
    fun getLatestNews(): retrofit2.Call<ResponseListBerita>

    @GET("webservices/get_detail_news/{id}")
    fun getDetailNews(@Path("id") id: String?): retrofit2.Call<ResponseDetailBerita>

    @GET("webservices/search_news")
    fun searchNews(@Query("q") terms: String?): retrofit2.Call<ResponseListBerita>
}