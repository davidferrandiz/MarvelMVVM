package com.davidferrandiz.domain.usecases

import com.davidferrandiz.domain.entities.MarvelCharacter
import com.davidferrandiz.domain.repositoryinterfaces.FavoriteCharactersRepository
import javax.inject.Inject

class SaveFavoriteCharacterUseCase @Inject constructor
    (private val favoriteCharactersRepository: FavoriteCharactersRepository) {

    operator fun invoke(marvelCharacter: MarvelCharacter) {
        favoriteCharactersRepository.saveCharacter(marvelCharacter)
    }
}