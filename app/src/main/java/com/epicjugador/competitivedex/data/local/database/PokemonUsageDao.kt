package com.epicjugador.competitivedex.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epicjugador.competitivedex.data.local.database.PokemonUsageEntity

@Dao
interface PokemonUsageDao {
    @Query("SELECT * FROM pokemon_usage")
    suspend fun getAll(): List<PokemonUsageEntity>

    @Query("SELECT * FROM pokemon_usage WHERE name = :name")
    suspend fun getByName(name: String): PokemonUsageEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(list: List<PokemonUsageEntity>)
}