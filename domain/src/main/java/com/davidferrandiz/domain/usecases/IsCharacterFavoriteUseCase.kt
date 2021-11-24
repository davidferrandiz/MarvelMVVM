package com.davidferrandiz.domain.usecases

import com.davidferrandiz.domain.repositoryinterfaces.FavoriteCharactersRepository
import javax.inject.Inject

class IsCharacterFavoriteUseCase @Inject constructor
    (private val favoriteCharactersRepository: FavoriteCharactersRepository) {

    operator fun invoke(id: Int) = favoriteCharactersRepository.isCharacterFavorite(id)
}