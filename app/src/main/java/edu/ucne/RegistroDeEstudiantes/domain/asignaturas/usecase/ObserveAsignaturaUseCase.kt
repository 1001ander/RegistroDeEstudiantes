package edu.ucne.RegistroDeEstudiantes.domain.asignaturas.usecase

import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.model.Asignatura
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAsignaturasUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    operator fun invoke(): Flow<List<Asignatura>> {
        return repository.observeAsignaturas()
    }
}

