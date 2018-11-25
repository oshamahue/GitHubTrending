package com.oshamahue.githubtrending.com.oshamahue.githubtrending.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.oshamahue.githubtrending.api.GitHubService
import com.oshamahue.githubtrending.viewmodel.TrendingViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    single<Converter.Factory> {
        MoshiConverterFactory.create(
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        )
    }

    single<OkHttpClient> { OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build() }

    single {
        val retrofit = Retrofit.Builder()
            .client(get())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(get())
            .baseUrl("https://api.github.com")
            .build()

        retrofit.create(GitHubService::class.java)
    }
    viewModel {
        val trendingViewModel = TrendingViewModel(get())
        trendingViewModel.getWeeklyTrending()
        trendingViewModel
    }

}




