package com.davidferrandiz.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidferrandiz.common.exceptions.ExceptionType
import com.davidferrandiz.domain.entities.MarvelCharacter
import com.davidferrandiz.domain.usecases.GetCharactersUseCase
import com.davidferrandiz.domain.usecases.GetFavoriteCharactersUseCase
import com.davidferrandiz.presentation.toUI
import com.davidferrandiz.presentation.uidata.CharacterListItem
import com.davidferrandiz.presentation.uidata.utils.FeedbackCreator
import com.davidferrandiz.presentation.uidata.utils.FeedbackType
import com.davidferrandiz.presentation.uidata.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase,
    private val feedbackCreator: FeedbackCreator,
) : ViewModel() {

    private val charactersMutableLiveData = MutableLiveData<Resource<List<CharacterListItem>>>()
    val charactersLiveData: LiveData<Resource<List<CharacterListItem>>> get() = charactersMutableLiveData

    private val characterDetailsMutableLiveData = MutableLiveData<MarvelCharacter>()
    val characterDetailsLiveData: LiveData<MarvelCharacter> get() = characterDetailsMutableLiveData

    private val showFeedbackMutableLiveData = MutableLiveData<Pair<String, () -> Unit>>()
    val showFeedbackLiveData: LiveData<Pair<String, () -> Unit>> get() = showFeedbackMutableLiveData

    private val favoriteStateMutableLiveData = MutableLiveData<Boolean>()
    val favoriteStateLiveData: LiveData<Boolean> get() = favoriteStateMutableLiveData

    lateinit var characters: List<MarvelCharacter>

    fun pressFavButton() {
        if (favoriteStateLiveData.value == true) {
            getAllCharacters()
        } else {
            getFavoriteCharacters()
        }
    }

    fun refreshCharacters() {
        if (favoriteStateLiveData.value == true) {
            getFavoriteCharacters()
        } else {
            getAllCharacters()
        }
    }

    fun getAllCharacters() {

        viewModelScope.launch {
            charactersMutableLiveData.postValue(Resource.Loading())
            favoriteStateMutableLiveData.value = false

            getCharactersUseCase().fold(
                onSuccess = {
                    if (it.isEmpty()) {
                        charactersMutableLiveData.postValue(Resource.Error(showFeedback { getAllCharacters() }))
                    } else {
                        characters = it
                        charactersMutableLiveData.postValue(
                            Resource.Success(
                                it.map { character ->
                                    character.toUI()
                                })
                        )
                    }
                },
                onError = {
                    charactersMutableLiveData.postValue(Resource.Error(showFeedback(it) { getAllCharacters() }))
                }
            )

        }
    }

    private fun getFavoriteCharacters() {
        viewModelScope.launch {
            charactersMutableLiveData.value = Resource.Loading()
            favoriteStateMutableLiveData.value = true

            getFavoriteCharactersUseCase().fold(
                onSuccess = {
                    if (it.isEmpty()) {
                        charactersMutableLiveData.postValue(Resource.Error(showFeedback { getAllCharacters() }))
                    } else {
                        characters = it
                        charactersMutableLiveData.postValue(
                            Resource.Success(
                                it.map { character ->
                                    character.toUI()
                                })
                        )
                    }
                },
                onError = {
                    charactersMutableLiveData.postValue(Resource.Error(showFeedback(it) { getAllCharacters() }))
                }
            )

        }
    }

    private fun showFeedback(actionToRetry: () -> Unit): Pair<String, () -> Unit> {
        val feedback = feedbackCreator.create(FeedbackType.GENERIC_EMPTY_VIEW)
        return Pair(feedback, actionToRetry)
    }

    private fun showFeedback(
        ex: ExceptionType,
        actionToRetry: () -> Unit,
    ): Pair<String, () -> Unit> {
        val feedback = feedbackCreator.create(ex)
        return Pair(feedback, actionToRetry)
    }

    fun goToCharacterDetails(id: Int) {
        val character = characters.single { it.id == id }
        characterDetailsMutableLiveData.value = character
    }
}