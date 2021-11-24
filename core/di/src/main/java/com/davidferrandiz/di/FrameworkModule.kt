package com.davidferrandiz.di

import android.content.Context
import androidx.room.Room
import com.davidferrandiz.api.ApiConstants
import com.davidferrandiz.api.ApiService
import com.davidferrandiz.api.MarvelApiInterceptor
import com.davidferrandiz.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FrameworkModule {
    @Provides
    @Singleton
    @Named("baseUrl")
    fun baseUrlProvider() = ApiConstants.BASE_URL

    @Provides
    @Singleton
    fun retrofitProvider(@Named("baseUrl") baseUrl: String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(MarvelApiInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-marvel"
        ).build()

    @Provides
    @Named("IO")
    fun provideIODispatcher() : CoroutineDispatcher = Dispatchers.IO
}