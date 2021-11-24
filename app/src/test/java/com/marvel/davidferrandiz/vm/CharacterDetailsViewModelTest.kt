package com.marvel.davidferrandiz.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.davidferrandiz.domain.usecases.IsCharacterFavoriteUseCase
import com.davidferrandiz.domain.usecases.RemoveFavoriteCharacterUseCase
import com.davidferrandiz.domain.usecases.SaveFavoriteCharacterUseCase
import com.davidferrandiz.presentation.vm.CharacterDetailsViewModel
import com.marvel.davidferrandiz.fakeMarvelCharacters
import com.marvel.davidferrandiz.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CharacterDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CharacterDetailsViewModel
    private val saveFavoriteCharacterUseCase: SaveFavoriteCharacterUseCase = mockk()
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase = mockk()
    private val isCharacterFavoriteUseCase: IsCharacterFavoriteUseCase = mockk()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        viewModel =
            CharacterDetailsViewModel(
                saveFavoriteCharacterUseCase,
                removeFavoriteCharacterUseCase,
                isCharacterFavoriteUseCase,
                testCoroutineDispatcher
            )

        viewModel.dispatcher = testCoroutineDispatcher
    }

    @Test
    fun `verify checkCharacter updates liveData value`() {
        testCoroutineDispatcher.runBlockingTest {

            viewModel.character = fakeMarvelCharacters[0]
            // given
            coEvery {
                isCharacterFavoriteUseCase(fakeMarvelCharacters[0].id)
            } returns true

            // when
            viewModel.checkFavoriteCharacter()

            // then
            val realResult = viewModel.isFavoriteLiveData.getOrAwaitValue()
            val expectedResult = true
            Assert.assertEquals(expectedResult, realResult)
        }
    }

    @Test
    fun `verify saveCharacter updates liveData value`() {
        testCoroutineDispatcher.runBlockingTest {

            viewModel.character = fakeMarvelCharacters[0]

            // given
            coEvery {
                saveFavoriteCharacterUseCase(fakeMarvelCharacters[0])
            } returns Unit

            // when
            viewModel.pressFavButton()

            // then
            val realResult = viewModel.isFavoriteLiveData.getOrAwaitValue()
            val expectedResult = true
            Assert.assertEquals(expectedResult, realResult)
        }
    }

    @Test
    fun `verify removeCharacter updates liveData value`() {
        testCoroutineDispatcher.runBlockingTest {

            viewModel.character = fakeMarvelCharacters[0]

            // given
            coEvery {
                removeFavoriteCharacterUseCase(fakeMarvelCharacters[0])
            } returns Unit
            coEvery {
                saveFavoriteCharacterUseCase(fakeMarvelCharacters[0])
            } returns Unit

            // when
            //Save to remove after
            viewModel.pressFavButton()
            viewModel.pressFavButton()

            // then
            val realResult = viewModel.isFavoriteLiveData.getOrAwaitValue()
            val expectedResult = false
            Assert.assertEquals(expectedResult, realResult)
        }
    }
}