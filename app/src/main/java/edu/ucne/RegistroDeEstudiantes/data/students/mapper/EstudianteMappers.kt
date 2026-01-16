package edu.ucne.RegistroDeEstudiantes.data.students.mapper

import edu.ucne.RegistroDeEstudiantes.data.students.local.EstudianteEntity
import edu.ucne.RegistroDeEstudiantes.domain.students.model.Estudiante

fun EstudianteEntity.toDomain() = Estudiante(
    estudianteId = estudianteId,
    nombres = nombres,
    email = email,
    edad = edad
)

fun Estudiante.toEntity() = EstudianteEntity(
    estudianteId = estudianteId,
    nombres = nombres,
    email = email,
    edad = edad
)
