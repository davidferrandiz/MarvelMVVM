package com.davidferrandiz.data.datasources

import com.davidferrandiz.api.ApiService
import com.davidferrandiz.api.responses.CharactersResponse
import com.davidferrandiz.common.Result
import com.davidferrandiz.common.exceptions.mapper.handleNetworkExceptions
import javax.inject.Inject

class CharacterListDatasourceImpl @Inject constructor(private val apiService: ApiService) :
    CharacterListDatasource {

    override suspend fun getCharacters(): Result<CharactersResponse> {
        return try {
            val response = apiService.getCharacters()
            return Result.Success(response)
        } catch (ex: Exception) {
            val exception = handleNetworkExceptions(ex)
            Result.Error(exception)
        }
    }
}