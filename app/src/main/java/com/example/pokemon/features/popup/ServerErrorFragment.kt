package com.example.pokemon.features.popup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pokemon.R
import kotlinx.android.synthetic.main.server_error.view.*

class ServerErrorFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view: View =  inflater.inflate(R.layout.server_error, container, false)

        view.server_error_button.setOnClickListener {
            activity!!.finish()
        }
        return view
    }
}