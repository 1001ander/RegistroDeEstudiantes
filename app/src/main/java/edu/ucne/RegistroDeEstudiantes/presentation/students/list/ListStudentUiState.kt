package edu.ucne.RegistroDeEstudiantes.presentation.students.list
import edu.ucne.RegistroDeEstudiantes.domain.students.model.Estudiante

data class ListEstudianteUiState(
    val estudiantes: List<Estudiante> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)