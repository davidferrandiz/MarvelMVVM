package com.davidferrandiz.data.datasources

import com.davidferrandiz.common.Result
import com.davidferrandiz.db.entities.CharacterEntity

interface FavoriteCharactersLocalDatasource {
    fun saveCharacter(characterEntity: CharacterEntity)
    fun removeCharacter(characterEntity: CharacterEntity)
    fun getFavoriteCharacters() : Result<List<CharacterEntity>>
    fun isCharacterFavorite(id: Int) : Boolean
}