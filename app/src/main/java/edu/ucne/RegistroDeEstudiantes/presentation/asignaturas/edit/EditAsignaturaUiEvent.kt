package edu.ucne.RegistroDeEstudiantes.presentation.asignaturas.edit

sealed interface EditAsignaturaUiEvent {
    data class LoadAsignatura(val id: Int?) : EditAsignaturaUiEvent
    data class CodigoChanged(val value: String) : EditAsignaturaUiEvent
    data class NombreChanged(val value: String) : EditAsignaturaUiEvent
    data class AulaChanged(val value: String) : EditAsignaturaUiEvent
    data class CreditosChanged(val value: String) : EditAsignaturaUiEvent
    data object Save : EditAsignaturaUiEvent
    data object Delete : EditAsignaturaUiEvent
}