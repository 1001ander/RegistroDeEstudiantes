package edu.ucne.RegistroDeEstudiantes.domain.asignaturas.usecase

import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.model.Asignatura
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.repository.AsignaturaRepository
import javax.inject.Inject

class GetAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int): Asignatura? {
        return repository.getAsignatura(id)
    }
}