package edu.ucne.RegistroDeEstudiantes.domain.asignaturas.model

data class Asignatura(
    val asignaturaId: Int = 0,
    val codigo: String = "",
    val nombre: String = "",
    val aula: String = "",
    val creditos: Int = 0
)