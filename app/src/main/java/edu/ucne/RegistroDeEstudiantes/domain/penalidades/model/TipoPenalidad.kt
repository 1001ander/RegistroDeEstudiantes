package edu.ucne.RegistroDeEstudiantes.domain.penalidades.model

data class TipoPenalidad(
    val tipoId: Int = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val puntosDescuento: Int = 0
)