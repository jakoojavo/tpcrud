package com.example.tpcrud

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * El ViewModel es un componente de la arquitectura de Android Jetpack que actúa como un proveedor
 * de datos para la UI y sobrevive a cambios de configuración (como rotar la pantalla).
 *
 * Beneficios de usar un ViewModel:
 * 1. Separa la lógica de la UI de la Activity/Fragment. La Activity solo se encarga de MOSTRAR
 *    los datos, mientras que el ViewModel los PREPARA y GESTIONA.
 * 2. Persistencia de datos: Como sobrevive a los cambios de configuración, no tienes que volver a
 *    cargar los datos desde la base de datos o la red cada vez que el usuario gira el teléfono.
 * 3. Evita fugas de memoria (memory leaks) al ser consciente del ciclo de vida.
 *
 * Se usa `AndroidViewModel` en lugar de `ViewModel` porque necesitamos el contexto de la aplicación
 * para poder instanciar la base de datos.
 */
class PropiedadesViewModel(application: Application) : AndroidViewModel(application) {

    // Referencia al Repositorio.
    private val repository: PropiedadesRepository
    // LiveData que la UI observará. Contiene la lista de todas las propiedades.
    val allPropiedades: LiveData<List<Propiedades>>

    // El bloque init se ejecuta cuando se crea una instancia del ViewModel.
    init {
        // Obtenemos una instancia del DAO a través de nuestra clase de Base de Datos.
        val propiedadesDao = PropiedadesDatabase.getDatabase(application).propiedadesDao()
        // Creamos una instancia del Repositorio, pasándole el DAO.
        repository = PropiedadesRepository(propiedadesDao)
        // Obtenemos el LiveData con todas las propiedades desde el repositorio.
        allPropiedades = repository.allPropiedades
    }

    /**
      Lanza una nueva coroutina para insertar los datos de forma no bloqueante.
     `viewModelScope` es un CoroutineScope predefinido que se cancela automáticamente cuando el ViewModel se destruye.
      `Dispatchers.IO` indica que esta operación debe ejecutarse en un hilo optimizado para operaciones de entrada/salida (I/O), como las de base de datos.
     */
    fun insert(propiedad: Propiedades) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(propiedad)
    }

    /**
     * Lanza una nueva coroutina para actualizar los datos.
     */
    fun update(propiedad: Propiedades) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(propiedad)
    }

    /**
     * Lanza una nueva coroutina para borrar los datos.
     */
    fun delete(propiedad: Propiedades) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(propiedad)
    }
}
