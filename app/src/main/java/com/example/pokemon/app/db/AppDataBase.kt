package com.example.pokemon.app.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokemon.app.App
import com.example.pokemon.features.pokemons.data.db.PokemonStatsConverter
import com.example.pokemon.features.pokemons.data.db.PokemonTypesConverter
import com.example.pokemon.features.pokemons.domain.model.Pokemon


@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
@TypeConverters(PokemonStatsConverter::class, PokemonTypesConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao

    companion object {
        var INSTANCE: AppDataBase? = null

        fun getAppDataBase(): AppDataBase? {
            if (INSTANCE == null){
                synchronized(AppDataBase::class){
                    INSTANCE = Room.databaseBuilder(App.applicationContext(), AppDataBase::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}