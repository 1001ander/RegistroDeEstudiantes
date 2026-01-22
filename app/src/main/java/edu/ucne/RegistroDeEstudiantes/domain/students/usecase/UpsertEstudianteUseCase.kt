package edu.ucne.RegistroDeEstudiantes.domain.students.usecase

import edu.ucne.RegistroDeEstudiantes.domain.students.model.Estudiante
import edu.ucne.RegistroDeEstudiantes.domain.students.repository.EstudianteRepository
import javax.inject.Inject

class UpsertEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {

    suspend operator fun invoke(estudiante: Estudiante): Result<Int> {

        val nombreResult = validateNombres(estudiante.nombres)
        if (!nombreResult.isValid)
            return Result.failure(IllegalArgumentException(nombreResult.error))

        val existe = repository.existeEstudianteConNombre(
            nombre = estudiante.nombres.trim(),
            estudianteId = estudiante.estudianteId?.takeIf { it > 0 }
        )
        if (existe) {
            return Result.failure(
                IllegalArgumentException("Ya existe un estudiante con el nombre '${estudiante.nombres}'")
            )
        }

        val emailResult = validateEmail(estudiante.email)
        if (!emailResult.isValid)
            return Result.failure(IllegalArgumentException(emailResult.error))

        val edadResult = validateEdad(estudiante.edad)
        if (!edadResult.isValid)
            return Result.failure(IllegalArgumentException(edadResult.error))

        return runCatching {
            repository.upsert(estudiante)
        }
    }
}