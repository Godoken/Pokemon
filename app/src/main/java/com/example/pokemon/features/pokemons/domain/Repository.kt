package com.example.pokemon.features.pokemons.domain

import com.example.pokemon.features.pokemons.data.network.responses.PokemonsResponse
import io.reactivex.Observable

interface Repository {
    fun expandPokemons(offset: Int): Observable<PokemonsResponse>
}