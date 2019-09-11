package com.example.pokemon.features.pokemons.data

import com.example.pokemon.features.pokemons.domain.Repository
import com.example.pokemon.features.pokemons.domain.model.PokemonsResponse
import io.reactivex.Observable

class RepositoryImpl(val loaderNetwork: LoaderNetwork, val loaderDB: LoaderDB) : Repository {
    override fun newPokemons(randomOffset: Int, limitPage: Int): Observable<PokemonsResponse> {
        return loaderNetwork.expandPokemons(randomOffset, limitPage)
            .doOnNext {
                loaderDB.deletePokemonsFromDatabase()
                loaderDB.insertPokemonsToDatabase(it.results)
            }
    }

    override fun expandPokemons(offset: Int, limitPage: Int): Observable<PokemonsResponse> {
        return expandPokemonsFromNet(offset, limitPage)
            .onErrorResumeNext(expandPokemonsFromDatabase(offset, limitPage) )
    }

    private fun expandPokemonsFromNet(offset: Int, limitPage: Int): Observable<PokemonsResponse> {
        return loaderNetwork.expandPokemons(offset, limitPage)
            .doOnNext { loaderDB.insertPokemonsToDatabase(it.results) }
    }

    private fun expandPokemonsFromDatabase(offset: Int, limitPage: Int): Observable<PokemonsResponse> {
        return loaderDB.expandPokemonsFromDatabase(offset, limitPage)
    }
}