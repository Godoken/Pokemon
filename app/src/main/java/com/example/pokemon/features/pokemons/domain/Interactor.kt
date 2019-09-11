package com.example.pokemon.features.pokemons.domain

import com.example.pokemon.features.pokemons.domain.model.Pokemon
import com.example.pokemon.features.pokemons.domain.model.PokemonsResponse
import io.reactivex.Observable

interface Interactor {
    fun updateSort(
        criterion: String,
        pokemons: List<Pokemon>,
        isChecked: Boolean
    ): Observable<List<Pokemon>>
    fun expandPokemons(offset: Int): Observable<PokemonsResponse>
    fun newPokemons(count: Int?): Observable<PokemonsResponse>
}