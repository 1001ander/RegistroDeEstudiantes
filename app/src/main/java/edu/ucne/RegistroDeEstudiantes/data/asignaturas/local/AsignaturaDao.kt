package edu.ucne.RegistroDeEstudiantes.data.asignaturas.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AsignaturaDao {

    @Query("SELECT * FROM asignaturas ORDER BY asignaturaId DESC")
    fun observeAll(): Flow<List<AsignaturaEntity>>

    @Query("SELECT * FROM asignaturas WHERE asignaturaId = :id")
    suspend fun getById(id: Int): AsignaturaEntity?

    @Upsert
    suspend fun upsert(entity: AsignaturaEntity): Long

    @Query("DELETE FROM asignaturas WHERE asignaturaId = :id")
    suspend fun deleteById(id: Int)

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM asignaturas 
            WHERE LOWER(TRIM(nombre)) = LOWER(TRIM(:nombre))
            AND (:asignaturaId IS NULL OR asignaturaId != :asignaturaId)
        )
    """)
    suspend fun existeAsignaturaConNombre(nombre: String, asignaturaId: Int?): Boolean
}