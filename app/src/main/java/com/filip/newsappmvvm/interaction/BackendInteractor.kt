package com.filip.newsappmvvm.interaction

import com.filip.newsappmvvm.api.ApiService
import com.filip.newsappmvvm.common.*
import com.filip.newsappmvvm.data.model.ArticleModel
import com.filip.newsappmvvm.data.response.NewsResponse
import org.koin.core.KoinComponent

const val ERROR_CODE = 404

class BackendInteractor(private val apiService: ApiService) : BackendInteractorInterface,
    KoinComponent {

    override suspend fun getArticles(
        source: String,
        sortBy: String,
        apiKey: String
    ): Result<NewsResponse> {
        apiService.getArticles(source, sortBy, apiKey)
            .awaitResult()
            .onSuccess { return Success(it) }
            .onError { return handleError(it) }
        return Failure(HttpError(Throwable(), ERROR_CODE))
    }

    private fun handleError(error: HttpError): Failure {
        return Failure(error)
    }
}