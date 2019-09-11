package com.example.pokemon.features.pokemons.data.db

import androidx.room.TypeConverter
import com.example.pokemon.features.pokemons.domain.model.PokemonStat
import com.example.pokemon.features.pokemons.domain.model.Stat

class PokemonStatsConverter {

    @TypeConverter
    fun fromPokemonStats(pokemonStats: List<PokemonStat>): String {
        var base_stats = ""
        var stat_names = ""
        pokemonStats.forEach {
            base_stats = base_stats.plus("${it.base_stat},")
            stat_names = stat_names.plus("${it.stat.name},")
        }
        val stringForDb = "$base_stats;$stat_names"
        return stringForDb
    }

    @TypeConverter
    fun toPokemonStats(data: String): List<PokemonStat> {
        val base_stats = data.substringBefore(";")
        val stat_names  = data.substringAfter(";")
        val base_stats_array = base_stats.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val stat_names_array = stat_names.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val stats: MutableList<Stat> = ArrayList()
        stat_names_array.forEach {
            stats.add(Stat(it))
        }
        val pokemonStats: MutableList<PokemonStat> = ArrayList()
        base_stats_array.forEachIndexed { index, base_stat ->
            pokemonStats.add(PokemonStat(stats[index], base_stat.toInt()))
        }
        return pokemonStats.toList()
    }

}