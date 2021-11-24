package com.davidferrandiz.data.repositories

import com.davidferrandiz.common.Result
import com.davidferrandiz.data.datasources.FavoriteCharactersLocalDatasource
import com.davidferrandiz.domain.entities.MarvelCharacter
import com.davidferrandiz.domain.repository.toDomain
import com.davidferrandiz.domain.repository.toRoomEntity
import com.davidferrandiz.domain.repositoryinterfaces.FavoriteCharactersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class FavoriteCharactersRepositoryImpl @Inject constructor(
    private val favoriteCharactersLocalDatasource: FavoriteCharactersLocalDatasource,
    @Named("IO") private val coroutineDispatcher: CoroutineDispatcher,
) : FavoriteCharactersRepository {

    override suspend fun saveCharacter(marvelCharacter: MarvelCharacter) {
        withContext(coroutineDispatcher) {
            favoriteCharactersLocalDatasource.saveCharacter(marvelCharacter.toRoomEntity())
        }
    }

    override suspend fun removeCharacter(marvelCharacter: MarvelCharacter) {
        withContext(coroutineDispatcher) {
            favoriteCharactersLocalDatasource.removeCharacter(marvelCharacter.toRoomEntity())
        }
    }

    override suspend fun getFavoriteCharacters(): Result<List<MarvelCharacter>> {
        val charactersResponse =
            withContext(coroutineDispatcher) { favoriteCharactersLocalDatasource.getFavoriteCharacters() }

        return if (charactersResponse is Result.Success) {
            val characters = charactersResponse.data.map { it.toDomain() }

            Result.Success(characters)
        } else {
            charactersResponse as Result.Error
        }
    }

    override suspend fun isCharacterFavorite(id: Int) = withContext(coroutineDispatcher) {
        favoriteCharactersLocalDatasource.isCharacterFavorite(id)
    }

}