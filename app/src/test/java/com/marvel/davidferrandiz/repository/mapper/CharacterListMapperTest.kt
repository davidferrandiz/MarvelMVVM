package com.marvel.davidferrandiz.repository.mapper

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.davidferrandiz.domain.repository.toDomain
import com.davidferrandiz.presentation.toUI
import com.marvel.davidferrandiz.fakeCharacterResponse
import com.marvel.davidferrandiz.fakeCharacterUIItems
import com.marvel.davidferrandiz.fakeMarvelCharacters
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterListMapperTest {

    @Test
    fun `verify map from response to domain objects`() {
        val characters = fakeCharacterResponse.toDomain()

        val expectedResult = fakeMarvelCharacters
        Assert.assertEquals(expectedResult, characters)
    }

    @Test
    fun `verify map to ui list`() {
        val charactersUI = fakeMarvelCharacters.map { it.toUI() }

        val expectedResult = fakeCharacterUIItems
        Assert.assertEquals(expectedResult, charactersUI)
    }
}