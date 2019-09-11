package com.example.pokemon.features.pokemons.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemon.features.pokemons.domain.Interactor
import com.example.pokemon.features.pokemons.domain.model.Pokemon
import com.example.pokemon.features.pokemons.domain.model.PokemonsResponse
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class ViewModel(private val interactor: Interactor) : ViewModel() {

    private var nextPageUrl: String? = null
    private var previousPageUrl: String? = null
    private var count: Int? = null

    private lateinit var pokemons: MutableLiveData<List<Pokemon>>
    private lateinit var updateSort: MutableLiveData<List<Pokemon>>
    private lateinit var newPokemons: MutableLiveData<List<Pokemon>>
    private lateinit var progress: MutableLiveData<String>

    fun expandPokemons(notSwipeRefresh: Boolean) : MutableLiveData<List<Pokemon>> {
        pokemons = MutableLiveData()
        val observable: Observable<PokemonsResponse> = interactor.expandPokemons(nextPageUrl?.substringBeforeLast("&")?.substringAfterLast("=")?.toInt() ?: 0)

        val observer: Observer<PokemonsResponse> = object : Observer<PokemonsResponse> {
            override fun onSubscribe(d: Disposable) {
                if (notSwipeRefresh) progress.postValue("onSubscribe")
            }

            override fun onNext(pokemonsResult: PokemonsResponse) {
                nextPageUrl = pokemonsResult.next
                previousPageUrl = pokemonsResult.previous
                count = pokemonsResult.count
                pokemons.postValue(pokemonsResult.results)
            }

            override fun onError(e: Throwable) {
                progress.postValue("onError")
            }

            override fun onComplete() {
                progress.postValue("onComplete")
            }
        }
        observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
        return pokemons
    }

    fun getProgress(): MutableLiveData<String> {
        progress = MutableLiveData()
        return progress
    }

    fun updateSort(criterion: String, pokemons: List<Pokemon>, isChecked: Boolean): MutableLiveData<List<Pokemon>> {
        updateSort = MutableLiveData()
        val observable: Observable<List<Pokemon>> = interactor.updateSort(criterion, pokemons, isChecked)
        val observer: Observer<List<Pokemon>> = object : Observer<List<Pokemon>> {
            override fun onSubscribe(d: Disposable) {
                progress.postValue("onSubscribe")
            }

            override fun onNext(pokemons: List<Pokemon>) {
                updateSort.postValue(pokemons)
            }

            override fun onError(e: Throwable) {
                progress.postValue("onError")
            }

            override fun onComplete() {
                progress.postValue("onComplete")
            }
        }
        observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
        return updateSort
    }

    fun newPokemons(): MutableLiveData<List<Pokemon>> {
        newPokemons = MutableLiveData()
        val observable: Observable<PokemonsResponse> = interactor.newPokemons(count)
        val observer: Observer<PokemonsResponse> = object : Observer<PokemonsResponse> {
            override fun onSubscribe(d: Disposable) {
                progress.postValue("onSubscribe")
            }

            override fun onNext(pokemonsResult: PokemonsResponse) {
                nextPageUrl = pokemonsResult.next
                previousPageUrl = pokemonsResult.previous
                count = pokemonsResult.count
                newPokemons.postValue(pokemonsResult.results)
            }

            override fun onError(e: Throwable) {
                progress.postValue("onError")
            }

            override fun onComplete() {
                progress.postValue("onComplete")
            }
        }
        observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
        return newPokemons
    }
}