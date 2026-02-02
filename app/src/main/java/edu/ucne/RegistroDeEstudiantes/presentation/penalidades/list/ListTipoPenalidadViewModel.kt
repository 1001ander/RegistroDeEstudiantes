package edu.ucne.RegistroDeEstudiantes.presentation.penalidades.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.usecase.DeleteTipoPenalidadUseCase
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.usecase.ObserveTiposPenalidadesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTipoPenalidadViewModel @Inject constructor(
    observeTiposPenalidadesUseCase: ObserveTiposPenalidadesUseCase,
    private val deleteTipoPenalidadUseCase: DeleteTipoPenalidadUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        ListTipoPenalidadUiState(isLoading = true)
    )
    val state: StateFlow<ListTipoPenalidadUiState> = _state.asStateFlow()

    init {
        observeTiposPenalidadesUseCase()
            .onEach { tiposPenalidades ->
                _state.update {
                    it.copy(
                        tiposPenalidades = tiposPenalidades,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun deleteTipoPenalidad(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true, errorMessage = null) }

            try {
                deleteTipoPenalidadUseCase(id)
                _state.update { it.copy(isDeleting = false) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isDeleting = false,
                        errorMessage = "Error al eliminar: ${e.message ?: "Error desconocido"}"
                    )
                }
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }
}