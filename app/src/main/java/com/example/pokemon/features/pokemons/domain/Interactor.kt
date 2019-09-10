package com.example.pokemon.features.pokemons.domain

import com.example.pokemon.features.pokemons.data.network.responses.PokemonsResponse
import com.example.pokemon.features.pokemons.domain.model.Pokemon
import io.reactivex.Observable

interface Interactor {
    fun updateSort(
        criterion: String,
        pokemons: List<Pokemon>,
        isChecked: Boolean
    ): Observable<List<Pokemon>>
    fun expandPokemons(offset: Int): Observable<PokemonsResponse>
}