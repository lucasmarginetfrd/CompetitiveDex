package com.epicjugador.competitivedex.data.network

import com.epicjugador.competitivedex.data.network.response.AbilityResponse
import com.epicjugador.competitivedex.data.network.response.PokeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon/{id}")
    suspend fun getPokeDetails(@Path("id") id: Int): PokeResponse
    @GET("ability/{name}")
    suspend fun getAbilityDetails(@Path("name") name: String): AbilityResponse
}