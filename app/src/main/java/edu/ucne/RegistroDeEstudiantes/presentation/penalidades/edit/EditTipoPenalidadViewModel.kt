package edu.ucne.RegistroDeEstudiantes.presentation.penalidades.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.model.TipoPenalidad
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.usecase.DeleteTipoPenalidadUseCase
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.usecase.GetTipoPenalidadUseCase
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.usecase.UpsertTipoPenalidadUseCase
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.usecase.validateDescripcion
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.usecase.validateNombre
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.usecase.validatePuntosDescuento
import edu.ucne.RegistroDeEstudiantes.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTipoPenalidadViewModel @Inject constructor(
    private val getTipoPenalidadUseCase: GetTipoPenalidadUseCase,
    private val upsertTipoPenalidadUseCase: UpsertTipoPenalidadUseCase,
    private val deleteTipoPenalidadUseCase: DeleteTipoPenalidadUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(EditTipoPenalidadUiState())
    val state: StateFlow<EditTipoPenalidadUiState> = _state.asStateFlow()

    init {
        val args = savedStateHandle.toRoute<Screen.EditTipoPenalidad>()
        val tipoId = args.tipoId

        if (tipoId != null && tipoId > 0) {
            loadTipoPenalidad(tipoId)
        } else {
            _state.update { it.copy(isNew = true) }
        }
    }

    private fun loadTipoPenalidad(id: Int) {
        viewModelScope.launch {
            val tipoPenalidad = getTipoPenalidadUseCase(id)
            if (tipoPenalidad != null) {
                _state.update {
                    it.copy(
                        tipoId = tipoPenalidad.tipoId,
                        nombre = tipoPenalidad.nombre,
                        descripcion = tipoPenalidad.descripcion,
                        puntosDescuento = tipoPenalidad.puntosDescuento.toString(),
                        isNew = false
                    )
                }
            }
        }
    }

    fun onEvent(event: EditTipoPenalidadUiEvent) {
        when (event) {
            is EditTipoPenalidadUiEvent.NombreChanged -> _state.update {
                it.copy(nombre = event.value, nombreError = null)
            }

            is EditTipoPenalidadUiEvent.DescripcionChanged -> _state.update {
                it.copy(descripcion = event.value, descripcionError = null)
            }

            is EditTipoPenalidadUiEvent.PuntosDescuentoChanged -> _state.update {
                it.copy(puntosDescuento = event.value, puntosDescuentoError = null)
            }

            EditTipoPenalidadUiEvent.Save -> onSave()
            EditTipoPenalidadUiEvent.Delete -> onDelete()
        }
    }

    private fun onSave() {
        val nombre = state.value.nombre
        val descripcion = state.value.descripcion
        val puntosDescuento = state.value.puntosDescuento

        val nombreValidation = validateNombre(nombre)
        val descripcionValidation = validateDescripcion(descripcion)
        val puntosValidation = validatePuntosDescuento(puntosDescuento)

        if (!nombreValidation.isValid || !descripcionValidation.isValid || !puntosValidation.isValid) {
            _state.update {
                it.copy(
                    nombreError = nombreValidation.error,
                    descripcionError = descripcionValidation.error,
                    puntosDescuentoError = puntosValidation.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val id = state.value.tipoId ?: 0
            val tipoPenalidad = TipoPenalidad(
                tipoId = id,
                nombre = nombre,
                descripcion = descripcion,
                puntosDescuento = puntosDescuento.toInt()
            )

            val result = upsertTipoPenalidadUseCase(tipoPenalidad)
            result.onSuccess { newId ->
                _state.update {
                    it.copy(
                        isSaving = false,
                        isSaved = true,
                        tipoId = newId
                    )
                }
            }.onFailure { e ->
                val errorMessage = e.message ?: "Error al guardar el tipo de penalidad"

                if (errorMessage.contains("Ya existe un tipo de penalidad", ignoreCase = true)) {
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
        val id = state.value.tipoId ?: return

        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }

            try {
                deleteTipoPenalidadUseCase(id)
                _state.update {
                    it.copy(isDeleting = false, deleted = true)
                }
            } catch (e: Exception) {
                _state.update { it.copy(isDeleting = false) }
            }
        }
    }
}