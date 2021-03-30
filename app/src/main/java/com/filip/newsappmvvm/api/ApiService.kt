package com.filip.newsappmvvm.api

import com.filip.newsappmvvm.data.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("articles?")
    fun getArticles(@Query("source") source:String,
                    @Query("sortBy") sortBy:String,
                    @Query("apiKey") apiKey:String) : Call<NewsResponse>

}