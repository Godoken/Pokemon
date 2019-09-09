package com.example.pokemon.features.pokemons.data.network

import com.example.pokemon.features.pokemons.data.LoaderNetwork
import com.example.pokemon.features.pokemons.data.network.responses.PokemonsResponse
import com.example.pokemon.features.pokemons.domain.model.Pokemon
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*

class LoaderNetworkImpl(private val apiPokemon: ApiPokemon) : LoaderNetwork {

    override fun expandPokemons(offset: Int, limit: Int): Observable<PokemonsResponse> {
        return apiPokemon.expandPokemonsSimple(offset, limit)
            .map {
                val pokemonsFull = ArrayList<Pokemon>()
                it.results.forEach { pokemonSimple ->
                    val pokemonFull = apiPokemon.expandPokemonFull(pokemonSimple.name).execute()
                    pokemonFull.body()?.let {
                        pokemonsFull.add(it)
                    }
                }
                PokemonsResponse(it.count, it.next, it.previous, pokemonsFull)
            }
            .subscribeOn(Schedulers.io())
    }
}