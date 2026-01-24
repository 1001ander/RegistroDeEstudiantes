package edu.ucne.RegistroDeEstudiantes.data.asignaturas.mapper



import edu.ucne.RegistroDeEstudiantes.data.asignaturas.local.AsignaturaEntity
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.model.Asignatura

fun AsignaturaEntity.toDomain(): Asignatura {
    return Asignatura(
        asignaturaId = asignaturaId ?: 0,
        codigo = codigo,
        nombre = nombre,
        aula = aula,
        creditos = creditos
    )
}

fun Asignatura.toEntity(): AsignaturaEntity {
    return AsignaturaEntity(
        asignaturaId = asignaturaId.takeIf { it > 0 },
        codigo = codigo,
        nombre = nombre,
        aula = aula,
        creditos = creditos
    )
}