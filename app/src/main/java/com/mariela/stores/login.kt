package com.mariela.stores

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class login : AppCompatActivity() {
    // Mapa para almacenar usuarios y contraseñas
    private val usersMap = mapOf(
        "diegom" to "12345",
        "juan" to "12345",
        "diego2" to "12345"
        // Agrega más usuarios según sea necesario
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        // Encuentra los elementos de la interfaz por sus IDs
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val etUsername: TextInputEditText = findViewById(R.id.etEmail) // Cambia según tu diseño
        val etPassword: TextInputEditText = findViewById(R.id.etPassword) // Cambia según tu diseño

        // Agrega un OnClickListener al botón
        btnLogin.setOnClickListener {
            // Verifica las credenciales antes de abrir la actividad PaquetesListView
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (validateCredentials(username, password)) {
                // Credenciales válidas, inicia la actividad
                val intent = Intent(this, PaquetesListView::class.java)
                startActivity(intent)
            } else {
                // Credenciales incorrectas, muestra un mensaje de error
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateCredentials(username: String, password: String): Boolean {
        // Verifica si las credenciales son correctas utilizando el mapa de usuarios
        return usersMap[username] == password
    }
}