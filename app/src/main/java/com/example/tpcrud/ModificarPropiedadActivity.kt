package com.example.tpcrud

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ModificarPropiedadActivity : AppCompatActivity() {

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
    private lateinit var btnGuardarCambios: Button
    private var propiedad: Propiedades? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_propiedad)

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
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios)

        @Suppress("DEPRECATION")
        propiedad = intent.getSerializableExtra("propiedad") as? Propiedades

        if (propiedad != null) {
            llenarCampos(propiedad!!)
        }

        btnGuardarCambios.setOnClickListener {
            guardarCambios()
        }
    }

    private fun llenarCampos(propiedad: Propiedades) {
        txtDireccion.setText(propiedad.direccion)
        txtPrecio.setText(propiedad.precio.toString())
        txtDescripcion.setText(propiedad.descripcion)
        txtLatitud.setText(propiedad.latitud)
        txtLongitud.setText(propiedad.longitud)
        txtID_zona.setText(propiedad.ID_zona.toString())
        txtID_agente.setText(propiedad.ID_agente.toString())
        txtID_tipoimueble.setText(propiedad.ID_tipoinmueble.toString())
        txtID_estadopropiedad.setText(propiedad.ID_estadopropiedad.toString())
        txtID_ambiente.setText(propiedad.ID_ambiente.toString())
        txtgarage.isChecked = propiedad.garage
        txtbalcon.isChecked = propiedad.balcon
        txtpatio.isChecked = propiedad.patio
        txtacepta_mascota.isChecked = propiedad.acepta_mascota
        txtID_Mascota.setText(propiedad.ID_mascota.toString())
    }

    private fun guardarCambios() {
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
            val propiedadModificada = Propiedades(propiedad!!.ID_propiedades, direccion, precio, descripcion, latitud, longitud, idZona, idAgente, idTipoImoble, idEstadoPropiedad, idAmbiente, garage, balcon, patio, aceptaMascota, idMascota)
            val resultIntent = Intent()
            resultIntent.putExtra("propiedadModificada", propiedadModificada)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
