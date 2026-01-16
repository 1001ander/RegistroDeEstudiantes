package edu.ucne.RegistroDeEstudiantes.presentation.students.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import edu.ucne.RegistroDeEstudiantes.domain.students.usecase.GetEstudianteUseCase  // AGREGAR
import edu.ucne.RegistroDeEstudiantes.domain.students.usecase.DeleteEstudianteUseCase  // AGREGAR


@HiltViewModel
class ListEstudianteViewModel @Inject constructor(
    private val getEstudianteUseCase: GetEstudianteUseCase,
    private val deleteEstudianteUseCase: DeleteEstudianteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListEstudianteUiState())
    val state: StateFlow<ListEstudianteUiState> = _state.asStateFlow()

    init {
        onEvent(ListEstudianteUiEvent.LoadEstudiantes)
    }

    fun onEvent(event: ListEstudianteUiEvent) {
        when (event) {
            ListEstudianteUiEvent.LoadEstudiantes -> loadEstudiantes()

            is ListEstudianteUiEvent.SearchQueryChanged -> {
                _state.update { it.copy(searchQuery = event.query) }
                loadEstudiantes()
            }

            is ListEstudianteUiEvent.DeleteEstudiante -> deleteEstudiante(event.id)

            is ListEstudianteUiEvent.NavigateToEdit -> {

            }
        }
    }

    private fun loadEstudiantes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getEstudianteUseCase().collect { estudiantes ->
                val filteredEstudiantes = if (state.value.searchQuery.isBlank()) {
                    estudiantes
                } else {
                    estudiantes.filter { estudiante ->
                        estudiante.nombres.contains(state.value.searchQuery, ignoreCase = true) ||
                                estudiante.email.contains(state.value.searchQuery, ignoreCase = true)
                    }
                }

                _state.update {
                    it.copy(
                        estudiantes = filteredEstudiantes,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun deleteEstudiante(id: Int) {
        viewModelScope.launch {
            deleteEstudianteUseCase(id).onSuccess {
                loadEstudiantes()
            }
        }
    }
}