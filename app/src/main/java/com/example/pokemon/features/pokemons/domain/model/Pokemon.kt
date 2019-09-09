package com.example.pokemon.features.pokemons.domain.model

class Pokemon (val id: Int,
               val url: String?,
               val name: String,
               val height: Int,
               val weight: Int,
               val stats: List<PokemonStat>)