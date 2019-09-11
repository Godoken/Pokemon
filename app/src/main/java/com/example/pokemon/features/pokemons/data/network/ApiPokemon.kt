package com.example.pokemon.features.pokemons.data.network

import com.example.pokemon.features.pokemons.domain.model.Pokemon
import com.example.pokemon.features.pokemons.domain.model.PokemonsResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiPokemon {

    @GET("pokemon/")
    fun expandPokemonsSimple(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<PokemonsResponse>

    @GET("pokemon/{name}/")
    fun expandPokemonFull(@Path("name") name: String): Call<Pokemon>
}