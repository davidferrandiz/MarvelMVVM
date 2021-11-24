package com.davidferrandiz.domain.usecases

import com.davidferrandiz.domain.repositoryinterfaces.FavoriteCharactersRepository
import javax.inject.Inject

class GetFavoriteCharactersUseCase @Inject constructor(
    private val favoriteCharactersRepository: FavoriteCharactersRepository
) {

    operator fun invoke() = favoriteCharactersRepository.getFavoriteCharacters()
}