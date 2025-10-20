package com.example.tpcrud

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var txtDireccion: EditText
    private lateinit var txtPrecio: EditText
    private lateinit var txtDescripcion: EditText
    private lateinit var txtLatitud: EditText
    private lateinit var txtLongitud: EditText
    private lateinit var txtID_zona: EditText
    private lateinit var txtID_agente: EditText
    private lateinit var txtID_tipoimueble: EditText
    private lateinit var txtID_estadopropiedad: EditText
    private lateinit var txtID_ambiente: EditText
    private lateinit var txtgarage: Switch
    private lateinit var txtbalcon: Switch
    private lateinit var txtpatio: Switch
    private lateinit var txtacepta_mascota: Switch
    private lateinit var txtID_Mascota: EditText
    private lateinit var btnAgregar: Button
    private lateinit var btnVerLista: Button

    private var listadepropiedades = ArrayList<Propiedades>()
    private var idCounter = 1

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            @Suppress("DEPRECATION")
            val updatedList = it.data?.getSerializableExtra("listaActualizada") as? ArrayList<Propiedades>
            if (updatedList != null) {
                listadepropiedades = updatedList
                // Actualizar el idCounter para evitar duplicados
                val maxId = updatedList.maxOfOrNull { p -> p.ID_propiedades } ?: 0
                idCounter = maxId + 1
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtDireccion = findViewById(R.id.txtDireccion)
        txtPrecio = findViewById(R.id.txtPrecio)
        txtDescripcion = findViewById(R.id.txtDescripcion)
        txtLatitud = findViewById(R.id.txtLatitud)
        txtLongitud = findViewById(R.id.txtLongitud)
        txtID_zona = findViewById(R.id.txtID_zona)
        txtID_agente = findViewById(R.id.txtID_agente)
        txtID_tipoimueble = findViewById(R.id.txtID_tipoimueble)
        txtID_estadopropiedad = findViewById(R.id.txtID_estadopropiedad)
        txtID_ambiente = findViewById(R.id.txtID_ambiente)
        txtgarage = findViewById(R.id.txtgarage)
        txtbalcon = findViewById(R.id.txtbalcon)
        txtpatio = findViewById(R.id.txtpatio)
        txtacepta_mascota = findViewById(R.id.txtacepta_mascota)
        txtID_Mascota = findViewById(R.id.txtID_Mascota)
        btnAgregar = findViewById(R.id.btnAgregar)
        btnVerLista = findViewById(R.id.btnVerLista)

        btnAgregar.setOnClickListener {
            agregarPropiedad()
        }

        btnVerLista.setOnClickListener {
            val intent = Intent(this, ListaPropiedadesActivity::class.java)
            intent.putExtra("lista", listadepropiedades)
            startForResult.launch(intent)
        }
    }

    private fun agregarPropiedad() {
        val direccion = txtDireccion.text.toString()
        val precio = txtPrecio.text.toString().toDoubleOrNull()
        val descripcion = txtDescripcion.text.toString()
        val latitud = txtLatitud.text.toString()
        val longitud = txtLongitud.text.toString()
        val idZona = txtID_zona.text.toString().toIntOrNull()
        val idAgente = txtID_agente.text.toString().toIntOrNull()
        val idTipoImoble = txtID_tipoimueble.text.toString().toIntOrNull()
        val idEstadoPropiedad = txtID_estadopropiedad.text.toString().toIntOrNull()
        val idAmbiente = txtID_ambiente.text.toString().toIntOrNull()
        val garage = txtgarage.isChecked
        val balcon = txtbalcon.isChecked
        val patio = txtpatio.isChecked
        val aceptaMascota = txtacepta_mascota.isChecked
        val idMascota = txtID_Mascota.text.toString().toIntOrNull()

        if (direccion.isNotEmpty() && precio != null && descripcion.isNotEmpty() && latitud.isNotEmpty() && longitud.isNotEmpty() && idZona != null && idAgente != null && idTipoImoble != null && idEstadoPropiedad != null && idAmbiente != null && idMascota != null) {
            val propiedad = Propiedades(idCounter++, direccion, precio, descripcion, latitud, longitud, idZona, idAgente, idTipoImoble, idEstadoPropiedad, idAmbiente, garage, balcon, patio, aceptaMascota, idMascota)
            listadepropiedades.add(propiedad)
            limpiarCampos()
            Toast.makeText(this, "Propiedad agregada", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        txtDireccion.text.clear()
        txtPrecio.text.clear()
        txtDescripcion.text.clear()
        txtLatitud.text.clear()
        txtLongitud.text.clear()
        txtID_zona.text.clear()
        txtID_agente.text.clear()
        txtID_tipoimueble.text.clear()
        txtID_estadopropiedad.text.clear()
        txtID_ambiente.text.clear()
        txtgarage.isChecked = false
        txtbalcon.isChecked = false
        txtpatio.isChecked = false
        txtacepta_mascota.isChecked = false
        txtID_Mascota.text.clear()
    }
}
