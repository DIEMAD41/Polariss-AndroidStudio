package com.mariela.stores.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.util.Pair

import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mariela.stores.R
import com.mariela.stores.databinding.FragmentVuelosBinding
import com.mariela.stores.vuelosFB.Vuelo

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


class VuelosFragment : Fragment() {

    private var binding: FragmentVuelosBinding? = null

    //Firebase
    private var firebaseDataBase: FirebaseDatabase?=null
    private var databaseReference: DatabaseReference?=null
    private var list = mutableListOf<Vuelo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño para este fragmento
        // Inflar el diseño para este fragmento usando View Binding
        binding = FragmentVuelosBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Firebase implements
        firebaseDataBase=FirebaseDatabase.getInstance()
        databaseReference= firebaseDataBase?.getReference("data")
        getData()

        binding?.btnReservaVuelo?.setOnClickListener{SaveData()}

        // Obtener referencias a los TextInputLayout
        val tilFechaida = view.findViewById<TextInputEditText>(R.id.TITfechaida)
        val tilFecharegreso = view.findViewById<TextInputEditText>(R.id.TITfecharegreso)

        // Configurar OnClickListener para TILfechaida
        tilFechaida.setOnClickListener {

            tilFechaida.textSize = 14f
            tilFecharegreso.textSize = 14f

            val picker = MaterialDatePicker.Builder.dateRangePicker()
                .setTheme(R.style.ThemeMaterialCalendar)
                .setTitleText("Seleccione las fechas de su viajes")
                .setSelection(Pair(null,null))
                .build()

            picker.show(requireActivity().supportFragmentManager,"TAG")

            picker.addOnPositiveButtonClickListener {
                tilFechaida.setText(convertTimeToDate(it.first))
                tilFecharegreso.setText(convertTimeToDate(it.second))
            }
            picker.addOnNegativeButtonClickListener{
                picker.dismiss()
            }

        }

        // Configurar OnClickListener para TILfecharegreso
        tilFecharegreso.setOnClickListener {


            tilFechaida.textSize = 14f
            tilFecharegreso.textSize = 14f

            val picker = MaterialDatePicker.Builder.dateRangePicker()
                .setTheme(R.style.ThemeMaterialCalendar)
                .setTitleText("Seleccione las fechas de su viajes")
                .setSelection(Pair(null,null))
                .build()

            picker.show(requireActivity().supportFragmentManager,"TAG")

            picker.addOnPositiveButtonClickListener {
                tilFechaida.setText(convertTimeToDate(it.first))
                tilFecharegreso.setText(convertTimeToDate(it.second))
            }
            picker.addOnNegativeButtonClickListener{
                picker.dismiss()
            }
        }


    }

    private fun SaveData(){
        val destino = binding?.TITdestino?.text?.toString()
        val origen = binding?.TITorigen?.text?.toString()
        val fechaida = binding?.TITfechaida?.text?.toString()
        val fecharegreso = binding?.TITfecharegreso?.text?.toString()
        val npasajeros = binding?.TITnpasajeros?.text?.toString()
        val clase = binding?.autoCompleteClase?.text?.toString()
        val equipaje = binding?.autoCompleteMaleta?.text?.toString()

        val vuelo = Vuelo(
            destino = destino,
            origen = origen,
            fechaida = fechaida,
            fecharegreso = fecharegreso,
            equipaje = equipaje,
            npasajeros = npasajeros,
            clase = clase
        )

        databaseReference?.child(getRandomString(2))?.setValue(vuelo)
    }

    private fun getRandomString(length: Int): String {
        val allowedcar = ('A'..'Z')+('a'..'z')+('0'..'9')
        return (1..length)
            .map { allowedcar.random() }.joinToString { "" }
    }

    private fun getData() {
        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Log.e("00000","onDataChange: $snapshot")
                for(ds in snapshot.children){
                    val id=ds.key
                    val destino=ds.child("destino").value.toString()
                    val origen=ds.child("origen").value.toString()
                    val fechaida=ds.child("fechaida").value.toString()
                    val fecharegreso=ds.child("fecharegreso").value.toString()
                    val equipaje=ds.child("equipaje").value.toString()
                    val npasajeros=ds.child("npasajeros").value.toString()
                    val clase=ds.child("clase").value.toString()

                    val vuelo=Vuelo(
                        id =id, destino =destino,
                        origen =origen,
                        fechaida =fechaida,
                        fecharegreso =fecharegreso,
                        equipaje =equipaje, npasajeros = npasajeros, clase = clase)
                    list.add(vuelo)
                }
                Log.e("00000","size:${list.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("00000","onCancelled: ${error.toException()}")
            }
        })
    }

    private fun convertTimeToDate(time: Long): String {
        val utc = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
        utc.timeInMillis=time
        utc.add(Calendar.DAY_OF_MONTH, 1)
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format((utc.time))
    }




}
