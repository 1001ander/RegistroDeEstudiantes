package edu.ucne.RegistroDeEstudiantes.domain.asignaturas.usecase

import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.repository.AsignaturaRepository
import javax.inject.Inject

class DeleteAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.delete(id)
    }
}