package com.example.tpcrud

import androidx.lifecycle.LiveData

/**
 * Un Repositorio es una clase que abstrae y centraliza el acceso a los datos desde el resto de la aplicación.
 * Actúa como intermediario entre los ViewModels y las fuentes de datos (en este caso, el DAO de Room).
 *
 * Beneficios de usar un Repositorio:
 * 1. Desacopla la lógica de la UI de la fuente de datos. El ViewModel no sabe (ni le importa)
 *    si los datos vienen de una base de datos, una API web o un archivo local.
 * 2. Facilita las pruebas, ya que puedes proporcionar un "falso" repositorio para probar tus ViewModels.
 * 3. Es el lugar ideal para decidir si obtener datos de la red o de la caché local.
 *
 * @param propiedadesDao El DAO que se utilizará para acceder a la base de datos.
 */
class PropiedadesRepository(private val propiedadesDao: PropiedadesDao) {

    // Expone un LiveData con todas las propiedades. El ViewModel observará este LiveData.
    // El repositorio simplemente obtiene este dato directamente del DAO.
    val allPropiedades: LiveData<List<Propiedades>> = propiedadesDao.getAllPropiedades()

    // Las operaciones de inserción, actualización y borrado son funciones 'suspend' porque deben
    // llamarse desde una coroutina. El ViewModel se encargará de gestionar esto.

    // Inserta una nueva propiedad llamando al método correspondiente del DAO.
    suspend fun insert(propiedad: Propiedades) {
        propiedadesDao.insert(propiedad)
    }

    // Actualiza una propiedad existente llamando al método del DAO.
    suspend fun update(propiedad: Propiedades) {
        propiedadesDao.update(propiedad)
    }

    // Borra una propiedad llamando al método del DAO.
    suspend fun delete(propiedad: Propiedades) {
        propiedadesDao.delete(propiedad)
    }
}
