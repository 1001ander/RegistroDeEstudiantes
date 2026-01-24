package edu.ucne.RegistroDeEstudiantes.domain.asignaturas.repository

import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.model.Asignatura
import kotlinx.coroutines.flow.Flow

interface AsignaturaRepository {
    fun observeAsignaturas(): Flow<List<Asignatura>>
    suspend fun getAsignatura(id: Int): Asignatura?
    suspend fun upsert(asignatura: Asignatura): Int
    suspend fun delete(id: Int)
    suspend fun existeAsignaturaConNombre(nombre: String, asignaturaId: Int?): Boolean
}