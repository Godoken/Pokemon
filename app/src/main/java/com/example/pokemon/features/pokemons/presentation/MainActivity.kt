package com.example.pokemon.features.pokemons.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemon.R
import com.example.pokemon.features.details.presentation.DetailsActivity
import com.example.pokemon.features.popup.ProgressFragment
import com.example.pokemon.features.popup.ServerErrorFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: ViewModel
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(ViewModel::class.java)

        viewModel.getProgress().observe(this, Observer {
            when (it) {
                "onSubscribe" -> {showProgress()}
                "onError" -> {
                    hideProgress()
                    showErrorMessage()}
                "onComplete" -> {hideProgress()}
            }
        })

        adapter = Adapter(this@MainActivity, object : Adapter.Listener {
            override fun onSelect(baseParameters: List<String>,
                                  typeNames: String) {
                startActivity(Intent(this@MainActivity, DetailsActivity::class.java)
                    .putStringArrayListExtra("baseParameters", ArrayList(baseParameters))
                    .putExtra("typeNames", typeNames))
            }
        })
        pokemon_recycler_view.adapter = adapter
        pokemon_recycler_view.layoutManager = LinearLayoutManager(this)

        viewModel.expandPokemons(true).observe(this@MainActivity, Observer {
            adapter.expandPokemons(it)
        })

        swipe_refresh.setOnRefreshListener {
            viewModel.expandPokemons(false).observe(this@MainActivity, Observer {
                adapter.expandPokemons(it)
                swipe_refresh.isRefreshing = false
            })
        }

        attack_checkBox.setOnClickListener(this)
        defense_checkBox.setOnClickListener(this)
        hp_checkBox.setOnClickListener(this)

        button.setOnClickListener {
            viewModel.newPokemons().observe(this@MainActivity, Observer {
                attack_checkBox.isChecked = false
                defense_checkBox.isChecked = false
                hp_checkBox.isChecked = false
                adapter.setPokemons(it)
            })
        }
    }

    override fun onClick(v: View?) {
        if (v is CheckBox) {
            viewModel.updateSort(v.text.toString(),
                adapter.getPokemons(), v.isChecked).observe(this@MainActivity, Observer {
                adapter.setPokemons(it)
            })
        }
    }


    private fun showErrorMessage() {
        val serverErrorFragment = ServerErrorFragment()
        supportFragmentManager.beginTransaction().add(R.id.pokemon_fragment_container, serverErrorFragment).commit()
    }

    private fun showProgress() {
        val progressFragment = ProgressFragment()
        supportFragmentManager.beginTransaction().add(R.id.pokemon_fragment_container, progressFragment).commit()
        attack_checkBox.visibility = View.INVISIBLE
        defense_checkBox.visibility = View.INVISIBLE
        hp_checkBox.visibility = View.INVISIBLE
        pokemon_recycler_view.visibility = View.INVISIBLE
        button.visibility = View.INVISIBLE
    }

    private fun hideProgress() {
        supportFragmentManager.findFragmentById(R.id.pokemon_fragment_container)?.let {
            supportFragmentManager.beginTransaction().remove(it).commit()
        }
        attack_checkBox.visibility = View.VISIBLE
        defense_checkBox.visibility = View.VISIBLE
        hp_checkBox.visibility = View.VISIBLE
        pokemon_recycler_view.visibility = View.VISIBLE
        button.visibility = View.VISIBLE
    }
}
