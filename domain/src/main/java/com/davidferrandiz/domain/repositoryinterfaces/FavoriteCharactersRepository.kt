package com.davidferrandiz.domain.repositoryinterfaces

import com.davidferrandiz.domain.entities.MarvelCharacter

interface FavoriteCharactersRepository {
    suspend fun saveCharacter(marvelCharacter: MarvelCharacter)
    suspend fun removeCharacter(marvelCharacter: MarvelCharacter)
    suspend fun getFavoriteCharacters() : com.davidferrandiz.common.Result<List<MarvelCharacter>>
    suspend fun isCharacterFavorite(id: Int) : Boolean
}