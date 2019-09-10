package com.example.pokemon.features.pokemons.domain

import com.example.pokemon.features.pokemons.data.network.responses.PokemonsResponse
import com.example.pokemon.features.pokemons.domain.model.Pokemon
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class InteractorImpl(val repository: Repository) : Interactor {

    private var attackIsChecked = false
    private var defenseIsChecked = false
    private var hpIsChecked = false

    override fun expandPokemons(offset: Int): Observable<PokemonsResponse> {
        return repository.expandPokemons(offset)
    }

    override fun updateSort(
        criterion: String,
        pokemons: List<Pokemon>,
        isChecked: Boolean
    ): Observable<List<Pokemon>> {
        return Observable.fromCallable {
            saveCriterion(criterion, isChecked)
            sort(pokemons)
        }.subscribeOn(Schedulers.io())
    }

    private fun saveCriterion(criterion: String, isChecked: Boolean) {
        when (criterion) {
            "Attack" -> {
                attackIsChecked = isChecked
            }
            "Defense" -> {
                defenseIsChecked = isChecked
            }
            "HP" -> {
                hpIsChecked = isChecked
            }
        }
    }

    private fun sort(pokemons: List<Pokemon>) : List<Pokemon> {
        val sortedPokemons = mutableListOf<Pokemon>()
        sortedPokemons.addAll(pokemons)
        if (attackIsChecked and defenseIsChecked and hpIsChecked) {
            sortedPokemons.sortBy {
                var sum = 0
                it.stats.forEach {
                    when (it.stat.name) {
                        "attack" -> { sum += it.base_stat }
                        "defense" -> { sum += it.base_stat }
                        "hp" -> { sum += it.base_stat }
                    }
                }
                sum
            }
        } else {
            if (attackIsChecked and defenseIsChecked and !hpIsChecked) {
                sortedPokemons.sortBy {
                    var sum = 0
                    it.stats.forEach {
                        when (it.stat.name) {
                            "attack" -> { sum += it.base_stat }
                            "defense" -> { sum += it.base_stat }
                        }
                    }
                    sum
                }
            } else {
                if (attackIsChecked and !defenseIsChecked and hpIsChecked) {
                    sortedPokemons.sortBy {
                        var sum = 0
                        it.stats.forEach {
                            when (it.stat.name) {
                                "attack" -> { sum += it.base_stat }
                                "hp" -> { sum += it.base_stat }
                            }
                        }
                        sum
                    }
                } else {
                    if (!attackIsChecked and defenseIsChecked and hpIsChecked) {
                        sortedPokemons.sortBy {
                            var sum = 0
                            it.stats.forEach {
                                when (it.stat.name) {
                                    "defense" -> { sum += it.base_stat }
                                    "hp" -> { sum += it.base_stat }
                                }
                            }
                            sum
                        }
                    } else {
                        if (!attackIsChecked and !defenseIsChecked and hpIsChecked) {
                            sortedPokemons.sortBy {
                                var sum = 0
                                it.stats.forEach {
                                    when (it.stat.name) {
                                        "hp" -> { sum += it.base_stat }
                                    }
                                }
                                sum
                            }
                        } else {
                            if (!attackIsChecked and defenseIsChecked and !hpIsChecked) {
                                sortedPokemons.sortBy {
                                    var sum = 0
                                    it.stats.forEach {
                                        when (it.stat.name) {
                                            "defense" -> { sum += it.base_stat }
                                        }
                                    }
                                    sum
                                }
                            } else {
                                if (attackIsChecked and !defenseIsChecked and !hpIsChecked) {
                                    sortedPokemons.sortBy {
                                        var sum = 0
                                        it.stats.forEach {
                                            when (it.stat.name) {
                                                "attack" -> { sum += it.base_stat }
                                            }
                                        }
                                        sum
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return sortedPokemons.toList()
    }
}