package com.hamilton.services.hiring.impl.di

import com.hamilton.services.hiring.api.HiringApi
import com.hamilton.services.hiring.api.HiringRepository
import com.hamilton.services.hiring.impl.HiringRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

@Module
@InstallIn(SingletonComponent::class)
object HiringDataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val networkJson = Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                networkJson.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideHiringApi(retrofit: Retrofit): HiringApi {
        return retrofit.create(HiringApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHiringRepository(hiringApi: HiringApi): HiringRepository {
        return HiringRepositoryImpl(hiringApi)
    }
}