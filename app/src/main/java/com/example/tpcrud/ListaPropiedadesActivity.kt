package com.example.tpcrud

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

/**
 * ListaPropiedadesActivity: Muestra la lista de todas las propiedades guardadas.
 * Permite al usuario seleccionar, modificar y borrar propiedades.
 */
class ListaPropiedadesActivity : AppCompatActivity() {

    // --- Referencias a las vistas y al ViewModel ---
    private lateinit var listViewPropiedades: ListView
    private lateinit var btnVolverAgregar: Button
    private lateinit var btnModificar: Button
    private lateinit var btnBorrar: Button
    private lateinit var propiedadesViewModel: PropiedadesViewModel

    // --- Variables para manejar el estado de la lista ---
    // Guardamos una copia local de la lista de propiedades para poder acceder a un elemento por su posición (ej. al hacer clic).
    private var listaDePropiedadesCache = emptyList<Propiedades>()
    private lateinit var adapter: ArrayAdapter<String> // El adaptador que convierte la lista de datos en vistas para el ListView.
    private var selectedItemPosition: Int = -1 // Guarda la posición del elemento seleccionado en la lista. -1 significa que no hay nada seleccionado.

    /**
     * ActivityResultLauncher: Es la forma moderna y recomendada de manejar los resultados de una Activity.
     * Lo usamos para recibir la propiedad modificada desde ModificarPropiedadActivity.
     */
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // Este bloque se ejecuta cuando la actividad que lanzamos (ModificarPropiedadActivity) se cierra.
        if (it.resultCode == Activity.RESULT_OK) {
            @Suppress("DEPRECATION")
            val modifiedProperty = it.data?.getSerializableExtra("propiedadModificada") as? Propiedades
            if (modifiedProperty != null) {
                // Si recibimos una propiedad modificada, le pedimos al ViewModel que la actualice en la base de datos.
                propiedadesViewModel.update(modifiedProperty)
                selectedItemPosition = -1 // Reseteamos la selección.
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_propiedades)

        // --- Inicialización del ViewModel y las Vistas ---
        propiedadesViewModel = ViewModelProvider(this).get(PropiedadesViewModel::class.java)
        listViewPropiedades = findViewById(R.id.listViewPropiedades)
        btnVolverAgregar = findViewById(R.id.btnVolverAgregar)
        btnModificar = findViewById(R.id.btnModificar)
        btnBorrar = findViewById(R.id.btnBorrar)

        // --- Observación del LiveData ---
        // Aquí ocurre la "magia" de la arquitectura moderna de Android.
        // `observe` se suscribe a los cambios en `allPropiedades` del ViewModel.
        // Cada vez que los datos cambian en la base de datos (insert, update, delete), este bloque se ejecuta automáticamente.
        propiedadesViewModel.allPropiedades.observe(this) { propiedades ->
            // Cuando la lista de propiedades cambia, actualizamos nuestra caché local y el adaptador de la lista.
            propiedades?.let {
                this.listaDePropiedadesCache = it
                actualizarAdapter(it)
            }
        }

        // --- Configuración de Listeners ---

        // Define la acción al hacer clic en un elemento del ListView.
        listViewPropiedades.setOnItemClickListener { parent, view, position, id ->
            selectedItemPosition = position // Guardamos la posición del elemento seleccionado.
            // Resaltamos el elemento seleccionado para dar feedback visual al usuario.
            for (i in 0 until parent.childCount) {
                parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT) // Limpiamos el color de fondo de todos los elementos.
            }
            view.setBackgroundColor(Color.LTGRAY) // Coloreamos solo el elemento seleccionado.
        }

        // El botón para volver a la pantalla de agregar.
        btnVolverAgregar.setOnClickListener {
            // Simplemente cierra esta actividad y vuelve a la anterior (MainActivity).
            finish()
        }

        // El botón para borrar una propiedad seleccionada.
        btnBorrar.setOnClickListener {
            if (selectedItemPosition != -1) { // Primero, comprobamos si se ha seleccionado algo.
                // Creamos un diálogo de alerta para confirmar la acción.
                AlertDialog.Builder(this)
                    .setTitle("Confirmar Borrado")
                    .setMessage("¿Estás seguro de que quieres borrar esta propiedad?")
                    .setPositiveButton("Sí") { _, _ ->
                        // Si el usuario confirma, obtenemos la propiedad de nuestra caché local...
                        val propiedadParaBorrar = listaDePropiedadesCache[selectedItemPosition]
                        // ...y le decimos al ViewModel que la borre de la base de datos.
                        propiedadesViewModel.delete(propiedadParaBorrar)
                        selectedItemPosition = -1 // Reseteamos la selección.
                        Toast.makeText(this, "Propiedad borrada", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("No", null) // Si el usuario dice "No", no hacemos nada (null).
                    .show()
            } else {
                Toast.makeText(this, "Seleccione una propiedad para borrar", Toast.LENGTH_SHORT).show()
            }
        }

        // El botón para modificar una propiedad seleccionada.
        btnModificar.setOnClickListener {
             if (selectedItemPosition == -1) { // Comprobamos si se ha seleccionado algo.
                Toast.makeText(this, "Por favor, seleccione una propiedad para modificar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Creamos un Intent para abrir la pantalla de modificación.
            val intent = Intent(this, ModificarPropiedadActivity::class.java)
            // Adjuntamos la propiedad seleccionada al Intent para que la otra actividad pueda recibirla.
            // Usamos la caché local para obtener el objeto completo.
            intent.putExtra("propiedad", listaDePropiedadesCache[selectedItemPosition])
            // Lanzamos la actividad usando el launcher que definimos antes, para poder recibir el resultado.
            startForResult.launch(intent)
        }
    }

    /**
     * Actualiza el adaptador del ListView con una nueva lista de propiedades.
     * Se formatea cada propiedad en un String multilínea para mostrar todos sus detalles.
     */
    private fun actualizarAdapter(lista: List<Propiedades>) {
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista.map { 
            "ID: ${it.ID_propiedades}\n" +
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
