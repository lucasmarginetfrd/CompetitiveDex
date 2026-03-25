package com.epicjugador.competitivedex.data.network

import retrofit2.http.GET

interface ShowdownApiService {
    @GET("data/items.js")
    suspend fun getItemsJS(): String
    @GET("data/moves.js")
    suspend fun getMovesJS(): String
    @GET("data/abilities.js")
    suspend fun getAbilitiesJS(): String
}