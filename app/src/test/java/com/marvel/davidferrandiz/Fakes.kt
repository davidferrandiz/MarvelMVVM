package com.marvel.davidferrandiz

import com.davidferrandiz.api.responses.CharacterDataResponse
import com.davidferrandiz.api.responses.CharacterResponse
import com.davidferrandiz.api.responses.CharacterThumbnailResponse
import com.davidferrandiz.api.responses.CharactersResponse
import com.davidferrandiz.db.entities.CharacterEntity
import com.davidferrandiz.domain.entities.MarvelCharacter
import com.davidferrandiz.presentation.uidata.CharacterListItem

val fakeThumbnail = CharacterThumbnailResponse("pathimage", "jpg")

val fakeCharacterList = listOf(
    CharacterResponse(1, "a", "abcdefg", fakeThumbnail),
    CharacterResponse(2, "b", "abcdefg", fakeThumbnail),
    CharacterResponse(3, "c", "abcdefg", fakeThumbnail),
    CharacterResponse(4, "d", "abcdefg", fakeThumbnail),
)

val fakeCharacterResponse = CharactersResponse(200, CharacterDataResponse(fakeCharacterList))

val fakeMarvelCharacters = listOf(
    MarvelCharacter(1, "a", "abcdefg", "pathimage/standard_medium.jpg"),
    MarvelCharacter(2, "b", "abcdefg", "pathimage/standard_medium.jpg"),
    MarvelCharacter(3, "c", "abcdefg", "pathimage/standard_medium.jpg"),
    MarvelCharacter(4, "d", "abcdefg", "pathimage/standard_medium.jpg")
)

val fakeCharacterEntities = listOf(
    CharacterEntity(1, "a", "abcdefg", "pathimage/standard_medium.jpg"),
    CharacterEntity(2, "b", "abcdefg", "pathimage/standard_medium.jpg"),
    CharacterEntity(3, "c", "abcdefg", "pathimage/standard_medium.jpg"),
    CharacterEntity(4, "d", "abcdefg", "pathimage/standard_medium.jpg"),
)

val fakeCharacterUIItems = listOf(
    CharacterListItem(1, "a", "pathimage/standard_medium.jpg"),
    CharacterListItem(2, "b", "pathimage/standard_medium.jpg"),
    CharacterListItem(3, "c", "pathimage/standard_medium.jpg"),
    CharacterListItem(4, "d", "pathimage/standard_medium.jpg")
)