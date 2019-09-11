package com.example.pokemon.features.pokemons.data.db

import androidx.room.TypeConverter
import com.example.pokemon.features.pokemons.domain.model.PokemonType
import com.example.pokemon.features.pokemons.domain.model.Type

class PokemonTypesConverter {

    @TypeConverter
    fun fromPokemonTypes(pokemonTypes: List<PokemonType>): String {
        var type_names = ""
        pokemonTypes.forEach {
            type_names = type_names.plus("${it.type.name},")
        }
        return type_names
    }

    @TypeConverter
    fun toPokemonTypes(data: String): List<PokemonType> {
        val type_names_array = data.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val types: MutableList<Type> = ArrayList()
        type_names_array.forEach {
            types.add(Type(it))
        }
        val pokemonTypes: MutableList<PokemonType> = ArrayList()
        types.forEach {
            pokemonTypes.add(PokemonType(it))
        }
        return pokemonTypes.toList()
    }
}