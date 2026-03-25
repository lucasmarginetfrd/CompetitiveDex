package com.epicjugador.competitivedex.data.local

import android.content.Context
import androidx.room.Room
import com.epicjugador.competitivedex.data.local.database.AppDatabase
import com.epicjugador.competitivedex.data.local.database.PokemonUsageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "competitive_dex_db"
        ).build()
    }

    @Provides
    fun provideDao(db: AppDatabase): PokemonUsageDao {
        return db.pokemonUsageDao()
    }
}