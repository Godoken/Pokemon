package com.example.pokemon.features.pokemons.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemon.features.pokemons.data.network.responses.PokemonsResponse
import com.example.pokemon.features.pokemons.domain.Interactor
import com.example.pokemon.features.pokemons.domain.model.Pokemon
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class ViewModel(private val interactor: Interactor) : ViewModel() {

    private var nextPageUrl: String? = null
    private var previousPageUrl: String? = null

    private lateinit var pokemons: MutableLiveData<List<Pokemon>>
    private lateinit var updateSort: MutableLiveData<Boolean>
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

    fun updateSort(criterion: String): MutableLiveData<Boolean> {
        updateSort = MutableLiveData()
        val observable: Observable<Boolean> = interactor.updateSort(criterion)
        val observer: Observer<Boolean> = object : Observer<Boolean> {
            override fun onSubscribe(d: Disposable) {
                progress.postValue("onSubscribe")
            }

            override fun onNext(result: Boolean) {
                updateSort.postValue(result)
            }

            override fun onError(e: Throwable) {}

            override fun onComplete() {}
        }
        observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
        return updateSort
    }
}