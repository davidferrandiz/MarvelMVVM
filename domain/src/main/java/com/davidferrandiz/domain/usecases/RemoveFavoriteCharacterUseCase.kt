package com.davidferrandiz.domain.usecases

import com.davidferrandiz.domain.entities.MarvelCharacter
import com.davidferrandiz.domain.repositoryinterfaces.FavoriteCharactersRepository
import javax.inject.Inject

class RemoveFavoriteCharacterUseCase @Inject constructor
    (private val favoriteCharactersRepository: FavoriteCharactersRepository) {

    operator fun invoke(marvelCharacter: MarvelCharacter) {
        favoriteCharactersRepository.removeCharacter(marvelCharacter)
    }
}