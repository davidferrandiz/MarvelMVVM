package com.davidferrandiz.api.responses

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("thumbnail")
    var thumbnail: CharacterThumbnailResponse
)

data class CharacterThumbnailResponse(
    @SerializedName("path")
    var path: String,
    @SerializedName("extension")
    var extension: String
)

data class CharactersResponse(
    @SerializedName("code")
    var code: Int,
    @SerializedName("data")
    var data: CharacterDataResponse
)

data class CharacterDataResponse(
    @SerializedName("results")
    var result: List<CharacterResponse>
)
