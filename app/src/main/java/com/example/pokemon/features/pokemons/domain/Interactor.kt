package com.example.pokemon.features.pokemons.domain

import com.example.pokemon.features.pokemons.data.network.responses.PokemonsResponse
import io.reactivex.Observable

interface Interactor {
    fun updateSort(criterion: String): Observable<Boolean>
    fun expandPokemons(offset: Int): Observable<PokemonsResponse>
}