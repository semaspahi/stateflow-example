package com.sema.stateflow.di

import com.sema.stateflow.BuildConfig
import com.sema.stateflow.data.api.ApiService
import com.sema.stateflow.data.networking.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideBaseURL(): String {
        return BuildConfig.BASE_URL
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideInterceptor(): HeaderInterceptor {
        return HeaderInterceptor()
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        interceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder();
        okHttpClient.addInterceptor(interceptor)
        okHttpClient.addInterceptor(headerInterceptor)
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideRestAdapter(
        baseURL: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val retro = Retrofit.Builder().baseUrl(baseURL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build();
        return retro
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideIDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return MainScope()
    }
}