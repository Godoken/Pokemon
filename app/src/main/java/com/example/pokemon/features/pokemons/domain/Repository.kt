package com.example.pokemon.features.pokemons.domain

import com.example.pokemon.features.pokemons.domain.model.PokemonsResponse
import io.reactivex.Observable

interface Repository {
    fun expandPokemons(offset: Int, limitPage: Int): Observable<PokemonsResponse>
    fun newPokemons(randomOffset: Int, limitPage: Int): Observable<PokemonsResponse>
}