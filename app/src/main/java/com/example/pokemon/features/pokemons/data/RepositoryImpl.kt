package com.example.pokemon.features.pokemons.data

import com.example.pokemon.features.pokemons.data.network.responses.PokemonsResponse
import com.example.pokemon.features.pokemons.domain.Repository
import io.reactivex.Observable

class RepositoryImpl(val loaderNetwork: LoaderNetwork) : Repository {
    private val limitPage = 30

    override fun expandPokemons(offset: Int): Observable<PokemonsResponse> {
        return loaderNetwork.expandPokemons(offset, limitPage)
    }
}