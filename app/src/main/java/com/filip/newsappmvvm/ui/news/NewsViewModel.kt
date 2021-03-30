package com.filip.newsappmvvm.ui.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.filip.newsappmvvm.common.*
import com.filip.newsappmvvm.coroutines.CoroutineContextProviderImpl
import com.filip.newsappmvvm.data.model.ArticleModel
import com.filip.newsappmvvm.data.response.NewsResponse
import com.filip.newsappmvvm.interaction.BackendInteractorInterface
import com.filip.newsappmvvm.prefs.PrefsHelperInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NewsViewModel(
    private val backend: BackendInteractorInterface,
    private val coroutine: CoroutineContextProviderImpl
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = coroutine.main + job

    private val job = Job()

    val newsLisState = MutableLiveData<NewsListState>()
    val newsList = MutableLiveData<NewsResponse>()
    val detailsArticle = MutableLiveData<ArticleModel>()

    fun getArticles(isNetworkAvailable: Boolean) {
        if (isNetworkAvailable) {
            setScreenState(NewsListState.Loading)
            launch(coroutine.io) {
                backend.getArticles(BBC_KEY, TOP_KEY, API_KEY)
                    .onSuccess {
                        if (it.articles.size == 0) {
                            setScreenState(NewsListState.NoData)
                        } else {
                            setScreenState(NewsListState.ShowData)
                            newsList.postValue(it)
                        }
                    }
                    .onError {
                        setScreenState(NewsListState.ErrorBackend)
                        Log.d("Error", it.toString())
                    }
            }
        } else {
            setScreenState(NewsListState.NoInternet)
        }
    }

    private fun setScreenState(state: NewsListState) {
        newsLisState.postValue(state)
    }

    fun setArticleData(articleModel: ArticleModel) {
        detailsArticle.postValue(articleModel)
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}

sealed class NewsListState {
    object NoInternet : NewsListState()
    object NoData : NewsListState()
    object Loading : NewsListState()
    object ShowData : NewsListState()
    object ErrorBackend : NewsListState()
    object ErrorNotFound : NewsListState()
    object ErrorUnauthorized : NewsListState()
    object ErrorBadRequest : NewsListState()
    object ErrorInternalServer: NewsListState()
}