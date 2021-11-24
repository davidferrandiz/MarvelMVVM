package com.davidferrandiz.api

import com.davidferrandiz.api.responses.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(ApiConstants.CHARACTERS)
    suspend fun getCharacters(@Query("limit") limit: Int = 20): CharactersResponse
}