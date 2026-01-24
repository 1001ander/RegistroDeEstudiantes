package edu.ucne.RegistroDeEstudiantes.data.asignaturas.repository

import edu.ucne.RegistroDeEstudiantes.data.asignaturas.local.AsignaturaDao
import edu.ucne.RegistroDeEstudiantes.data.asignaturas.mapper.toDomain
import edu.ucne.RegistroDeEstudiantes.data.asignaturas.mapper.toEntity
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.model.Asignatura
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AsignaturaRepositoryImpl @Inject constructor(
    private val dao: AsignaturaDao
) : AsignaturaRepository {

    override fun observeAsignaturas(): Flow<List<Asignatura>> =
        dao.observeAll().map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun getAsignatura(id: Int): Asignatura? =
        dao.getById(id)?.toDomain()

    override suspend fun upsert(asignatura: Asignatura): Int {
        dao.upsert(entity = asignatura.toEntity())
        return asignatura.asignaturaId
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun existeAsignaturaConNombre(nombre: String, asignaturaId: Int?): Boolean {
        return dao.existeAsignaturaConNombre(nombre, asignaturaId)
    }
}