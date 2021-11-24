package com.davidferrandiz.domain.repository

import com.davidferrandiz.api.responses.CharacterThumbnailResponse
import com.davidferrandiz.api.responses.CharactersResponse
import com.davidferrandiz.db.entities.CharacterEntity
import com.davidferrandiz.domain.entities.MarvelCharacter

fun CharactersResponse.toDomain() =
    data.result.map {
        with(it) {
            MarvelCharacter(id, name, description, mapThumbnail(thumbnail))
        }
    }

fun mapThumbnail(thumbnail: CharacterThumbnailResponse) =
    "${thumbnail.path.replace("http", "https")}/standard_medium.${thumbnail.extension}"

fun MarvelCharacter.toRoomEntity() = CharacterEntity(id, name, description, thumbnail)
fun CharacterEntity.toDomain() = MarvelCharacter(id, name, description, thumbnail)