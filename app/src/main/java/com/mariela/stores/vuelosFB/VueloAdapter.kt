package com.mariela.stores.vuelosFB

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mariela.stores.R
import com.mariela.stores.tab_fragments.tabVuelosFragment

class VueloAdapter(private val dataChangeListener: tabVuelosFragment) :
    RecyclerView.Adapter<VueloAdapter.VueloViewHolder>() {

    private var vueloList = mutableListOf<Vuelo>()

    fun obtenerDatosDesdeFirebase() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("vuelos")

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val nuevosVuelos = mutableListOf<Vuelo>()

                for (snapshot in dataSnapshot.children) {
                    val vuelo = snapshot.getValue(Vuelo::class.java)
                    vuelo?.let { nuevosVuelos.add(it) }
                }

                vueloList = nuevosVuelos
                notifyDataSetChanged()

                // Notificar al fragmento que los datos han sido cargados
                dataChangeListener.onDataLoaded()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                dataChangeListener.onDataLoadError(databaseError.message)
            }
        })
    }

    class VueloViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var origen: TextView? = null
        private var destino: TextView? = null
        private var fregreso: TextView? = null
        private var fida: TextView? = null
        private var clase: TextView? = null
        private var npasajeros: TextView? = null
        private var equipaje: TextView? = null

        fun setItem(data: Vuelo) {
            origen = itemView.findViewById(R.id.textorigenv)
            destino = itemView.findViewById(R.id.textDestinov)
            fregreso = itemView.findViewById(R.id.textfregresov)
            fida = itemView.findViewById(R.id.textfsalidav)
            clase = itemView.findViewById(R.id.textclasev)
            npasajeros = itemView.findViewById(R.id.textnpasajeros)
            equipaje = itemView.findViewById(R.id.textequipaje)

            origen?.text = data.origen
            destino?.text = data.destino
            fregreso?.text = data.fecharegreso
            fida?.text = data.fechaida
            clase?.text = data.clase
            npasajeros?.text = data.npasajeros
            equipaje?.text = data.equipaje
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VueloViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_vuelo, parent, false)
        return VueloViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VueloViewHolder, position: Int) {
        val vuelo = vueloList[position]
        holder.setItem(vuelo)
    }

    override fun getItemCount(): Int {
        return vueloList.size
    }

    fun setItems(list: MutableList<Vuelo>) {
        this.vueloList = list
        notifyDataSetChanged()
    }

    // Interfaz para notificar eventos al fragmento
    interface DataChangeListener {
        fun onDataLoaded()
        fun onDataLoadError(errorMessage: String)
    }
}