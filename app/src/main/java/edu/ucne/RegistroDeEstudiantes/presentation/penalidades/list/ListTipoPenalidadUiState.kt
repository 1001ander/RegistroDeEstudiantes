package edu.ucne.RegistroDeEstudiantes.presentation.penalidades.list

import edu.ucne.RegistroDeEstudiantes.domain.penalidades.model.TipoPenalidad

data class ListTipoPenalidadUiState(
    val tiposPenalidades: List<TipoPenalidad> = emptyList(),
    val isLoading: Boolean = false,
    val isDeleting: Boolean = false,
    val errorMessage: String? = null
)