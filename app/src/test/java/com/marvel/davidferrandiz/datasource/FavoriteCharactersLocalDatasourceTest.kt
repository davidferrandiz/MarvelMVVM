package com.marvel.davidferrandiz.datasource

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.davidferrandiz.common.Result
import com.davidferrandiz.data.datasources.FavoriteCharactersLocalDatasourceImpl
import com.davidferrandiz.db.AppDatabase
import com.marvel.davidferrandiz.fakeCharacterEntities
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FavoriteCharactersLocalDatasourceTest {

    private val mockRoomDb: AppDatabase = mockk()
    private lateinit var favoriteCharactersLocalDatasourceImpl: FavoriteCharactersLocalDatasourceImpl

    @Before
    fun setup() {
        favoriteCharactersLocalDatasourceImpl = FavoriteCharactersLocalDatasourceImpl(mockRoomDb)
    }

    @Test
    fun `verify getFavoriteCharacters result when api returns success`() {
        runBlockingTest {
            // given
            coEvery {
                mockRoomDb.characterDao().getFavoriteCharacters()
            } returns fakeCharacterEntities

            // when
            val favoriteCharactersResponseResult =
                favoriteCharactersLocalDatasourceImpl.getFavoriteCharacters()

            // then
            val expectedResult = com.davidferrandiz.common.Result.Success(fakeCharacterEntities)
            Assert.assertEquals(expectedResult, favoriteCharactersResponseResult)
        }
    }

    @Test
    fun `verify getFavorite result when Exception`() {
        runBlockingTest {
            // given
            coEvery {
                mockRoomDb.characterDao().getFavoriteCharacters()
            } throws Exception()

            // when
            val favoriteCharactersResponseResult =
                favoriteCharactersLocalDatasourceImpl.getFavoriteCharacters()

            // then
            val expectedResult = com.davidferrandiz.common.exceptions.GenericException::class
            val realResult = (favoriteCharactersResponseResult as Result.Error).ex::class
            Assert.assertEquals(expectedResult, realResult)
        }
    }
}