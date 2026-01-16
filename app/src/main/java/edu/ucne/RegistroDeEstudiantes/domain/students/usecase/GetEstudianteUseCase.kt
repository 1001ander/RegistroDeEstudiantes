package edu.ucne.RegistroDeEstudiantes.domain.students.usecase

import edu.ucne.RegistroDeEstudiantes.domain.students.model.Estudiante
import edu.ucne.RegistroDeEstudiantes.domain.students.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int): Estudiante? =
        repository.getEstudiante(id)

    operator fun invoke(): Flow<List<Estudiante>> =
        repository.observeEstudiantes()
}