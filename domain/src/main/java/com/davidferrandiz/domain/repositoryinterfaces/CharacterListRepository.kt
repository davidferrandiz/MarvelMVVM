package com.davidferrandiz.domain.repositoryinterfaces

import com.davidferrandiz.common.Result
import com.davidferrandiz.domain.entities.MarvelCharacter

interface CharacterListRepository {
    suspend fun getCharacters() : Result<List<MarvelCharacter>>
}