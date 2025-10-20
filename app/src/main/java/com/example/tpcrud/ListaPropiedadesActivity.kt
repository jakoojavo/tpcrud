package com.example.tpcrud

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ListaPropiedadesActivity : AppCompatActivity() {

    private lateinit var listViewPropiedades: ListView
    private lateinit var btnVolverAgregar: Button
    private lateinit var btnModificar: Button
    private lateinit var btnBorrar: Button
    private var lista: ArrayList<Propiedades>? = null
    private lateinit var adapter: ArrayAdapter<String>
    private var selectedItemPosition: Int = -1

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            @Suppress("DEPRECATION")
            val modifiedProperty = it.data?.getSerializableExtra("propiedadModificada") as? Propiedades
            if (modifiedProperty != null && selectedItemPosition != -1) {
                lista?.set(selectedItemPosition, modifiedProperty)
                actualizarAdapter()
                selectedItemPosition = -1 // Reset selection
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_propiedades)

        listViewPropiedades = findViewById(R.id.listViewPropiedades)
        btnVolverAgregar = findViewById(R.id.btnVolverAgregar)
        btnModificar = findViewById(R.id.btnModificar)
        btnBorrar = findViewById(R.id.btnBorrar)

        @Suppress("DEPRECATION")
        lista = intent.getSerializableExtra("lista") as? ArrayList<Propiedades>

        if (lista != null) {
            actualizarAdapter()
        }

        listViewPropiedades.setOnItemClickListener { parent, view, position, id ->
            selectedItemPosition = position
            for (i in 0 until parent.childCount) {
                parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT)
            }
            view.setBackgroundColor(Color.LTGRAY)
        }

        btnVolverAgregar.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("listaActualizada", lista)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        btnBorrar.setOnClickListener {
            if (selectedItemPosition != -1) {
                lista?.removeAt(selectedItemPosition)
                actualizarAdapter()
                selectedItemPosition = -1 // Reset selection
                Toast.makeText(this, "Propiedad borrada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Seleccione una propiedad para borrar", Toast.LENGTH_SHORT).show()
            }
        }

        btnModificar.setOnClickListener {
             if (selectedItemPosition == -1) {
                Toast.makeText(this, "Por favor, seleccione una propiedad para modificar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, ModificarPropiedadActivity::class.java)
            intent.putExtra("propiedad", lista?.get(selectedItemPosition))
            startForResult.launch(intent)
        }
    }

    private fun actualizarAdapter() {
        if (lista != null) {
            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista!!.map { 
                "ID: ${it.ID_propiedades}\n "+
                "Dirección: ${it.direccion}\n" +
                "Precio: $${it.precio}\n" +
                "Descripción: ${it.descripcion}\n" +
                "Latitud: ${it.latitud}\n" +
                "Longitud: ${it.longitud}\n" +
                "ID Zona: ${it.ID_zona}\n" +
                "ID Agente: ${it.ID_agente}\n" +
                "ID Tipo Inmueble: ${it.ID_tipoinmueble}\n" +
                "ID Estado Propiedad: ${it.ID_estadopropiedad}\n" +
                "ID Ambiente: ${it.ID_ambiente}\n" +
                "Garage: ${if (it.garage) "si" else "no"}\n" +
                "Balcón: ${if (it.balcon) "si" else "no"}\n" +
                "Patio: ${if (it.patio) "si" else "no"}\n" +
                "Acepta Mascota: ${if (it.acepta_mascota) "si" else "no"}\n" +
                "ID Mascota: ${it.ID_mascota}"
            })
            listViewPropiedades.adapter = adapter
        }
    }
}
