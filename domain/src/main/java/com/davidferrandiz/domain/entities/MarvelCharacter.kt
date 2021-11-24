package com.davidferrandiz.domain.entities

import java.io.Serializable

data class MarvelCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String
) : Serializable
