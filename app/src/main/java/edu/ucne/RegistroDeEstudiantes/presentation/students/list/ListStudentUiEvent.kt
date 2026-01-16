package edu.ucne.RegistroDeEstudiantes.presentation.students.list

sealed interface ListEstudianteUiEvent {
    data object LoadEstudiantes : ListEstudianteUiEvent
    data class SearchQueryChanged(val query: String) : ListEstudianteUiEvent
    data class DeleteEstudiante(val id: Int) : ListEstudianteUiEvent
    data class NavigateToEdit(val id: Int?) : ListEstudianteUiEvent
}