package com.mariela.stores.fragments

import PaqueteAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mariela.stores.R
import com.mariela.stores.model.Paquete
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PaqueteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño para este fragmento
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter = PaqueteAdapter()
        recyclerView.adapter = adapter

        obtenerDatosDeApi()

        return view
    }

    private fun obtenerDatosDeApi() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitClient.apiService.obtenerPaquetes()
                mostrarDatosEnRecyclerView(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun mostrarDatosEnRecyclerView(paquetes: List<Paquete>) {
        adapter.setPaquetes(paquetes)
    }
}
