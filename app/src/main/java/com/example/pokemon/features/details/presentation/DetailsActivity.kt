
package com.example.pokemon.features.details.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemon.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initView()
    }

    private fun initView() {
        val baseParameters: List<String> = intent.getStringArrayListExtra("baseParameters")

        pokemon_types.text = intent.getStringExtra("typeNames")
        baseParameters.forEachIndexed { index, parameter ->
            when (index) {
                0 -> pokemon_title.text = parameter
                1 -> pokemon_attack.text = parameter
                2 -> pokemon_defense.text = parameter
                3 -> pokemon_hp.text = parameter
            }
        }
    }
}
