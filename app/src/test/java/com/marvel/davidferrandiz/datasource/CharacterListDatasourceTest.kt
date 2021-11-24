package com.marvel.davidferrandiz.datasource

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.davidferrandiz.common.Result
import com.davidferrandiz.data.datasources.CharacterListDatasourceImpl
import com.davidferrandiz.api.ApiService
import com.davidferrandiz.common.exceptions.ExceptionType
import com.marvel.davidferrandiz.fakeCharacterResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CharacterListDatasourceTest {

    private val mockApiService: ApiService = mockk()
    private lateinit var characterListDatasourceImpl: CharacterListDatasourceImpl

    @Before
    fun setup() {
        characterListDatasourceImpl = CharacterListDatasourceImpl(mockApiService)
    }

    @Test
    fun `verify getCharacters result when api returns success`() {
        runBlockingTest {
            // given
            coEvery {
                mockApiService.getCharacters()
            } returns fakeCharacterResponse

            // when
            val charactersResponseResult = characterListDatasourceImpl.getCharacters()

            // then
            val expectedResult = Result.Success(fakeCharacterResponse)
            Assert.assertEquals(expectedResult, charactersResponseResult)
        }
    }

    @Test
    fun `verify getCharacters result when IOException is triggered returns NetworkException`() {
        runBlockingTest {
            // given
            coEvery {
                mockApiService.getCharacters()
            } throws IOException()

            // when
            val charactersResponseResult = characterListDatasourceImpl.getCharacters()

            // then
            val expectedResult = ExceptionType.NetworkException::class
            val realResult = (charactersResponseResult as Result.Error).ex::class
            Assert.assertEquals(expectedResult, realResult)
        }
    }
}