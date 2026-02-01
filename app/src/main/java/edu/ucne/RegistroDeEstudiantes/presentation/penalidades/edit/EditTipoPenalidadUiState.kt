package edu.ucne.RegistroDeEstudiantes.presentation.penalidades.edit

data class EditTipoPenalidadUiState(
    val tipoId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val puntosDescuento: String = "",
    val nombreError: String? = null,
    val descripcionError: String? = null,
    val puntosDescuentoError: String? = null,
    val errorMessage: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val isSaved: Boolean = false,
    val deleted: Boolean = false
)