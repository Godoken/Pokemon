package com.example.pokemon.app.db

import androidx.room.*
import com.example.pokemon.features.pokemons.data.db.PokemonStatsConverter
import com.example.pokemon.features.pokemons.data.db.PokemonTypesConverter
import com.example.pokemon.features.pokemons.domain.model.Pokemon
import io.reactivex.Single


@Dao
@TypeConverters(PokemonStatsConverter::class, PokemonTypesConverter::class)
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPokemons(pokemons: List<Pokemon>): Array<Long>

    @Query("DELETE FROM pokemon")
    fun deleteAllPokemons()

    @Query("SELECT * FROM pokemon LIMIT :limit OFFSET :offset")
    fun getPokemonsPage(offset: Int, limit: Int): Single<List<Pokemon>>
}