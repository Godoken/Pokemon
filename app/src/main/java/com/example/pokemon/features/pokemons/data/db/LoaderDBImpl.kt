package com.example.pokemon.features.pokemons.data.db

import com.example.pokemon.app.db.PokemonDao
import com.example.pokemon.features.pokemons.data.LoaderDB
import com.example.pokemon.features.pokemons.domain.model.Pokemon
import com.example.pokemon.features.pokemons.domain.model.PokemonsResponse
import io.reactivex.Observable

class LoaderDBImpl(private val pokemonDao: PokemonDao) : LoaderDB {

    override fun insertPokemonsToDatabase(pokemons: List<Pokemon>) {
        pokemonDao.insertAllPokemons(pokemons)
    }

    override fun deletePokemonsFromDatabase() {
        pokemonDao.deleteAllPokemons()
    }

    override fun expandPokemonsFromDatabase(
        offset: Int,
        limitPage: Int
    ): Observable<PokemonsResponse> {
        return pokemonDao.getPokemonsPage(offset, limitPage).map {
            val nextOffset = if (it.isNotEmpty()) {
                "=${offset.plus(limitPage)}&"
            } else {
                "=${offset}&"
            }
            PokemonsResponse(
                964,
                nextOffset,
                null,
                it
            )
        }.toObservable()
    }

}