package com.example.getir_bootcamp_final_project.di

import com.example.getir_bootcamp_final_project.data.remote.ProductService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val BASE_URL = "https://65c38b5339055e7482c12050.mockapi.io/api/"

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gson = Gson()
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.NONE
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        OkHttpClient.Builder().apply {
            callTimeout(40, TimeUnit.SECONDS)
            connectTimeout(40, TimeUnit.SECONDS)
            readTimeout(40, TimeUnit.SECONDS)
            addInterceptor(httpLoggingInterceptor)
            return build()
        }
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }
}