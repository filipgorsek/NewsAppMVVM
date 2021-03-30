package com.filip.newsappmvvm.di

import android.content.Context
import com.filip.newsappmvvm.R
import com.filip.newsappmvvm.api.ApiService
import com.filip.newsappmvvm.coroutines.CoroutineContextProviderImpl
import com.filip.newsappmvvm.interaction.BackendInteractor
import com.filip.newsappmvvm.interaction.BackendInteractorInterface
import com.filip.newsappmvvm.prefs.PrefsHelper
import com.filip.newsappmvvm.prefs.PrefsHelperInterface
import com.filip.newsappmvvm.ui.news.NewsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val PREFERENCES_NAME = "newsAppPrefs"
private const val LOGGING_INTERCEPTOR = "logging"
private const val BACKEND_RETROFIT_NEWS_APP = "newsAppBackend"

val appModule = module {
    single { androidContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE) }
    single<PrefsHelperInterface> { PrefsHelper(get()) }
}

val networkingModule = module {

    single { GsonConverterFactory.create() as Converter.Factory }

    single(named(LOGGING_INTERCEPTOR)) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }
    //okHttpClient
    single {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(get<HttpLoggingInterceptor>(named(LOGGING_INTERCEPTOR)))
            }
            readTimeout(1L, TimeUnit.MINUTES)
            connectTimeout(1L, TimeUnit.MINUTES)
        }.build()
    }
    single(named(BACKEND_RETROFIT_NEWS_APP)) {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.base_backend_url))
            .client(get())
            .addConverterFactory(get())
            .build()
    }
    single { get<Retrofit>(named(BACKEND_RETROFIT_NEWS_APP)).create(ApiService::class.java) }
}

val interactionModule = module {
    single<BackendInteractorInterface> { BackendInteractor(get()) }
}

val viewModule = module {
    viewModel { NewsViewModel(get(), get()) }
}

val coroutineModule = module {
    single { CoroutineContextProviderImpl() }
}

val modules = listOf(
    appModule,
    coroutineModule,
    networkingModule,
    interactionModule,
    viewModule)