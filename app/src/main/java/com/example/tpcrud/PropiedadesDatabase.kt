package com.example.tpcrud

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Esta es la clase principal de la base de datos para la aplicación.
 *
 * Anotaciones de Room:
 * @Database: Marca esta clase como una base de datos de Room.
 *   'entities': Un array de todas las clases que están marcadas con @Entity. Aquí le decimos a Room
 *     qué tablas debe crear.
 *   'version': El número de versión de la base de datos. Si cambias el esquema de la base de datos
 *     (por ejemplo, añades una columna), debes incrementar este número.
 *   'exportSchema': Se recomienda mantenerlo en 'false' para proyectos sencillos para no exportar
 *     el esquema de la base de datos a un archivo.
 *
 * 'abstract class': La clase de la base de datos debe ser abstracta y heredar de RoomDatabase.
 *
 * Patrón Singleton:
 * El objetivo es asegurar que solo exista UNA instancia de la base de datos en toda la aplicación.
 * Esto se logra con el bloque 'companion object'.
 * 'INSTANCE': Esta variable estática mantendrá la única instancia de la base de datos.
 * '@Volatile': Asegura que el valor de INSTANCE sea siempre el más actualizado y visible para todos
 *   los hilos de ejecución.
 * 'synchronized(this)': Este bloque asegura que solo un hilo a la vez pueda ejecutar el código que
 *   está dentro, evitando que se creen dos instancias de la base de datos al mismo tiempo si dos hilos
 *   lo solicitan simultáneamente.
 */
@Database(entities = [Propiedades::class], version = 1, exportSchema = false)
abstract class PropiedadesDatabase : RoomDatabase() {

    // Room implementará esta función abstracta para devolvernos una instancia del DAO.
    abstract fun propiedadesDao(): PropiedadesDao

    // El companion object permite acceder a los métodos sin necesidad de crear una instancia de la clase.
    companion object {
        // La instancia de la base de datos.
        @Volatile
        private var INSTANCE: PropiedadesDatabase? = null

        // Este método obtiene la instancia de la base de datos (o la crea si no existe).
        fun getDatabase(context: Context): PropiedadesDatabase {
            // Si la instancia ya existe, la devuelve. Si no, entra al bloque sincronizado para crearla.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, // El contexto de la aplicación.
                    PropiedadesDatabase::class.java, // La clase de la base de datos.
                    "propiedades_database" // El nombre del archivo de la base de datos.
                ).build()
                INSTANCE = instance
                // Devuelve la instancia recién creada.
                instance
            }
        }
    }
}
