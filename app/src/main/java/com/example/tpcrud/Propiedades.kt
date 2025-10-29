package com.example.tpcrud

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Esta es la clase de datos que define la estructura de nuestra tabla en la base de datos.
 * Cada instancia de esta clase representa una fila en la tabla 'propiedades_table'.
 *
 * Anotaciones de Room:
 * @Entity(tableName = "propiedades_table"): Marca esta clase como una tabla de la base de datos.
 *   'tableName' es el nombre que le damos a nuestra tabla.
 * @PrimaryKey(autoGenerate = true): Designa el campo 'ID_propiedades' como la clave primaria de la tabla.
 *   'autoGenerate = true' le dice a Room que genere automáticamente un valor único para esta columna
 *   cada vez que se inserta una nueva propiedad. Esto es útil porque nos libera de tener que manejar los IDs manualmente.
 *
 * Implementación de Serializable:
 * 'Serializable' permite que los objetos de esta clase se conviertan en una secuencia de bytes.
 * Esto es crucial para poder pasar objetos 'Propiedades' entre diferentes Activities (por ejemplo,
 * desde ListaPropiedadesActivity a ModificarPropiedadActivity) a través de los 'Intents'.
 */
@Entity(tableName = "propiedades_table")
data class Propiedades(
    @PrimaryKey(autoGenerate = true)
    val ID_propiedades: Int = 0, // Room se encargará de generar el ID, por eso el valor por defecto es 0.
    val direccion: String,
    val precio: Double,
    val descripcion: String,
    val latitud: String,
    val longitud: String,
    val ID_zona: Int,
    val ID_agente: Int,
    val ID_tipoinmueble: Int,
    val ID_estadopropiedad: Int,
    val ID_ambiente: Int,
    val garage: Boolean,
    val balcon: Boolean,
    val patio: Boolean,
    val acepta_mascota: Boolean,
    val ID_mascota: Int
) : Serializable
