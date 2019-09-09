package com.example.pokemon.features.pokemons.data

import com.example.pokemon.features.pokemons.data.network.responses.PokemonsResponse
import io.reactivex.Observable

interface LoaderNetwork {
    fun expandPokemons(offset: Int, limit: Int): Observable<PokemonsResponse>
}