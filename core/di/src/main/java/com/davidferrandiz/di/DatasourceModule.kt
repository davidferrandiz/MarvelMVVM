package com.davidferrandiz.di

import com.davidferrandiz.api.ApiService
import com.davidferrandiz.data.datasources.CharacterListDatasource
import com.davidferrandiz.data.datasources.CharacterListDatasourceImpl
import com.davidferrandiz.data.datasources.FavoriteCharactersLocalDatasource
import com.davidferrandiz.data.datasources.FavoriteCharactersLocalDatasourceImpl
import com.davidferrandiz.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatasourceModule {

    @Provides
    fun provideCharactersDatasource(apiService: ApiService): CharacterListDatasource =
        CharacterListDatasourceImpl(apiService)

    @Provides
    fun provideFavoriteCharactersDatasource(appDatabase: AppDatabase): FavoriteCharactersLocalDatasource =
        FavoriteCharactersLocalDatasourceImpl(appDatabase)

}