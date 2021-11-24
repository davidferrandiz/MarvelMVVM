package com.davidferrandiz.data.datasources

import com.davidferrandiz.api.responses.CharactersResponse

interface CharacterListDatasource {
    suspend fun getCharacters() : com.davidferrandiz.common.Result<CharactersResponse>
}