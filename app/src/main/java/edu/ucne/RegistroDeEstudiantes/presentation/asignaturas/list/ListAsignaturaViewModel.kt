package edu.ucne.RegistroDeEstudiantes.presentation.asignaturas.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.usecase.ObserveAsignaturasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ListAsignaturaViewModel @Inject constructor(
    observeAsignaturasUseCase: ObserveAsignaturasUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListAsignaturaUiState())
    val state: StateFlow<ListAsignaturaUiState> = _state.asStateFlow()

    init {
        observeAsignaturasUseCase()
            .onEach { asignaturas ->
                _state.update {
                    it.copy(asignaturas = asignaturas, isLoading = false)
                }
            }
            .launchIn(viewModelScope)
    }
}

