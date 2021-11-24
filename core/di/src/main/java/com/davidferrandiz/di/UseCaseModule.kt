package com.davidferrandiz.di

import com.davidferrandiz.domain.repositoryinterfaces.CharacterListRepository
import com.davidferrandiz.domain.repositoryinterfaces.FavoriteCharactersRepository
import com.davidferrandiz.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideCharactersUseCase(characterListRepository: CharacterListRepository): GetCharactersUseCase =
        GetCharactersUseCase(characterListRepository)

    @Provides
    fun provideGetFavoriteCharactersUseCase(favoriteCharactersRepository: FavoriteCharactersRepository): GetFavoriteCharactersUseCase =
        GetFavoriteCharactersUseCase(favoriteCharactersRepository)

    @Provides
    fun provideSaveFavoriteCharactersUseCase(favoriteCharactersRepository: FavoriteCharactersRepository): SaveFavoriteCharacterUseCase =
        SaveFavoriteCharacterUseCase(favoriteCharactersRepository)

    @Provides
    fun provideRemoveFavoriteCharactersUseCase(favoriteCharactersRepository: FavoriteCharactersRepository): RemoveFavoriteCharacterUseCase =
        RemoveFavoriteCharacterUseCase(favoriteCharactersRepository)

    @Provides
    fun provideIsFavoriteCharacterUseCase(favoriteCharactersRepository: FavoriteCharactersRepository): IsCharacterFavoriteUseCase =
        IsCharacterFavoriteUseCase(favoriteCharactersRepository)

}