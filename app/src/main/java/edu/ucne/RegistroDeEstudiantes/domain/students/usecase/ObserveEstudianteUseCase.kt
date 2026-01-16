package edu.ucne.RegistroDeEstudiantes.domain.students.usecase

import edu.ucne.RegistroDeEstudiantes.domain.students.model.Estudiante
import edu.ucne.RegistroDeEstudiantes.domain.students.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow

class ObserveEstudiantesUseCase(
    private val repository: EstudianteRepository
) {
    operator fun invoke(): Flow<List<Estudiante>> =
        repository.observeEstudiantes()
}
