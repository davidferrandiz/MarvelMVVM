package com.marvel.davidferrandiz.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.davidferrandiz.common.Result
import com.davidferrandiz.common.exceptions.ExceptionType
import com.davidferrandiz.presentation.uidata.utils.FeedbackCreator
import com.davidferrandiz.presentation.uidata.utils.FeedbackType
import com.davidferrandiz.domain.entities.MarvelCharacter
import com.davidferrandiz.domain.usecases.GetCharactersUseCase
import com.davidferrandiz.domain.usecases.GetFavoriteCharactersUseCase
import com.davidferrandiz.presentation.vm.CharactersViewModel
import com.marvel.davidferrandiz.fakeCharacterUIItems
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
class CharacterViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CharactersViewModel
    private val getCharactersUseCase: GetCharactersUseCase = mockk()
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase = mockk()
    private val mockFeedbackCreator: FeedbackCreator = mockk()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        viewModel =
            CharactersViewModel(
                getCharactersUseCase,
                getFavoriteCharactersUseCase,
                mockFeedbackCreator,
                testCoroutineDispatcher
            )
    }

    @Test
    fun `verify getCharacters use case return a success result with data`() {
        testCoroutineDispatcher.runBlockingTest {
            // given
            coEvery {
                getCharactersUseCase()
            } returns Result.Success(fakeMarvelCharacters)

            // when
            viewModel.getAllCharacters()

            // then
            val realResult = viewModel.charactersLiveData.getOrAwaitValue()
            val expectedResult = fakeCharacterUIItems
            Assert.assertEquals(expectedResult, realResult)
        }
    }

    @Test
    fun `verify showFeedbackMutableLiveData data when getCharacters returns an error`() {
        testCoroutineDispatcher.runBlockingTest {
            // given
            val fakeFeedback = "fakeFeedback"
            val fakeRetryAction: () -> Unit = {}
            val ex = ExceptionType.GenericException()

            coEvery {
                getCharactersUseCase()
            } returns Result.Error(ex)

            coEvery {
                mockFeedbackCreator.create(ex = any())
            } returns fakeFeedback

            // when
            viewModel.getAllCharacters()

            // then
            val realResult = viewModel.showFeedbackLiveData.getOrAwaitValue()::class.java
            val expectedResult = Pair(fakeFeedback, fakeRetryAction)::class.java
            Assert.assertEquals(expectedResult, realResult)
        }
    }

    @Test
    fun `verify getFavoriteCharacters use case return a success result with data`() {
        testCoroutineDispatcher.runBlockingTest {
            // given
            coEvery {
                getCharactersUseCase()
            } returns Result.Success(listOf(MarvelCharacter(5, "a", "b", "a.jpg")))

            coEvery {
                getFavoriteCharactersUseCase()
            } returns Result.Success(fakeMarvelCharacters)

            // when
            viewModel.getAllCharacters()
            viewModel.pressFavButton()

            // then
            val realResult = viewModel.charactersLiveData.getOrAwaitValue()
            val expectedResult = fakeCharacterUIItems
            Assert.assertEquals(expectedResult, realResult)
        }
    }

    @Test
    fun `verify getFavoriteCharacters use case return an empty list and shows feedback`() {
        testCoroutineDispatcher.runBlockingTest {
            val fakeFeedback = "fakeFeedback"
            val fakeRetryAction: () -> Unit = {}

            // given
            coEvery {
                getCharactersUseCase()
            } returns Result.Success(listOf(MarvelCharacter(5, "a", "b", "a.jpg")))

            coEvery {
                getFavoriteCharactersUseCase()
            } returns Result.Success(emptyList())

            coEvery {
                mockFeedbackCreator.create(FeedbackType.GENERIC_EMPTY_VIEW)
            } returns fakeFeedback

            // when
            viewModel.getAllCharacters()
            viewModel.pressFavButton()

            // then
            val realResult = viewModel.showFeedbackLiveData.getOrAwaitValue()::class.java
            val expectedResult = Pair(fakeFeedback, fakeRetryAction)::class.java
            Assert.assertEquals(expectedResult, realResult)
        }
    }

}