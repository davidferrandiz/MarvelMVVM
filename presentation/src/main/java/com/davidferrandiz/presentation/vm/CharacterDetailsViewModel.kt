package com.davidferrandiz.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidferrandiz.domain.entities.MarvelCharacter
import com.davidferrandiz.domain.usecases.IsCharacterFavoriteUseCase
import com.davidferrandiz.domain.usecases.RemoveFavoriteCharacterUseCase
import com.davidferrandiz.domain.usecases.SaveFavoriteCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val saveFavoriteCharacterUseCase: SaveFavoriteCharacterUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase,
    private val isCharacterFavoriteUseCase: IsCharacterFavoriteUseCase,
) : ViewModel() {

    lateinit var character: MarvelCharacter

    private val isFavoriteMutableLiveData = MutableLiveData<Boolean>()
    val isFavoriteLiveData: LiveData<Boolean> get() = isFavoriteMutableLiveData

    var dispatcher = Dispatchers.IO

    fun pressFavButton() {
        if (isFavoriteLiveData.value == true) removeCharacter()
        else saveCharacter()
    }

    private fun saveCharacter() {
        viewModelScope.launch {
            saveFavoriteCharacterUseCase(character)
        }

        isFavoriteMutableLiveData.postValue(true)
    }

    private fun removeCharacter() {
        viewModelScope.launch {
            removeFavoriteCharacterUseCase(character)
        }

        isFavoriteMutableLiveData.postValue(false)
    }

    fun checkFavoriteCharacter() {
        viewModelScope.launch {
            isFavoriteMutableLiveData.postValue(isCharacterFavoriteUseCase(character.id))
        }
    }
}