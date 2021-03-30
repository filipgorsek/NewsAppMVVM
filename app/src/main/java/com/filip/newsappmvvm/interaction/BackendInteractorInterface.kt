package com.filip.newsappmvvm.interaction

import com.filip.newsappmvvm.common.Result
import com.filip.newsappmvvm.data.response.NewsResponse

interface BackendInteractorInterface {

    suspend fun getArticles(source:String, sortBy:String,apiKey:String): Result<NewsResponse>

}