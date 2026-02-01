package edu.ucne.RegistroDeEstudiantes.data.penalidades.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TipoPenalidadDao {

    @Query("SELECT * FROM TiposPenalidades ORDER BY tipoId DESC")
    fun observeAll(): Flow<List<TipoPenalidadEntity>>

    @Query("SELECT * FROM TiposPenalidades WHERE tipoId = :id")
    suspend fun getById(id: Int): TipoPenalidadEntity?

    @Upsert
    suspend fun upsert(entity: TipoPenalidadEntity): Long

    @Query("DELETE FROM TiposPenalidades WHERE tipoId = :id")
    suspend fun deleteById(id: Int)

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM TiposPenalidades 
            WHERE LOWER(TRIM(nombre)) = LOWER(TRIM(:nombre))
            AND (:tipoId IS NULL OR tipoId != :tipoId)
        )
    """)
    suspend fun existeTipoPenalidadConNombre(nombre: String, tipoId: Int?): Boolean
}