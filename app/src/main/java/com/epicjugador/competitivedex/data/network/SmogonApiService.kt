package com.epicjugador.competitivedex.data.network

import com.epicjugador.competitivedex.data.network.response.AllStatsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SmogonApiService {
    @GET("stats/{month}/chaos/{format}-1630.json")
    suspend fun getStats(
        @Path("month") month: String,
        @Path("format") format: String
    ): AllStatsResponse
}