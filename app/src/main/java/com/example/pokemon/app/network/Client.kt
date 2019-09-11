package com.example.pokemon.app.network

import com.example.pokemon.BuildConfig
import com.example.pokemon.features.pokemons.data.network.ApiPokemon
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client private constructor() {

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        apiPokemon = retrofit.create(ApiPokemon::class.java)
    }

    companion object {
        val instance = Client()
        private lateinit var apiPokemon: ApiPokemon
        private const val BASE_URL = "https://pokeapi.co/api/v2/"
    }

    fun getApiPokemon(): ApiPokemon {
        return apiPokemon
    }
}