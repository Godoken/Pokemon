package com.example.pokemon.features.pokemons.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.features.pokemons.domain.model.Pokemon

class Adapter (context: Context, private var listener: Listener) : RecyclerView.Adapter<Adapter.Holder>() {

    private val pokemons = ArrayList<Pokemon>()
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = layoutInflater.inflate(R.layout.pokemon_item, parent, false)
        return Holder(itemView, listener)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(pokemons[position])
    }

    fun setPokemons(pokemons: List<Pokemon>) {
        this.pokemons.clear()
        this.pokemons.addAll(pokemons)
        notifyDataSetChanged()
    }

    fun getPokemons() : List<Pokemon> {
        return this.pokemons
    }

    fun expandPokemons(pokemons: List<Pokemon>) {
        this.pokemons.addAll(pokemons)
        notifyDataSetChanged()
    }

    inner class Holder(item: View, private val listener: Listener) :
        RecyclerView.ViewHolder(item) {

        private val title: TextView = item.findViewById(R.id.pokemon_title)
        private val pokemonHp: TextView = item.findViewById(R.id.pokemon_hp)
        private val pokemonAttack: TextView = item.findViewById(R.id.pokemon_attack)
        private val pokemonDefense: TextView = item.findViewById(R.id.pokemon_defense)
        private val pokemonImage: ImageView = item.findViewById(R.id.pokemon_image)

        fun bind(pokemon: Pokemon) {
            title.text = pokemon.name
            pokemon.stats.forEach {
                when (it.stat.name) {
                    "attack" -> { pokemonAttack.text = "Attack: ".plus(it.base_stat.toString()) }
                    "defense" -> { pokemonDefense.text = "Defense: ".plus(it.base_stat.toString()) }
                    "hp" -> { pokemonHp.text = "HP: ".plus(it.base_stat.toString()) }
                }
            }

            itemView.setOnClickListener {
                var typeNames = "Types: "
                pokemon.types.forEach {
                    typeNames += "${it.type.name} "
                }
                listener.onSelect(listOf(pokemon.name, pokemonAttack.text.toString(), pokemonDefense.text.toString(), pokemonHp.text.toString()),
                    typeNames)
            }
        }
    }

    interface Listener {
        fun onSelect(
            baseParameters: List<String>,
            typeNames: String
        )
    }
}