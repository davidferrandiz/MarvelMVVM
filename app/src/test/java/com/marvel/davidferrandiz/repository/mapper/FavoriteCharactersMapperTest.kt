package com.marvel.davidferrandiz.repository.mapper

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.davidferrandiz.domain.repository.toDomain
import com.davidferrandiz.domain.repository.toRoomEntity
import com.marvel.davidferrandiz.fakeCharacterEntities
import com.marvel.davidferrandiz.fakeMarvelCharacters
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteCharactersMapperTest {

    @Test
    fun `verify map from marvelCharacter to room entity`() {
        val characters = fakeMarvelCharacters.map { it.toRoomEntity() }

        val expectedResult = fakeCharacterEntities
        Assert.assertEquals(expectedResult, characters)
    }

    @Test
    fun `verify map from room entity to marvelCharacter`() {
        val characters = fakeCharacterEntities.map { it.toDomain() }

        val expectedResult = fakeMarvelCharacters
        Assert.assertEquals(expectedResult, characters)
    }
}