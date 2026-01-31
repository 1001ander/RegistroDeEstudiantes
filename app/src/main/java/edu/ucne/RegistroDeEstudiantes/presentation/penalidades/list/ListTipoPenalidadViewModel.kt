package edu.ucne.RegistroDeEstudiantes.presentation.penalidades.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.usecase.ObserveTiposPenalidadesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ListTipoPenalidadViewModel @Inject constructor(
    observeTiposPenalidadesUseCase: ObserveTiposPenalidadesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListTipoPenalidadUiState())
    val state: StateFlow<ListTipoPenalidadUiState> = _state.asStateFlow()

    init {
        observeTiposPenalidadesUseCase()
            .onEach { tiposPenalidades ->
                _state.update {
                    it.copy(tiposPenalidades = tiposPenalidades, isLoading = false)
                }
            }
            .launchIn(viewModelScope)
    }
}