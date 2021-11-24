package com.davidferrandiz.data.repositories

import com.davidferrandiz.common.Result
import com.davidferrandiz.data.datasources.CharacterListDatasource
import com.davidferrandiz.domain.entities.MarvelCharacter
import com.davidferrandiz.domain.repository.toDomain
import com.davidferrandiz.domain.repositoryinterfaces.CharacterListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterListRepositoryImpl @Inject constructor(
    private val characterListDatasource: CharacterListDatasource,
    private val coroutineDispatcher: CoroutineDispatcher,
) :
    CharacterListRepository {

    override suspend fun getCharacters(): Result<List<MarvelCharacter>> {
        return withContext(coroutineDispatcher) {

            val charactersResponse = characterListDatasource.getCharacters()

            return@withContext if (charactersResponse is Result.Success) {
                val characters = charactersResponse.data.toDomain()
                Result.Success(characters)
            } else {
                charactersResponse as Result.Error
            }
        }
    }
}