package com.davidferrandiz.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.davidferrandiz.db.entities.CharacterEntity

@Dao
interface CharacterDao {

    @Query("SELECT * FROM CharacterEntity")
    fun getFavoriteCharacters(): List<CharacterEntity>

    @Insert
    fun insertFavoriteCharacter(character: CharacterEntity)

    @Delete
    fun removeFavoriteCharacter(character: CharacterEntity)

    @Query("SELECT EXISTS (SELECT 1 FROM CharacterEntity WHERE id = :id)")
    fun isCharacterFavorite(id: Int): Boolean
}