package edu.ucne.RegistroDeEstudiantes.presentation.asignaturas.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.model.Asignatura
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.usecase.DeleteAsignaturaUseCase
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.usecase.GetAsignaturaUseCase
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.usecase.UpsertAsignaturaUseCase
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.usecase.validateAula
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.usecase.validateCodigo
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.usecase.validateCreditos
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.usecase.validateNombre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditAsignaturaViewModel @Inject constructor(
    private val getAsignaturaUseCase: GetAsignaturaUseCase,
    private val upsertAsignaturaUseCase: UpsertAsignaturaUseCase,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditAsignaturaUiState())
    val state: StateFlow<EditAsignaturaUiState> = _state.asStateFlow()

    fun onEvent(event: EditAsignaturaUiEvent) {
        when (event) {
            is EditAsignaturaUiEvent.LoadAsignatura -> onLoad(id = event.id)

            is EditAsignaturaUiEvent.CodigoChanged -> _state.update {
                it.copy(codigo = event.value, codigoError = null)
            }

            is EditAsignaturaUiEvent.NombreChanged -> _state.update {
                it.copy(nombre = event.value, nombreError = null)
            }

            is EditAsignaturaUiEvent.AulaChanged -> _state.update {
                it.copy(aula = event.value, aulaError = null)
            }

            is EditAsignaturaUiEvent.CreditosChanged -> _state.update {
                it.copy(creditos = event.value, creditosError = null)
            }

            EditAsignaturaUiEvent.Save -> onSave()
            EditAsignaturaUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null) {
            _state.update { it.copy(isNew = true) }
            return
        }

        viewModelScope.launch {
            val asignatura = getAsignaturaUseCase(id)
            if (asignatura != null) {
                _state.update {
                    it.copy(
                        asignaturaId = asignatura.asignaturaId,
                        codigo = asignatura.codigo,
                        nombre = asignatura.nombre,
                        aula = asignatura.aula,
                        creditos = asignatura.creditos.toString(),
                        isNew = false
                    )
                }
            }
        }
    }

    private fun onSave() {
        val codigo = state.value.codigo
        val nombre = state.value.nombre
        val aula = state.value.aula
        val creditos = state.value.creditos

        val codigoValidation = validateCodigo(codigo)
        val nombreValidation = validateNombre(nombre)
        val aulaValidation = validateAula(aula)
        val creditosValidation = validateCreditos(creditos)

        if (!codigoValidation.isValid || !nombreValidation.isValid ||
            !aulaValidation.isValid || !creditosValidation.isValid) {
            _state.update {
                it.copy(
                    codigoError = codigoValidation.error,
                    nombreError = nombreValidation.error,
                    aulaError = aulaValidation.error,
                    creditosError = creditosValidation.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val id = state.value.asignaturaId ?: 0
            val asignatura = Asignatura(
                asignaturaId = id,
                codigo = codigo,
                nombre = nombre,
                aula = aula,
                creditos = creditos.toInt()
            )

            val result = upsertAsignaturaUseCase(asignatura)
            result.onSuccess { newId ->
                _state.update {
                    it.copy(
                        isSaving = false,
                        isSaved = true,
                        asignaturaId = newId
                    )
                }
            }.onFailure { e ->
                val errorMessage = e.message ?: "Error al guardar la asignatura"

                if (errorMessage.contains("Ya existe una asignatura", ignoreCase = true)) {
                    _state.update {
                        it.copy(
                            isSaving = false,
                            nombreError = errorMessage
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isSaving = false,
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.asignaturaId ?: return

        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }

            try {
                deleteAsignaturaUseCase(id)
                _state.update {
                    it.copy(isDeleting = false, deleted = true)
                }
            } catch (e: Exception) {
                _state.update { it.copy(isDeleting = false) }
            }
        }
    }
}

