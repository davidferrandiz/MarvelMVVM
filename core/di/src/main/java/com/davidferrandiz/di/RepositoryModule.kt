package com.davidferrandiz.di

import com.davidferrandiz.data.datasources.CharacterListDatasource
import com.davidferrandiz.data.datasources.FavoriteCharactersLocalDatasource
import com.davidferrandiz.data.repositories.CharacterListRepositoryImpl
import com.davidferrandiz.data.repositories.FavoriteCharactersRepositoryImpl
import com.davidferrandiz.domain.repositoryinterfaces.CharacterListRepository
import com.davidferrandiz.domain.repositoryinterfaces.FavoriteCharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideCharactersRepository(
        characterListDatasource: CharacterListDatasource,
        @Named("IO") dispatcher: CoroutineDispatcher,
    ): CharacterListRepository =
        CharacterListRepositoryImpl(characterListDatasource, dispatcher)

    @Provides
    fun provideFavoriteCharactersRepository(favoriteCharactersLocalDatasource: FavoriteCharactersLocalDatasource):
            FavoriteCharactersRepository =
        FavoriteCharactersRepositoryImpl(favoriteCharactersLocalDatasource)

}