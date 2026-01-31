package edu.ucne.RegistroDeEstudiantes.presentation.penalidades.list

sealed interface ListTipoPenalidadUiEvent {
    data class SelectTipoPenalidad(val tipoId: Int) : ListTipoPenalidadUiEvent
    data object AddTipoPenalidad : ListTipoPenalidadUiEvent
}