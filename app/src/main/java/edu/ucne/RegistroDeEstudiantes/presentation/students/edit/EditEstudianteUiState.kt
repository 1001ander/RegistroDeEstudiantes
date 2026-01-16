package edu.ucne.RegistroDeEstudiantes.presentation.students.edit

data class EditEstudianteUiState(
    val estudianteId: Int? = null,
    val nombres: String = "",
    val email: String = "",
    val edad: String = "",
    val nombresError: String? = null,
    val emailError: String? = null,
    val edadError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val isSaved: Boolean = false,
    val deleted: Boolean = false
)