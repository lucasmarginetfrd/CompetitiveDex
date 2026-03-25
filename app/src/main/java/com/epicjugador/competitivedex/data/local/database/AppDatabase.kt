package com.epicjugador.competitivedex.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.epicjugador.competitivedex.data.local.database.PokemonUsageDao
import com.epicjugador.competitivedex.data.local.database.PokemonUsageEntity

@Database(
    entities = [PokemonUsageEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonUsageDao(): PokemonUsageDao
}