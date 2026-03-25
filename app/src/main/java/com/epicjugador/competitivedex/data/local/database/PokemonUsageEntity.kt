package com.epicjugador.competitivedex.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_usage")
data class PokemonUsageEntity(
    @PrimaryKey
    val name: String,
    val usage: Double,
    val itemsJson: String,
    val movesJson: String,
    val abilitiesJson: String,
    val teammatesJson: String
)