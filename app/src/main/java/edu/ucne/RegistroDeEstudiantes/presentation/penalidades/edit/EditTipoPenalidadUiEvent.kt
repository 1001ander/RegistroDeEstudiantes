package edu.ucne.RegistroDeEstudiantes.presentation.penalidades.edit

sealed interface EditTipoPenalidadUiEvent {
    data class NombreChanged(val value: String) : EditTipoPenalidadUiEvent
    data class DescripcionChanged(val value: String) : EditTipoPenalidadUiEvent
    data class PuntosDescuentoChanged(val value: String) : EditTipoPenalidadUiEvent
    data object Save : EditTipoPenalidadUiEvent
    data object Delete : EditTipoPenalidadUiEvent
}