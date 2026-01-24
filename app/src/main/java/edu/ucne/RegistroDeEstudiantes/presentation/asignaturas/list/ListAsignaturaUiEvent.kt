package edu.ucne.RegistroDeEstudiantes.presentation.asignaturas.list

sealed interface ListAsignaturaUiEvent {
    data class SelectAsignatura(val asignaturaId: Int) : ListAsignaturaUiEvent
    data object AddAsignatura : ListAsignaturaUiEvent
}