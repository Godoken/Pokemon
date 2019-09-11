package com.example.pokemon.features.pokemons.presentation

import androidx.lifecycle.ViewModelProvider
import com.example.pokemon.app.db.AppDataBase
import com.example.pokemon.app.network.Client
import com.example.pokemon.features.pokemons.data.RepositoryImpl
import com.example.pokemon.features.pokemons.data.db.LoaderDBImpl
import com.example.pokemon.features.pokemons.data.network.LoaderNetworkImpl
import com.example.pokemon.features.pokemons.domain.Interactor
import com.example.pokemon.features.pokemons.domain.InteractorImpl


class ViewModelFactory : ViewModelProvider.Factory {

    private val interactor: Interactor

    init {
        val pokemonDao = AppDataBase.getAppDataBase()!!.pokemonDao
        val loaderDB = LoaderDBImpl(pokemonDao)
        val apiPokemon = Client.instance.getApiPokemon()
        val loaderNetwork = LoaderNetworkImpl(apiPokemon)
        val repository = RepositoryImpl(loaderNetwork, loaderDB)
        this.interactor = InteractorImpl(repository)
    }

    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T{
        return ViewModel(interactor) as T
    }
}