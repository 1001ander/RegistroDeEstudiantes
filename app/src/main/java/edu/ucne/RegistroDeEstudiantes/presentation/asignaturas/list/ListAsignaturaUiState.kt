package edu.ucne.RegistroDeEstudiantes.presentation.asignaturas.list

import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.model.Asignatura

data class ListAsignaturaUiState(
    val asignaturas: List<Asignatura> = emptyList(),
    val isLoading: Boolean = false
)

