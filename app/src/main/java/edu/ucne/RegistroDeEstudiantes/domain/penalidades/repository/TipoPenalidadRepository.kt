package edu.ucne.RegistroDeEstudiantes.domain.penalidades.repository

import edu.ucne.RegistroDeEstudiantes.domain.penalidades.model.TipoPenalidad
import kotlinx.coroutines.flow.Flow

interface TipoPenalidadRepository {
    fun observeTiposPenalidades(): Flow<List<TipoPenalidad>>
    suspend fun getTipoPenalidad(id: Int): TipoPenalidad?
    suspend fun upsert(tipoPenalidad: TipoPenalidad): Int
    suspend fun delete(id: Int)
    suspend fun existeTipoPenalidadConNombre(nombre: String, tipoId: Int?): Boolean
}