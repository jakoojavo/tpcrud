package com.example.tpcrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

/**
 * MainActivity: La pantalla principal de la aplicación.
 * Su responsabilidad es permitir al usuario introducir los datos de una nueva propiedad y guardarla.
 */
class MainActivity : AppCompatActivity() {

    // --- Referencias a las vistas de la UI ---
    // Se declaran como 'private lateinit var' porque se inicializarán en el método onCreate.
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

    // Referencia al ViewModel.
    private lateinit var propiedadesViewModel: PropiedadesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // --- Inicialización del ViewModel ---
        // Se obtiene una instancia del PropiedadesViewModel. El sistema se encarga de decidir si
        // crea una nueva instancia o proporciona una existente (en caso de rotación de pantalla, por ejemplo).
        propiedadesViewModel = ViewModelProvider(this).get(PropiedadesViewModel::class.java)

        // --- Inicialización de las Vistas ---
        // Se enlazan las variables con sus respectivas vistas definidas en el archivo XML (activity_main.xml).
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

        // --- Configuración de los Listeners de los Botones ---

        // Define la acción a realizar cuando se hace clic en el botón "Agregar Propiedad".
        btnAgregar.setOnClickListener {
            agregarPropiedad()
        }

        // Define la acción para el botón "Ver Lista".
        btnVerLista.setOnClickListener {
            // Crea un Intent para abrir la pantalla de la lista (ListaPropiedadesActivity).
            val intent = Intent(this, ListaPropiedadesActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Recoge los datos de los campos de texto y switches, los valida y crea un nuevo objeto Propiedades.
     * Luego, utiliza el ViewModel para insertar la nueva propiedad en la base de datos.
     */
    private fun agregarPropiedad() {
        // Recoge los valores de todos los campos de entrada.
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

        // Validación simple: comprueba que los campos no estén vacíos.
        if (direccion.isNotEmpty() && precio != null && descripcion.isNotEmpty() && latitud.isNotEmpty() && longitud.isNotEmpty() && idZona != null && idAgente != null && idTipoImoble != null && idEstadoPropiedad != null && idAmbiente != null && idMascota != null) {
            // Si la validación es exitosa, crea un objeto Propiedades.
            // El ID se establece en 0 porque Room se encargará de autogenerarlo.
            val propiedad = Propiedades(0, direccion, precio, descripcion, latitud, longitud, idZona, idAgente, idTipoImoble, idEstadoPropiedad, idAmbiente, garage, balcon, patio, aceptaMascota, idMascota)
            
            // Llama al método insert del ViewModel para guardar la propiedad en la base de datos.
            // Esta operación se ejecutará en un hilo de fondo gracias a las coroutinas.
            propiedadesViewModel.insert(propiedad)
            
            // Limpia los campos para que el usuario pueda agregar otra propiedad.
            limpiarCampos()
            
            // Muestra un mensaje de confirmación al usuario.
            Toast.makeText(this, "Propiedad agregada a la base de datos", Toast.LENGTH_SHORT).show()
        } else {
            // Si la validación falla, muestra un mensaje de error.
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Restablece todos los campos del formulario a su estado inicial.
     * Se llama después de agregar una propiedad con éxito.
     */
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
