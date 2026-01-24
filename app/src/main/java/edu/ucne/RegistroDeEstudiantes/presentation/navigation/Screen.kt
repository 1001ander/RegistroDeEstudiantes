package edu.ucne.RegistroDeEstudiantes.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object EstudianteList : Screen()

    @Serializable
    data class EditEstudiante(val estudianteId: Int?) : Screen()

    @Serializable
    data object AsignaturaList : Screen()

    @Serializable
    data class EditAsignatura(val asignaturaId: Int?) : Screen()
}