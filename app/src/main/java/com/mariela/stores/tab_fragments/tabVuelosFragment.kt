package com.mariela.stores.tab_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mariela.stores.R
import com.mariela.stores.vuelosFB.VueloAdapter
import com.mariela.stores.vuelosFB.Vuelo

class tabVuelosFragment : Fragment(), DataChangeListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var vueloAdapter: VueloAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab_vuelos, container, false)

        // Inicializar RecyclerView
        recyclerView = view.findViewById(R.id.recyclerviewV)
        vueloAdapter = VueloAdapter(this)
        recyclerView.adapter = vueloAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Aquí puedes llamar a una función para cargar los datos en el adapter
        // Por ejemplo, loadVuelosData()
        // Obtener datos de Firebase y actualizar el adaptador
        vueloAdapter.obtenerDatosDesdeFirebase()

        return view
    }

    // Puedes agregar una función para cargar los datos en el RecyclerView
    private fun loadVuelosData() {
        // Obtener la lista de vuelos (puedes obtenerla de tu fuente de datos)
        val listaVuelos: MutableList<Vuelo> = obtenerListaDeVuelos()

        // Establecer la lista en el adapter
        vueloAdapter.setItems(listaVuelos)
    }

    // Esta función es solo un ejemplo, deberías obtener la lista de vuelos desde tu fuente de datos
    private fun obtenerListaDeVuelos(): MutableList<Vuelo> {
        val listaDeVuelos: MutableList<Vuelo> = mutableListOf()

        // Agrega vuelos manualmente a la lista
        val vuelo1 = Vuelo("Vuelo001", "Origen1", "Destino1", "2023-01-01","2023-01-01","2","Economica","Normal")
        val vuelo2 = Vuelo("Vuelo002", "Origen2", "Destino2", "2023-02-01","2023-01-01","2","Economica","Normal")
        val vuelo3 = Vuelo("Vuelo003", "Origen3", "Destino3", "2023-03-01","2023-01-01","2","Economica","Normal")

        listaDeVuelos.add(vuelo1)
        listaDeVuelos.add(vuelo2)
        listaDeVuelos.add(vuelo3)

        // Retorna la lista de vuelos
        return listaDeVuelos
    }

    override fun onDataLoaded() {
        Toast.makeText(requireContext(), "Datos cargados exitosamente", Toast.LENGTH_SHORT).show()

        // Verificar que la lista no esté vacía
        if (vueloAdapter.itemCount == 0) {
            Toast.makeText(requireContext(), "La lista esta vacia", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDataLoadError(errorMessage: String) {
        Toast.makeText(requireContext(), "Error al cargar datos: $errorMessage", Toast.LENGTH_SHORT).show()
    }
}