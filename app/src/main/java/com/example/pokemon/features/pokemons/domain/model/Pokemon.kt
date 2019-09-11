package com.example.pokemon.features.pokemons.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pokemon.features.pokemons.data.db.PokemonStatsConverter
import com.example.pokemon.features.pokemons.data.db.PokemonTypesConverter

@Entity
class Pokemon (@field:PrimaryKey
               val id: Int,
               val url: String?,
               val name: String,
               val height: Int,
               val weight: Int,
               @TypeConverters(PokemonStatsConverter::class)
               var stats: List<PokemonStat>,
               @TypeConverters(PokemonTypesConverter::class)
               var types: List<PokemonType>)