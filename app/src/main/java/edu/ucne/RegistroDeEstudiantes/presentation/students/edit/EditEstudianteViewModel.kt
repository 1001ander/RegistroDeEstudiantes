package edu.ucne.RegistroDeEstudiantes.presentation.students.edit

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroDeEstudiantes.domain.students.model.Estudiante
import edu.ucne.RegistroDeEstudiantes.domain.students.usecase.DeleteEstudianteUseCase
import edu.ucne.RegistroDeEstudiantes.domain.students.usecase.GetEstudianteUseCase
import edu.ucne.RegistroDeEstudiantes.domain.students.usecase.UpsertEstudianteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEstudianteViewModel @Inject constructor(
    private val getEstudianteUseCase: GetEstudianteUseCase,
    private val upsertEstudianteUseCase: UpsertEstudianteUseCase,
    private val deleteEstudianteUseCase: DeleteEstudianteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditEstudianteUiState())
    val state: StateFlow<EditEstudianteUiState> = _state.asStateFlow()

    fun onEvent(event: EditEstudianteUiEvent) {
        when (event) {
            is EditEstudianteUiEvent.LoadEstudiante -> onLoad(id = event.id)

            is EditEstudianteUiEvent.NombresChanged -> _state.update {
                it.copy(nombres = event.value, nombresError = null)
            }

            is EditEstudianteUiEvent.EmailChanged -> _state.update {
                it.copy(email = event.value, emailError = null)
            }

            is EditEstudianteUiEvent.EdadChanged -> _state.update {
                it.copy(edad = event.value, edadError = null)
            }

            EditEstudianteUiEvent.Save -> onSave()
            EditEstudianteUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null) {
            _state.update { it.copy(isNew = true) }
            return
        }

        viewModelScope.launch {
            val estudiante = getEstudianteUseCase(id)
            if (estudiante != null) {
                _state.update {
                    it.copy(
                        estudianteId = estudiante.estudianteId,
                        nombres = estudiante.nombres,
                        email = estudiante.email,
                        edad = estudiante.edad.toString(),
                        isNew = false
                    )
                }
            }
        }
    }

    private fun onSave() {
        val nombres = state.value.nombres
        val email = state.value.email
        val edad = state.value.edad

        val nombresValidation = validateNombres(value = nombres)
        val emailValidation = validateEmail(value = email)
        val edadValidation = validateEdad(value = edad)

        if (!nombresValidation.isValid || !emailValidation.isValid || !edadValidation.isValid) {
            _state.update {
                it.copy(
                    nombresError = nombresValidation.error,
                    emailError = emailValidation.error,
                    edadError = edadValidation.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val id = state.value.estudianteId ?: 0
            val estudiante = Estudiante(
                estudianteId = id,
                nombres = nombres,
                email = email,
                edad = edad.toInt()
            )

            val result = upsertEstudianteUseCase(estudiante)
            result.onSuccess { newId ->
                _state.update {
                    it.copy(
                        isSaving = false,
                        isSaved = true,
                        estudianteId = newId
                    )
                }
            }.onFailure { e ->
                _state.update { it.copy(isSaving = false) }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.estudianteId ?: return

        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }

            try {
                deleteEstudianteUseCase(id)
                _state.update {
                    it.copy(isDeleting = false, deleted = true)
                }
            } catch (e: Exception) {
                _state.update { it.copy(isDeleting = false) }
            }
        }
    }

    private fun validateNombres(value: String): ValidationResult {
        if (value.isBlank()) {
            return ValidationResult(isValid = false, error = "Los nombres son requeridos")
        }
        if (value.length < 3) {
            return ValidationResult(isValid = false, error = "Los nombres deben tener al menos 3 caracteres")
        }
        return ValidationResult(isValid = true)
    }

    private fun validateEmail(value: String): ValidationResult {
        if (value.isBlank()) {
            return ValidationResult(isValid = false, error = "El email es requerido")
        }
        val emailPattern = Patterns.EMAIL_ADDRESS
        if (!emailPattern.matcher(value).matches()) {
            return ValidationResult(isValid = false, error = "Email inválido")
        }
        return ValidationResult(isValid = true)
    }

    private fun validateEdad(value: String): ValidationResult {
        if (value.isBlank()) {
            return ValidationResult(isValid = false, error = "La edad es requerida")
        }
        val edad = value.toIntOrNull()
        if (edad == null) {
            return ValidationResult(isValid = false, error = "La edad debe ser un número")
        }
        if (edad < 1 || edad > 120) {
            return ValidationResult(isValid = false, error = "La edad debe estar entre 1 y 120")
        }
        return ValidationResult(isValid = true)
    }
}

data class ValidationResult(
    val isValid: Boolean,
    val error: String? = null
)