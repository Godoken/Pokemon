package com.example.pokemon.features.pokemons.domain.model

class PokemonsResponse (val count: Int,
                        val next: String?,
                        val previous: String?,
                        val results: List<Pokemon>)