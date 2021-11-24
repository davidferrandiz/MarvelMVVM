package com.davidferrandiz.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davidferrandiz.db.entities.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}