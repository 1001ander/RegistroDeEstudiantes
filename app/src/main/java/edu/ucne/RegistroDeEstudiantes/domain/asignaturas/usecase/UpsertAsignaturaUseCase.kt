package edu.ucne.RegistroDeEstudiantes.domain.asignaturas.usecase

import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.model.Asignatura
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.repository.AsignaturaRepository
import javax.inject.Inject

class UpsertAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(asignatura: Asignatura): Result<Int> {


        val codigoResult = validateCodigo(asignatura.codigo)
        if (!codigoResult.isValid)
            return Result.failure(IllegalArgumentException(codigoResult.error))


        val nombreResult = validateNombre(asignatura.nombre)
        if (!nombreResult.isValid)
            return Result.failure(IllegalArgumentException(nombreResult.error))


        val existe = repository.existeAsignaturaConNombre(
            nombre = asignatura.nombre.trim(),
            asignaturaId = asignatura.asignaturaId.takeIf { it > 0 }
        )

        if (existe) {
            return Result.failure(
                IllegalArgumentException("Ya existe una asignatura con el nombre '${asignatura.nombre}'")
            )
        }


        val aulaResult = validateAula(asignatura.aula)
        if (!aulaResult.isValid)
            return Result.failure(IllegalArgumentException(aulaResult.error))


        val creditosResult = validateCreditos(asignatura.creditos.toString())
        if (!creditosResult.isValid)
            return Result.failure(IllegalArgumentException(creditosResult.error))

        return runCatching {
            repository.upsert(asignatura)
        }
    }
}