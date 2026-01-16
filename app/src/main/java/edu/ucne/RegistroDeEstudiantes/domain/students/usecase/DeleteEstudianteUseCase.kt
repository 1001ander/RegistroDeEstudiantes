package edu.ucne.RegistroDeEstudiantes.domain.students.usecase

import edu.ucne.RegistroDeEstudiantes.domain.students.repository.EstudianteRepository
import javax.inject.Inject

class DeleteEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int): Result<Unit> =
        try {
            repository.delete(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
}