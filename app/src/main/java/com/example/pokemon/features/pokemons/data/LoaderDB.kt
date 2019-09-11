package com.example.pokemon.features.pokemons.data

import com.example.pokemon.features.pokemons.domain.model.Pokemon
import com.example.pokemon.features.pokemons.domain.model.PokemonsResponse
import io.reactivex.Observable

interface LoaderDB {
    fun insertPokemonsToDatabase(pokemons: List<Pokemon>)
    fun expandPokemonsFromDatabase(offset: Int, limitPage: Int): Observable<PokemonsResponse>
    fun deletePokemonsFromDatabase()
}