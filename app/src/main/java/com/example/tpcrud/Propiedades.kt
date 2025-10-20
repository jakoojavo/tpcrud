package com.example.tpcrud

import java.io.Serializable

data class Propiedades(
    val ID_propiedades: Int,
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
