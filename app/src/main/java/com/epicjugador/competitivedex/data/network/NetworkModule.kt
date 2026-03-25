package com.epicjugador.competitivedex.data.network

import android.content.Context
import com.epicjugador.competitivedex.data.RepositoryImp
import com.epicjugador.competitivedex.data.core.interceptors.AuthInterceptor
import com.epicjugador.competitivedex.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @PokeRetrofit
    fun providePokeRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @SmogonRetrofit
    fun provideSmogonRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://www.smogon.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @ShowdownRetrofit
    fun provideShowdownRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://play.pokemonshowdown.com/")
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @Provides
    fun providePokeApiService(@PokeRetrofit retrofit: Retrofit) : PokeApiService {
        return retrofit.create(PokeApiService::class.java)
    }

    @Provides
    fun provideSmogonApiService(@SmogonRetrofit smogonRetrofit: Retrofit) : SmogonApiService {
        return smogonRetrofit.create(SmogonApiService::class.java)
    }

    @Provides
    fun provideShowdownService(@ShowdownRetrofit showdownRetrofit: Retrofit) : ShowdownApiService {
        return showdownRetrofit.create(ShowdownApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePokeRepository(pokeApiService: PokeApiService, @ApplicationContext context: Context): Repository {
        return RepositoryImp(pokeApiService, context)
    }


}