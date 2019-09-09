package com.example.pokemon.features.pokemons.data.network.responses

import com.example.pokemon.features.pokemons.domain.model.Pokemon

class PokemonsResponse (val count: Int,
                        val next: String?,
                        val previous: String?,
                        val results: List<Pokemon>)