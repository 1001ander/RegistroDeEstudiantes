package edu.ucne.RegistroDeEstudiantes.data.students.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface EstudianteDao {

    @Query( value = "SELECT * FROM Estudiantes ORDER BY estudianteId DESC")
    fun observeAll(): Flow<List<EstudianteEntity>>

    @Query( value = "SELECT * FROM Estudiantes WHERE estudianteId = :id")
    suspend fun getById(id: Int): EstudianteEntity?

    @Upsert
    suspend fun upsert(entity: EstudianteEntity)

    @Delete
    suspend fun delete(entity: EstudianteEntity)

    @Query(value = "DELETE FROM Estudiantes WHERE estudianteId = :id")
    suspend fun deleteById(id: Int
    )
}