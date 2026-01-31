package edu.ucne.RegistroDeEstudiantes.data.penalidades.mapper

import edu.ucne.RegistroDeEstudiantes.data.penalidades.local.TipoPenalidadEntity
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.model.TipoPenalidad

fun TipoPenalidadEntity.toDomain(): TipoPenalidad {
    return TipoPenalidad(
        tipoId = tipoId ?: 0,
        nombre = nombre,
        descripcion = descripcion,
        puntosDescuento = puntosDescuento
    )
}

fun TipoPenalidad.toEntity(): TipoPenalidadEntity {
    return TipoPenalidadEntity(
        tipoId = tipoId.takeIf { it > 0 },
        nombre = nombre,
        descripcion = descripcion,
        puntosDescuento = puntosDescuento
    )
}

