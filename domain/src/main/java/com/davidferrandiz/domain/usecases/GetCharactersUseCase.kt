package com.davidferrandiz.domain.usecases

import com.davidferrandiz.domain.repositoryinterfaces.CharacterListRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val characterListRepository: CharacterListRepository) {

    suspend operator fun invoke() = characterListRepository.getCharacters()
}