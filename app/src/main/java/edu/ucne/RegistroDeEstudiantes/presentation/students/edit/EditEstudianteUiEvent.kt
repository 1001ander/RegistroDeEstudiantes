package edu.ucne.RegistroDeEstudiantes.presentation.students.edit

sealed interface EditEstudianteUiEvent {
    data class LoadEstudiante(val id: Int?) : EditEstudianteUiEvent
    data class NombresChanged(val value: String) : EditEstudianteUiEvent
    data class EmailChanged(val value: String) : EditEstudianteUiEvent
    data class EdadChanged(val value: String) : EditEstudianteUiEvent
    data object Save : EditEstudianteUiEvent
    data object Delete : EditEstudianteUiEvent
}