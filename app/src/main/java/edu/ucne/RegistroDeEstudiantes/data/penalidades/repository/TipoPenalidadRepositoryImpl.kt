package edu.ucne.RegistroDeEstudiantes.data.penalidades.repository

import edu.ucne.RegistroDeEstudiantes.data.penalidades.local.TipoPenalidadDao
import edu.ucne.RegistroDeEstudiantes.data.penalidades.mapper.toDomain
import edu.ucne.RegistroDeEstudiantes.data.penalidades.mapper.toEntity
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.model.TipoPenalidad
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TipoPenalidadRepositoryImpl @Inject constructor(
    private val dao: TipoPenalidadDao
) : TipoPenalidadRepository {

    override fun observeTiposPenalidades(): Flow<List<TipoPenalidad>> =
        dao.observeAll().map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun getTipoPenalidad(id: Int): TipoPenalidad? =
        dao.getById(id)?.toDomain()

    override suspend fun upsert(tipoPenalidad: TipoPenalidad): Int {
        dao.upsert(entity = tipoPenalidad.toEntity())
        return tipoPenalidad.tipoId
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun existeTipoPenalidadConNombre(nombre: String, tipoId: Int?): Boolean {
        return dao.existeTipoPenalidadConNombre(nombre, tipoId)
    }
}

