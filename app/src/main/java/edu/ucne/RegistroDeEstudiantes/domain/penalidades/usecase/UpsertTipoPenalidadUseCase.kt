package edu.ucne.RegistroDeEstudiantes.domain.penalidades.usecase

import edu.ucne.RegistroDeEstudiantes.domain.penalidades.model.TipoPenalidad
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.repository.TipoPenalidadRepository
import javax.inject.Inject

class UpsertTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(tipoPenalidad: TipoPenalidad): Result<Int> {


        val nombreResult = validateNombre(tipoPenalidad.nombre)
        if (!nombreResult.isValid)
            return Result.failure(IllegalArgumentException(nombreResult.error))


        val existe = repository.existeTipoPenalidadConNombre(
            nombre = tipoPenalidad.nombre.trim(),
            tipoId = tipoPenalidad.tipoId.takeIf { it > 0 }
        )

        if (existe) {
            return Result.failure(
                IllegalArgumentException("Ya existe un tipo de penalidad con el nombre '${tipoPenalidad.nombre}'")
            )
        }


        val descripcionResult = validateDescripcion(tipoPenalidad.descripcion)
        if (!descripcionResult.isValid)
            return Result.failure(IllegalArgumentException(descripcionResult.error))


        val puntosResult = validatePuntosDescuento(tipoPenalidad.puntosDescuento.toString())
        if (!puntosResult.isValid)
            return Result.failure(IllegalArgumentException(puntosResult.error))

        return runCatching {
            repository.upsert(tipoPenalidad)
        }
    }
}