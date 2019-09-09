package com.example.pokemon.features.pokemons.domain

import com.example.pokemon.features.pokemons.data.network.responses.PokemonsResponse
import io.reactivex.Observable


class InteractorImpl(val repository: Repository) : Interactor {

    override fun expandPokemons(offset: Int): Observable<PokemonsResponse> {
        return repository.expandPokemons(offset)
    }

    override fun updateSort(criterion: String): Observable<Boolean> {
        // Hard code
        return Observable.just(true)
    }
}