package edu.ucne.RegistroDeEstudiantes.data.students.repository

import edu.ucne.RegistroDeEstudiantes.data.students.local.EstudianteDao
import edu.ucne.RegistroDeEstudiantes.data.students.mapper.toDomain
import edu.ucne.RegistroDeEstudiantes.data.students.mapper.toEntity
import edu.ucne.RegistroDeEstudiantes.domain.students.model.Estudiante
import edu.ucne.RegistroDeEstudiantes.domain.students.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class EstudianteRepositoryImpl @Inject constructor(
    private val dao: EstudianteDao
) : EstudianteRepository {

    override fun observeEstudiantes(): Flow<List<Estudiante>> =
        dao.observeAll().map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun getEstudiante(id: Int): Estudiante? =
        dao.getById(id)?.toDomain()

    override suspend fun upsert(estudiante: Estudiante): Int {
        dao.upsert(entity = estudiante.toEntity())
        return estudiante.estudianteId
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }
    override suspend fun existeEstudianteConNombre(nombre: String, estudianteId: Int?): Boolean {
        return dao.existeEstudianteConNombre(nombre, estudianteId)
}   }
