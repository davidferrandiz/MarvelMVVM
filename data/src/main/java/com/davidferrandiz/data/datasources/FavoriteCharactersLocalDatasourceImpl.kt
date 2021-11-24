package com.davidferrandiz.data.datasources

import com.davidferrandiz.common.Result
import com.davidferrandiz.common.exceptions.ExceptionType
import com.davidferrandiz.db.AppDatabase
import com.davidferrandiz.db.entities.CharacterEntity
import javax.inject.Inject

class FavoriteCharactersLocalDatasourceImpl @Inject constructor(private val appDatabase: AppDatabase) :
    FavoriteCharactersLocalDatasource {

    override fun saveCharacter(characterEntity: CharacterEntity) {
        appDatabase.characterDao().insertFavoriteCharacter(characterEntity)
    }

    override fun removeCharacter(characterEntity: CharacterEntity) =
        appDatabase.characterDao().removeFavoriteCharacter(characterEntity)

    override fun getFavoriteCharacters(): Result<List<CharacterEntity>> {
        return try {
            val a = appDatabase.characterDao().getFavoriteCharacters()
            Result.Success(a)
        } catch (ex: Exception) {
            Result.Error(ExceptionType.GenericException())
        }
    }

    override fun isCharacterFavorite(id: Int): Boolean {
        return appDatabase.characterDao().isCharacterFavorite(id)
    }
}