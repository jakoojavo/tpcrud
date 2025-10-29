package com.example.tpcrud

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * DAO (Data Access Object) - Objeto de Acceso a Datos.
 * Esta interfaz define todas las operaciones que podemos realizar sobre la tabla 'propiedades_table'.
 * Room se encargará de generar automáticamente el código necesario para implementar estos métodos.
 *
 * Anotaciones de Room:
 * @Dao: Marca esta interfaz como un DAO para Room.
 * @Insert: Marca un método para insertar datos. 'onConflict' define qué hacer si intentamos insertar una
 *   propiedad que ya existe (en este caso, la ignoramos).
 * @Update: Marca un método para actualizar un dato existente.
 * @Delete: Marca un método para borrar un dato.
 * @Query: Permite realizar consultas personalizadas a la base de datos usando SQL.
 *
 * 'suspend': Esta palabra clave indica que la función es una 'función de suspensión' de Kotlin Coroutines.
 *   Esto significa que la operación puede ser pausada y reanudada, y debe ser llamada desde otra
 *   función 'suspend' o desde un 'coroutine scope'. Room usa esto para asegurar que las operaciones
 *   de base de datos se ejecuten en un hilo de fondo, evitando bloquear la interfaz de usuario.
 *
 * LiveData: Es un contenedor de datos observable y consciente del ciclo de vida. Al devolver
 *   'LiveData<List<Propiedades>>', Room se asegura de que la lista de propiedades se actualice
 *   automáticamente en la UI cada vez que haya un cambio en la base de datos.
 */
@Dao
interface PropiedadesDao {

    // Inserta una nueva propiedad en la tabla.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(propiedad: Propiedades)

    // Actualiza una propiedad existente.
    @Update
    suspend fun update(propiedad: Propiedades)

    // Borra una propiedad de la tabla.
    @Delete
    suspend fun delete(propiedad: Propiedades)

    // Obtiene todas las propiedades de la tabla, ordenadas por su ID en orden ascendente.
    // Devuelve un LiveData, lo que permite que la UI reaccione a los cambios en la base de datos.
    @Query("SELECT * from propiedades_table ORDER BY ID_propiedades ASC")
    fun getAllPropiedades(): LiveData<List<Propiedades>>
}
